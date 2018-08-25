package com.izumi.myletter.service.Impl;

import com.izumi.myletter.dao.UserRepository;
import com.izumi.myletter.entity.User;
import com.izumi.myletter.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
