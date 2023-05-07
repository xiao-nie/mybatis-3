package com.javae.dao;

import com.javae.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    List<User> queryAll();

    User getById(@Param("id") Integer id);

}
