package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.User;
import web.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class HelloController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/index")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        model.addAttribute("messages", messages);
        return "/index";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "/login";
    }

    @GetMapping("/user")
    public String userPage(Model model, Authentication authentication) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        User userByName = userService.findUserByName(authentication.getName());
        messages.add("Your name" + " " + authentication.getName());
        messages.add("Your role" + " " + authentication.getAuthorities());
        messages.add("Your age" + " " + userByName.getAge());
        messages.add("Your profession" + " " + userByName.getProfession());
        model.addAttribute("messages", messages);
        return "/user";
    }

}