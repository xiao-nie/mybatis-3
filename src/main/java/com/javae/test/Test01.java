package com.javae.test;

import com.javae.cache.config.JedisUtil;
import com.javae.pojo.Account;
import com.javae.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Test01 {

    public static void main(String[] args) throws IOException {

        JedisUtil.getJedis().hset("key", "id1", "value1");
        JedisUtil.getJedis().hset("key", "id2", "value2");
        JedisUtil.getJedis().hset("key", "id3", "value3");
        JedisUtil.getJedis().hset("key", "id4", "value4");
        JedisUtil.getJedis().hset("key", "id5", "value5");


        Map<String, String> map = JedisUtil.getJedis().hgetAll("key");
        System.out.println("map = " + map);


        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("com.javae.dao.UserDao.queryAll");
        User user = sqlSession.selectOne("com.javae.dao.UserDao.getById");
        List<Account> accounts = sqlSession.selectList("com.javae.dao.AccountDao.queryAll");
        System.out.println("user = " + user);
        System.out.println("users = " + users);
        System.out.println("accounts = " + accounts);
        System.out.println("=========================================================================================");
        sqlSession.commit();

        users = sqlSession.selectList("com.javae.dao.UserDao.queryAll");
        user = sqlSession.selectOne("com.javae.dao.UserDao.getById");
        accounts = sqlSession.selectList("com.javae.dao.AccountDao.queryAll");
        System.out.println("user = " + user);
        System.out.println("users = " + users);
        System.out.println("accounts = " + accounts);
        System.out.println("=========================================================================================");
        sqlSession.commit();

        sqlSession.update("com.javae.dao.AccountDao.updatePasswordById");
        sqlSession.commit();

        users = sqlSession.selectList("com.javae.dao.UserDao.queryAll");
        user = sqlSession.selectOne("com.javae.dao.UserDao.getById");
        accounts = sqlSession.selectList("com.javae.dao.AccountDao.queryAll");
        System.out.println("user = " + user);
        System.out.println("users = " + users);
        System.out.println("accounts = " + accounts);
        System.out.println("=========================================================================================");
        sqlSession.commit();

        sqlSession.update("com.javae.dao.UserDao.updateAgeById");
        sqlSession.commit();

        users = sqlSession.selectList("com.javae.dao.UserDao.queryAll");
        user = sqlSession.selectOne("com.javae.dao.UserDao.getById");
        accounts = sqlSession.selectList("com.javae.dao.AccountDao.queryAll");
        System.out.println("user = " + user);
        System.out.println("users = " + users);
        System.out.println("accounts = " + accounts);
        System.out.println("=========================================================================================");
        sqlSession.commit();
    }

}
