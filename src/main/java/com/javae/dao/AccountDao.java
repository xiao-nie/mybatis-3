package com.javae.dao;

import com.javae.pojo.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountDao {

    List<Account> queryAll();

    int updatePasswordById(@Param("id") Integer id, @Param("password") String password);

}