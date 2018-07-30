**mysql 读写分离配置**
**docker mysql下载安装**
docker search mysql
docker pull docker.io/mysql
**配置**
docker run -p 3308:3306 --name mysql_3308 -v /home/docker/mysql/mysql_3308/data:/var/lib/mysql -v /home/docker/mysql/mysql_3308/conf:/etc/mysql/conf.d -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=123456 -d 8d99edb9fd40
-p 3308:3306 将容器3306端口映射到主机3308端口
--name mysql_3308   制定容器名
-v /home/docker/mysql/mysql_3308/data:/var/lib/mysql 将容器/var/lib/mysql文件夹映射到主机/home/docker/mysql/mysql_3308/data文件夹，用于文件数据存储
-v /home/docker/mysql/mysql_3308/conf:/etc/mysql/conf.d 将容器/etc/mysql/conf.d文件夹映射到主机/home/docker/mysql/mysql_3308/conf文件夹，配置文件
-e MYSQL_ROOT_HOST=%    配置mysql root用户任意访问
-e MYSQL_ROOT_PASSWORD=123456   配置mysql root用户密码
-d 8d99edb9fd40     制定镜像id

