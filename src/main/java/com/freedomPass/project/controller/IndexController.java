package com.freedomPass.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"/"})
    public String index() {
        return "login";
    }

    @GetMapping("/install")
    public String install() {
        return "installation/installation";
    }
}
