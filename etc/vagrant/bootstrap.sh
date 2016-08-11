#!/usr/bin/env bash
 
# LIBRERÍAS
debconf-set-selections <<< 'mysql-server mysql-server/root_password password root'
debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password root'

apt-get update
apt-get upgrade -y
apt-get install -y vim curl htop 
apt-get install -y mysql-server
sed -i "s/^bind-address/#bind-address/" /etc/mysql/my.cnf
mysql -u root -proot -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION; FLUSH PRIVILEGES;"

cp /vagrant/filesystem/etc/mysql/conf.d/mysqld_override.cnf /etc/mysql/conf.d
chown root.root /etc/mysql/conf.d/mysqld_override.cnf

service mysql restart