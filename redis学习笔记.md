# redis学习

参考教程：[【尚硅谷】Redis 6 入门到精通 超详细 教程_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1Rv41177Af)

## NoSql

redis是一种NoSql数据库，为了解决性能的问题

解决cpu和内存的压力，如图

![解决CPU内存压力](C:\dev\devworkspace\study\redis学习\解决CPU和内存压力.png)

**session存储问题**

> 1. 存储到客户端的cookie中，不安全
> 2. 存储到每台服务器上，数据冗余，session复制

解决io压力

![](C:\dev\devworkspace\study\redis学习\解决io压力.png)



NoSql（not only sql）：非关系型数据库，不依赖业务逻辑方式存储，以简单的key-value模式存储，大大增加了数据库的扩展能力。（mysql是关系型数据库）

> 1. 不支持SQL标准
> 2. 不支持ACID（没有事务的四大特性：原子性、一致性、隔离性、持久性）
> 3. 远超SQL的性能

适用场景

>1. 对数据高并发读写
>2. 海量数据读写
>3. 对数据高扩展性

不适用场景

> 1. 需要事务支持
> 2. 基于SQL的结构化查询存储，处理复杂的关系，需要即席查询

## Redis

> redis支持多数据类型，支持持久化，单线程+多路IO复用

**redis5大数据类型：Redis字符串（String）、Redis列表（List）、Redis集合（Set）、Redis哈希（Hash）、Redis有序集合Zset**

使用usr/local/bin/redis-cli连接redis

> - 字符串String类型最大512M，自动扩容类似ArrayList
> - List类型底层是双向列表
> - Set是string类型的无序集合，底层是一个value为null的hash表，数据结构是dict字典，字典是哈希表实现
> - 哈希类型是键值对集合，他的value是一个string类型的field-value映射表，类似Java中的Map\<String,Object\>，哈希类型类似Java中的Map\<String,Map\<String,Object\>\>
> - 有序集合Zset每个成员关联一个score，根据score有序

### redis事务

Redis事务是一个单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。事务在执行的过程中，不会被其他客户端发送来的命令请求所打断。

Redis事务的主要作用就是串联多个命令防止别的命令插队。

> 悲观锁：每次拿数据都认为别人会修改，所以每次拿数据都会上锁，别人拿数据就会block阻塞直到拿到锁，传统关系型数据库很多地方用到这个锁机制，比如行锁、表锁、读锁、写锁，做操作之前都会上锁。
>
> 乐观锁：拿数据时很乐观，不会认为别人会修改，不会上锁，但是更新的时候会判断期间有没有别人更新该数据，可以使用版本号等机制，乐观锁适用于多读的场景，可以提高吞吐量。Redis就是利用这种check-and-set机制实现事务的。























