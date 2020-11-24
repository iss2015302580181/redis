package com.h3c.redis;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WzTestController {

    @RequestMapping("/abc")
    public String ww(){
        System.out.println("successss");
        return "index";
    }
}
