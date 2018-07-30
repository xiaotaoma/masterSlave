**mysql主从配置（基于docker）**
参考：https://www.jianshu.com/p/ab20e835a73f 基于 Docker 的 MySQL 主从复制
https://www.cnblogs.com/jaycekon/p/spring-blog-mybatis.html  Mybatis 读写分离
**docker mysql下载安装**
docker search mysql
docker pull docker.io/mysql
**docker容器创建**
master容器创建启动
docker run -p 3308:3306 --name mysql_3308 -v /home/docker/mysql/mysql_3308/data:/var/lib/mysql -v /home/docker/mysql/mysql_3308/conf:/etc/mysql/conf.d -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=123456 -d 8d99edb9fd40
slave容器创建启动
docker run -p 3309:3306 --name mysql_3309 -v /home/docker/mysql/mysql_3309/data:/var/lib/mysql -v /home/docker/mysql/mysql_3309/conf:/etc/mysql/conf.d -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=123456 -d 8d99edb9fd40
-p 3308:3306 将容器3306端口映射到主机3308端口
--name mysql_3308   指定容器名
-v /home/docker/mysql/mysql_3308/data:/var/lib/mysql 将容器/var/lib/mysql文件夹映射到主机/home/docker/mysql/mysql_3308/data文件夹，用于文件数据存储
-v /home/docker/mysql/mysql_3308/conf:/etc/mysql/conf.d 将容器/etc/mysql/conf.d文件夹映射到主机/home/docker/mysql/mysql_3308/conf文件夹，配置文件
-e MYSQL_ROOT_HOST=%    配置mysql root用户任意访问
-e MYSQL_ROOT_PASSWORD=123456   配置mysql root用户密码
-d 8d99edb9fd40     指定镜像id

**master配置**
在/home/docker/mysql/mysql_3308/conf增加配置文件master.cnf
增加以下内容
[mysqld]
## 设置server_id，一般设置为IP，同一局域网内注意要唯一
server_id=100
## 复制过滤：也就是指定哪个数据库不用同步（mysql库一般不同步）
binlog-ignore-db=mysql
## 开启二进制日志功能，可以随便取，最好有含义（关键就是这里了）
log-bin=edu-mysql-bin
## 为每个session 分配的内存，在事务过程中用来存储二进制日志的缓存
binlog_cache_size=1M
## 主从复制的格式（mixed,statement,row，默认格式是statement）
binlog_format=mixed
## 二进制日志自动删除/过期的天数。默认值为0，表示不自动删除。
expire_logs_days=7
## 跳过主从复制中遇到的所有错误或指定类型的错误，避免slave端复制中断。
## 如：1062错误是指一些主键重复，1032错误是因为主从数据库数据不一致
slave_skip_errors=1062
**slave配置**
在/home/docker/mysql/mysql_3309/conf增加配置文件slave.cnf
增加以下内容
[mysqld]
## 设置server_id，一般设置为IP，同一局域网内注意要唯一
server_id=102
## 复制过滤：也就是指定哪个数据库不用同步（mysql库一般不同步）
binlog-ignore-db=mysql
## 开启二进制日志功能，可以随便取，最好有含义（关键就是这里了）
log-bin=edu-mysql-bin
## 为每个session 分配的内存，在事务过程中用来存储二进制日志的缓存
binlog_cache_size=1M
## 主从复制的格式（mixed,statement,row，默认格式是statement）
binlog_format=mixed
## 二进制日志自动删除/过期的天数。默认值为0，表示不自动删除。
expire_logs_days=7
## 跳过主从复制中遇到的所有错误或指定类型的错误，避免slave端复制中断。
## 如：1062错误是指一些主键重复，1032错误是因为主从数据库数据不一致
slave_skip_errors=1062
## relay_log配置中继日志
relay_log=edu-mysql-relay-bin
### log_slave_updates表示slave将复制事件写进自己的二进制日志
log_slave_updates=1
### 防止改变数据(除了特殊的线程)
read_only=1
**完成Master和Slave链接**
mysql -uroot -p123456 -P 3308 进入主库
show master status;
+----------------------+----------+--------------+------------------+-------------------+
| File                 | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+----------------------+----------+--------------+------------------+-------------------+
| edu-mysql-bin.000004 |     3177 |              | mysql            |                   |
+----------------------+----------+--------------+------------------+-------------------+

mysql -uroot -p123456 -P 3309 进入从库
change master to master_host='192.168.0.33', master_user='slave', master_password='123456', master_port=3306, master_log_file='edu-mysql-bin.000001', master_log_pos=929, master_connect_retry=30;
master_host='192.168.0.33'  配置主库ip
master_port=3306            配置主库端口
master_user='slave'         配置链接主库用户名
master_password='123456'    配置链接主库用户密码
master_log_file='edu-mysql-bin.000001'  主库日志文件
master_log_pos=929          开始复制的日志文件位置

start slave; 开始复制




























