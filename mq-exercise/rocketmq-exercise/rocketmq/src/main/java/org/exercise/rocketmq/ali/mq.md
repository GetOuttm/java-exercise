https://rocketmq.apache.org/
RocketMQ 消息构成非常简单，如下图所示。

topic，表示要发送的消息的主题。 body 表示消息的存储内容 properties 表示消息属性 transactionId 会在事务消息中使用。 提示 Tag: 不管是 RocketMQ 的 Tag 过滤还是延迟消息等都会利用 Properties
消息属性机制，这些特殊信息使用了系统保留的属性Key，设置自定义属性时需要避免和系统属性Key冲突。

Keys: 服务器会根据 keys 创建哈希索引，设置后，可以在 Console 系统根据 Topic、Keys 来查询消息，由于是哈希索引，请尽可能保证 key 唯一，例如订单号，商品 Id 等。 Message 可以设置的属性值包括： 字段名 默认值 必要性 说明 Topic null 必填 消息所属 topic
的名称 Body null 必填 消息体 Tags null 选填 消息标签，方便服务器过滤使用。目前只支持每个消息设置一个 Keys null 选填 代表这条消息的业务关键词 Flag 0 选填 完全由应用来设置，RocketMQ 不做干预 DelayTimeLevel 0 选填 消息延时级别，0 表示不延时，大于
0 会延时特定的时间才会被消费 WaitStoreMsgOK true 选填 表示消息是否在服务器落盘后才返回应答。