package com.izumi.myletter.controller;

import com.izumi.myletter.common.ParamUtils;
import com.izumi.myletter.entity.User;
import com.izumi.myletter.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private ILoginService loginService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findAll(){
        return loginService.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void add(HttpServletRequest request)
    throws Exception{
        Map<String, Object> temp = ParamUtils.getParamMap(request);
        User user = ParamUtils.map2obj(temp,User.class);
        System.out.println(user);
        loginService.save(user);
    }
}
