

## Nacos升级

从 1.4 -> 2.1 ，表结构变化

https://github.com/alibaba/nacos/blob/develop/distribution/conf/nacos-mysql.sql

需要新增字段

```sql
ALTER TABLE his_config_info ADD `encrypted_data_key` TEXT NULL COMMENT '秘钥';
ALTER TABLE config_info_beta ADD `encrypted_data_key` TEXT NULL COMMENT '秘钥';
ALTER TABLE config_info ADD `encrypted_data_key` TEXT NULL COMMENT '秘钥';
```



启动 mogu-web 的时候，出现问题

解决方法：https://blog.csdn.net/pointer_v/article/details/104989935