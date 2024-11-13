# 消息队列组件@Topic

# 简介

Spring原生消息队列的使用体验不够友好，Topic、生产者、消费者三者的关联性较弱不利于维护，另外阿里云版RocketMQ的使用方式和开源版又有不同，所以@Topic组件基于面向对象设计，同时支持开源版和阿里云RocketMQ，消灭两者差异，提供统一而又便利的开发体验。

# 快速上手

## 1、定义Topic

```java

@Topic("my_topic")
public interface MyTopic extends MqTopic<String> {
}
```

注解中声明 topic 名称，`MqTopic`接口是组件提供，自定义接口继承`MqTopic` 并在泛型中声明消息的数据类型。

## 2、生产者逻辑

```java
@Resource
private MyTopic myTopic;

public void sendMessage(){
        myTopic.product("hello world");
        }
```

## 3、消费者逻辑

```java

@Consumer
public class MyTopicConsumer implements MyTopic {

    @Override
    public void consumer(String message) {
        log.info("消费数据 {}", message);
    }
}
```

# 详细说明

## MqTopic接口

`MqTopic`接口中提供两种方法，生产方法和消费方法，生产方法有多个重载以提供额外功能

```java
/**
 * 发送消息
 *
 * @param message 消息体
 */
default void product(T message){
        }

/**
 * 发送消息
 *
 * @param message  消息体
 * @param mqConfig 消息配置
 */
default void product(T message,MqConfig mqConfig){
        }

/**
 * 发送消息
 *
 * @param message       消息体
 * @param configSetting 消息配置
 */
default void product(T message,Consumer<MqConfig> configSetting){
        }

/**
 * 消费消息
 *
 * @param message 消息体
 */
        void consume(T message);

```

## MqConfig消息发送配置（支持Tag、HashKey、延时消息）

发送消息方法有两个带配置参数`MqConfig`的重载方法，以实现更特殊的需求，`MqConfig` 中可以配置参数如下：

```java
/**
 * tag
 */
private String tag;

/**
 * 设置了同一hashKey的消息可保证消费顺序
 */
private String hashKey;

/**
 * 延迟消息的延迟级别（开源rocketmq使用）
 */
private MqDelayLevel delayLevel;

/**
 * 消息交付时间（延时消息的发送时间，阿里云rocketmq使用）
 */
@Getter
private Long startDeliverTime;
```

使用示例：

```java
@Resource
private UserTokenLogoutTopic userTokenLogoutTopic;

public void producerMessage(Long userId){
        UserTokenLogoutMessageBody messageBody=new UserTokenLogoutMessageBody(userId);
        // 1.普通方式
        userTokenLogoutTopic.product(messageBody);

        // 2.延迟消息
        // 开源版，只能配置固定的18种延时时间（下例为延时30秒）
        MqConfig mqConfig=new MqConfig().setDelayLevel(MqDelayLevel.TIME_30S);
        // 阿里云版，支持任意延迟时间发送（下例为延时59秒）
        MqConfig mqConfig=new MqConfig().delay(59);
        // 阿里云版也支持定时发送（2023-6-30 18:30发送）
        MqConfig mqConfig=new MqConfig().delayAt(LocalDateTime.of(2023,6,30,18,30));
        userTokenLogoutTopic.product(messageBody,mqConfig);

        // 3.也可以使用lambda写法
        userTokenLogoutTopic.product(messageBody,config->config.delay(59).setTag(tag).setHashKey(hashKey));
        }
```

## @Consumer注解（支持Tag、顺序消费、广播）

标注在消费者类上，同时提供如下属性可配置

```java
//消费组id（gid）默认为消费者服务名
String groupId()default "${spring.application.name}";
//消息的tag
        String tag()default "*";
//消费模式，可选并发消费和顺序消费
        ConsumeMode consumeMode()default ConsumeMode.CONCURRENTLY;
//消息模式，可选集群消费和广播
        MessageModel messageModel()default MessageModel.CLUSTERING;
```

## @MessageQueueScan（扫包注解）

扫描指定路径下的Topic注解类，类似于 mybatis 的`@MapperScan` ，默认扫描路径是`"com.msb.**.mq.**"`
，推荐自定义Topic接口写在`mq.topic`包下，消费者类写在`mq.consumer`包下，如果需要扫描其他包，在Application类上使用该注解指定扫描路径

## 选择RocketMQ版本

配置文件中指定 RocketMQ 版本是开源版还是阿里云版（如果不指定默认是开源版）

```yaml
# 阿里云版:ali_cloud，开源版:open_sourse
rocketmq:
  server-provider: ali_cloud
```

开源版配置完整示例：

```yaml
rocketmq:
  server-provider: open_sourse
  # 自己的mq服务器
  name-server: 127.0.0.1:9876
  # 组名（只有生产者服务需要配置，消费者不用）
  producer:
    group: ${spring.application.name}
```

阿里云版配置完整示例：

```yml
rocketmq:
  server-provider: ali_cloud
  # 阿里云mq服务器及AK/SK
  name-server: http://MQ_INST_xx.cn-zhangjiakou.mq-vpc.aliyuncs.com:8080
  ali-mq:
    access-key: ak
    secret-key: sk
```