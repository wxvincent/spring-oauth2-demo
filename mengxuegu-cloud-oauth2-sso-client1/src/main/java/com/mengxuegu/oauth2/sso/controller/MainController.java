package com.mengxuegu.oauth2.sso.controller;

import com.mengxuegu.base.result.MengxueguResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/member")
    public String member() {
        MengxueguResult result =
                restTemplate.getForObject("http://localhost:7001/product/list", MengxueguResult.class);
        System.out.println("获取商品信息：" + result);
        return "member";
    }
}
