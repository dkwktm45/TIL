package com.sp.fc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomController {

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("name","Jin22");
        return "index";
    }
    @GetMapping("/login")
    public String login(@RequestParam String site,Model model){
        model.addAttribute("site",site);
        return "loginForm";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String site,Model model){
        model.addAttribute("site",site);
        return "redirect:/"+site;
    }
}
