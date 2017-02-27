#!bin/sh
echo -n "waiting for TCP connection"

while ! mysql -h$DB_1_PORT_3306_TCP_ADDR -P$DB_1_PORT_3306_TCP_PORT -uroot -p$DB_1_ENV_MYSQL_ROOT_PASSWORD -e 'SHOW DATABASES;' ; 
do 
    echo .;
    sleep 1;
done

mysql -h$DB_1_PORT_3306_TCP_ADDR -P$DB_1_PORT_3306_TCP_PORT -uroot -p$DB_1_ENV_MYSQL_ROOT_PASSWORD < /tmp/sql/0_init_db.sql
