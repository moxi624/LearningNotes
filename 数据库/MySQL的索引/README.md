# MySQL索引

## 索引分类

MySQL索引可以分为普通索引，唯一索引，主键索引，组合索引和全文索引。索引不包含null值的列，索引项可以为null（唯一索引，组合索引），但是只要列中有null值就不会被包含在索引中。

- 普通索引：create index  index_name on table(column)，或者创建表时指定，create table(..., index index_name column)

- 唯一索引：类似于普通索引，索引列的值必须唯一（可以为空，这点和主键索引不同）

  create unique index index_name on table(column)；或者创建表时指定unique index_name column

- 主键索引：特殊的唯一索引，不允许为空，只能有一个

  一般是在建表时指定primary key(column)

- 组合索引：在多个字段上创建索引，遵循**最左匹配**原则

  alter table t add index index_name(a,b,c);

- 全文索引：主要用来查找文本中的关键字，不是直接与索引中的值相比较，像是一个搜索引擎，配合 match against 使用，现在只有char，varchar，text上可以创建索引，在数据量比较大时，先将数据放在一个没有全文索引的表里，然后在利用create index创建全文索引，比先生成全文索引在插入数据快很多。

## 索引的使用

MySQL每次只使用一个索引，与其说 数据库查询只能用一个索引，倒不如说，和全表扫描比起来，去分析两个索引 B+树更耗费时间，所以where A=a and B=b 这种查询使用（A，B）的组合索引最佳，B+树根据（A，B）来排序。

- 主键，unique字段
- 和其他表做连接的字段需要加索引
- 在where 里使用 >, >=, = , <, <=, is null 和 between等字段。
- 使用不以通配符开始的like，where A like ‘China%’
- 聚合函数里面的 MIN()， MAX()的字段
- order by  和 group by字段

## 何时不使用索引

- 表记录太少
- 数据重复且分布平均的字段（只有很少数据的列）；
- 经常插入、删除、修改的表要减少索引
- text，image 等类型不应该建立索引，这些列的数据量大（加入text的前10个字符唯一，也可以对text前10个字符建立索引）
- MySQL能估计出全表扫描比使用索引更快的时候，不使用索引

## 索引何时失效

- 组合索引为使用最左前缀，例如组合索引（A，B），where B = b 不会使用索引
- like未使用最左前缀，where A  like "%China"
- 搜索一个索引而在另一个索引上做 order by， where A = a order by B，只会使用A上的索引，因为查询只使用一个索引。
- or会使索引失效。如果查询字段相同，也可以使用索引。例如  where A = a1 or A = a2（生效），where A=a or B = b （失效）
- 在索引列上的操作，函数upper()等，or、！ = （<>）,not in 等



