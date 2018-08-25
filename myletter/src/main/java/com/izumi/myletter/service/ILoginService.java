package com.izumi.myletter.service;

import com.izumi.myletter.entity.User;

import java.util.List;


public interface ILoginService {

    List<User> findAll();

    void save(User user);
}
