package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping(value = "/")
    public String getLoginPage(){
        return "redirect:/login";
    }

    @GetMapping(value = "/myLogOut")
    public String getLogOut(HttpServletRequest request) throws ServletException {
       request.logout();
       return "redirect:/login";
    }
}
