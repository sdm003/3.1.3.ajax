package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userServiceImpl;

    @GetMapping(value = "/")
    public String adminPage() {
        return "admin/admin";
    }

    @ResponseBody
    @GetMapping(value = "/findUser/{id}")
    public User findUser(@PathVariable long id) {
        User user = userServiceImpl.getUserById(id);
        return user;
    }

    @ResponseBody
    @GetMapping("/rest")
    public List<User> allUsersPage() {
        return userServiceImpl.listUsers();
    }

    @PostMapping(value = "/edit")
    public void editUser(@RequestBody() User user) {
        userServiceImpl.setRole(user,user.getRoles().iterator().next().getId());
        userServiceImpl.updateUser(user);
    }

    @PostMapping(value = "/add")
    public void createUser(@RequestBody User user) {
        userServiceImpl.setRole(user,user.getRoles().iterator().next().getId());
        userServiceImpl.add(user);
    }

    @GetMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userServiceImpl.deleteUser(id);
    }


    @GetMapping("/error")
    public String errorUser(ModelMap model) {
        return "error";
    }
}
