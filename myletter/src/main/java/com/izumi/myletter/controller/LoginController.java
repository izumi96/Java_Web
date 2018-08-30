package com.izumi.myletter.controller;

import com.izumi.myletter.common.ParamUtils;
import com.izumi.myletter.entity.User;
import com.izumi.myletter.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/crud")
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
