Provider/MessageProvider：生产者
Consumer/MessageConsumer：消费者
PTP：Point To Point，点对点通信消息模型
Pub/Sub：Publish/Subscribe，发布订阅消息模型
Queue：队列，目标类型之一，和PTP结合
Topic：主题，目标类型之一，和Pub/Sub结合
ConnectionFactory：连接工厂，JMS用它创建连接
Connnection：JMS Client到JMS Provider的连接
Destination：消息目的地，由Session创建
Session：会话，由Connection创建，实质上就是发送、接受消息的一个线程，因此生产者、消费者都是Session创建的


https://www.jianshu.com/u/cb569cce501b

https://blog.51cto.com/mazongfei/2415479
