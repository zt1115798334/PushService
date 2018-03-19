package com.zt.pushservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangtong
 * Created by on 2018/3/19
 */
@RestController
@RequestMapping("my")
public class MyController {

    @RequestMapping("index")
    public String index(HttpServletRequest request){
        String req = request.getParameter("body");
        System.out.println("req = " + req);
        return "success";
    }
}
