package com.mengxuegu.oauth2.web.controller;

import com.mengxuegu.base.result.MengxueguResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('product:list')")
    public MengxueguResult list() {
        List<String> list = new ArrayList<>();
        list.add("眼镜");
        list.add("格子衬衣");
        list.add("双肩背包");
        return MengxueguResult.ok(list);
    }
}
