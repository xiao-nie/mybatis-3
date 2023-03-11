# MyBatis源码分析

## 第一章、回顾

### 1. 课程中工具的版本

```markdown
1. JDK8
2. IDEA2018.3
3. Maven3.5.3
4. MySQL5.1.48 ---> MySQL5
5. MyBatis3.4.6
```

## 2. MyBatis开发的简单回顾

```markdown
1. MyBatis做什么？
	MyBatis是一个ORM类型框架，解决数据库访问和操作的问题，对现有JDBC技术的封装。
2. MyBatis搭建开发环境
	1. 准备Jar
		a. MyBatis
		b. MySQL
	2. 准备配置文件
		a. 基本配置文件 mybatis-config.xml
			1. 数据源的设置 environments
			2. 类型别名
			3. mapper文件的注册
		b. Mapper.xml
			1. DAO规定方法的实现 ---> SQL语句
	3. 初始化配置
		1. mybatis-config.xml
		2. 配置 environment
3. 开发步骤 7步
	1. entity
	2. 类型别名
	3. table
	4. DAO接口
	5. Mapper文件
	6. Mapper文件注册
	7. API编程
		
		
```

- 核心代码分析

  ```java
  InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
  SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
  SqlSession sqlSession = sqlSessionFactory.openSession();
  // 两种方式功能等价
  // 实现效果 区别
  // 哪种方式好？第一种方式好 表达概念更清晰 可读性更高
  // 第一种本质上就是对第二种开发的封装。（代理设计模式）
  // 第一种
  UserDAO userDAO = sqlSession.getMapper(UserDAO.class);
  List<User> users = userDAO.queryAllUsersByPage();
  // 第二种
  List<User> users = sqlSession.selectList("com.baizhiedu.dao.UserDAO.queryAllUsers");
  
  String name = "zhangsan";
  public class User{
      private String name = "zhangsan";
  }
  ```

## 第二章、MyBatis的核心对象

### 1.  MyBatis的核心对象及其作用

```markdown
1. 数据存储类对象
	概念: 在Java中（JVM）对MyBatis相关的配置信息进行封装。
	mybatis-config.xml ---> Configuration
	Configuration
		1. 封装了mybatis-config.xml
		2. 封装了 mapper 文件 MappedStatement
		3. 创建了MyBatis其他相关对象
	xxxDaoMapper.xml ---> MapperStatement（形象的认知，不准确）
	MappedStatement对象
		对应的就是Mapper文件中的一个一个的配置标签
		<select id=""> ---> MappedStatement
		<insert id=""> ---> MappedStatement
		
		注定 一个MyBatis应用中 N个MappedStament 对象
		MappedStatment 可以找到 Configuration
		
		statementType
		
		MappedStatment 中封装了SQL语句 ---> BoundSql
2. 操作类对象
	Excutor
		Excutor 是Mybatis中处理功能的核心
			1. 增删改update	查 query
			2. 事务操作
				提交 回滚
			3. 缓存相关的操作
		Excutor接口
			BatchExcutor
				JDBX中批处理的操作，BatchExcutor
			ReuseExcutor
				目的: 复用 Statement 
				insert into t_user(id, name) values(1, 'nie')
				insert into t_user(id, name) values(2, 'nie')
				参数不同，无法复用，该实现(ReuseExcutor)作用不大
			SimpleExcutor
				常用Excutor Mybatis推荐 默认 
				Configuration protected ExecutorType defaultExcutorType = ExcutorType.SIMPLEQ
	StatmentHandle
		StatementHandler是Mybatis封装了JDBC Statement，真正Mybatis进行数据库访问操作的核心
			功能: 增删改查
			StatementHandler接口
				SimpleStatementHandler
					JDBC 操作
				PreparedStatementHandler
				CallableStatementHandler	
	ParameterHandler
		目的：Mybatis参数 ---> JDBC 相关参数
			@Param ---> #{} ---> ?
	ResultSetHandler
		目的：对JDBC中查询的结果集 ResultSet 进行封装
	TypeHandler
```

![image-20221129211117171](MyBatis源码分析.assets/image-20221129211117171.png)

![image-20230302224221071](C:\Users\50200\AppData\Roaming\Typora\typora-user-images\image-20230302224221071.png)

### 2. MyBatis的核心对象如何与SqlSession建立联系？

```markdown
Mybatis源码中的这些核心对象 在 SqlSession调用对应功能时候建立连接
SqlSession.insert()
	DefaultSqlSession
		Exctutor
			StatmentHandler
SqlSession.update();
SqlSession.insert();
SqlSession.selectOne();
...

底层：
	SqlSession.insert();
	SqlSession.update();
    SqlSession.delete();
应用层面：
	UserDAO userDAO = SqlSession.getMapper(UserDao.class);
	// UserDAO接口的实现类的对象
	// 疑问？UserDAO接口实现类 在哪里？
	// 动态字节码技术 ---> 类 在 JVM 运行时创建  JVM 运行结束后，消失了
		1. 如何 创 UserDAO xxxDAO接口的实现类
			代理 （动态代理）
            a. 为原始对象(目标)增加【额外功能】
            b. 远程代理 (RPC) Dubbo
            c. 接口实现类，我们看不到实实在在的类文件，但是运行时却能体现出来。
               无中生有
			
		2. 实现类 如何进行实现的
			interface UserDAO{
				List<User> queryAllUsers();
			}
			UserDAOImpl implements UserDAO{
				queryAllUsers(){
					sqlSession.select("namespace.id", 参数)
						|- Excutor
							|- StatementHandler
								|- ParamenterHandler,ResultSetHandler
									TypeHandler
				}
				save(){
					sqlSession.insert("namespace.id", 参数);
				}
			}
	userDAO.queryUserById();
	userDAO.queryUsers();
			
```



  









































