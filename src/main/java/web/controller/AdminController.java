package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping()
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/admin")
    public String users(Model model) {
        model.addAttribute("user", userService.listUser());
        return "/admin";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute(new User());
        List<Role> roles = roleService.listRole();
        model.addAttribute("allRoles", roles);
        return "/new";
    }

    @PostMapping("/new")
    public String addUser(@Validated(User.class) @ModelAttribute("user") User user,
                          @RequestParam("authorities") List<String> values) {

        Set<Role> roleSet = userService.getSetOfRole(values);
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.listRole());
        return "/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@Validated(User.class) @ModelAttribute("user") User user,
                             @RequestParam("authorities") List<String> values) {
        Set<Role> roleSet = userService.getSetOfRole(values);
        user.setRoles(roleSet);
        userService.updateUser(user);
        return "redirect:/admin";
    }


    @RequestMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") Long id, Model model) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
