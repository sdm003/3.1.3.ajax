package web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.User;
import web.service.UserService;
import java.security.Principal;

@Controller
@PreAuthorize("hasRole('USER')")
@RequestMapping("/user")
public class UserController {

    private final UserService userServiceImpl;

    public UserController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal) {
        User user = userServiceImpl.listUsers().stream().filter(x -> x.getName().equals(principal.getName())).findFirst().get();
        model.addAttribute("user", user);
        return "/main/resources/templates/user/hello.html";
    }
}
