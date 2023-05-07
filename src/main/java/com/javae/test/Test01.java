package com.javae.test;

import com.javae.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test01 {

    public static void main(String[] args) throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("com.javae.dao.UserDao.queryAll");
        User user = sqlSession.selectOne("com.javae.dao.UserDao.getById");
        System.out.println("list = " + list);
        sqlSession.commit();
        List<User> list2 = sqlSession.selectList("com.javae.dao.UserDao.queryAll");
        System.out.println("list2 = " + list2);
        sqlSession.commit();
        List<User> list3 = sqlSession.selectList("com.javae.dao.UserDao.queryAll");
        System.out.println("list3 = " + list3);
        sqlSession.commit();

    }

}
