package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping()
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, Principal principal) {
        List<User> users = userService.getAllUsers();
        User user = new User();
        model.addAttribute("activeUser", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("users", users);
        model.addAttribute("user", user);
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "adminPage";
    }


    @GetMapping(value = "users/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(name = "rolesSelected", defaultValue = "0") Integer[] rolesId) {
        Set<Role> roles = new HashSet<>(roleService.getRoles(rolesId));
        user.setRoles(roles);
        userService.add(user);
        return "redirect:/admin";

    }

    @GetMapping(value = "users/delete")
    public String deleteUser(@RequestParam(name = "id") Integer id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "users/update", method = RequestMethod.POST)
    public String updateUser(User user, Integer oldUserId) {
        userService.updateUser(oldUserId, user);
        return "redirect:/admin";
    }
}
