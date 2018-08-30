package com.izumi.myletter.controller;

import com.izumi.myletter.common.Result;
import com.izumi.myletter.common.ResultUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping(value = "/upanddown")
public class UpLoadController {

    /**单文件上传，主要用到的是MultipartFile获取前端post请求的图片
     * 普通上传
     * 字节流有些文件传不上去,试了一下xmind是不行的
     * */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResultUtil.fileFail();
        }

        try {
            // 字节流写进磁盘
            byte[] bytes = file.getBytes();
            Path path = Paths.get("F://test/" + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultUtil.success();
    }

    @RequestMapping(value = "/upload2",method = RequestMethod.POST)
    @ResponseBody
    public Result multiUpload(@RequestParam MultipartFile[] files){
        Result result = new Result();
        if(files.length == 0){
            return ResultUtil.fileFail();
        }
            for(MultipartFile temp: files){
            try{
                //字节数组写入磁盘
                byte[] bytes = temp.getBytes();
                Path path = Paths.get("F://test/" + temp.getOriginalFilename());
                Files.write(path, bytes);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ResultUtil.success();
    }
}
