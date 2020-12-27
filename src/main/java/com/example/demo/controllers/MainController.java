package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return "redirect:" + EndPoints.LOGIN;
    }

    @GetMapping(EndPoints.REGISTER)
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(EndPoints.PROCESS_REGISTER)
    public String processRegistration(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        return this.userService.register(user, bindingResult, redirectAttributes);
    }

    @GetMapping(EndPoints.LOGIN)
    public String login(Model model) {
        return "login";
    }

    @GetMapping(EndPoints.USER_FORM_EDIT_PROFILE)
    public String formEditProfile(Model model) {
        return this.userService.formEditProfile(model);
    }

    @PostMapping(EndPoints.USER_EDIT_PROFILE)
    public String editProfile(@Valid User user, @PathVariable (value = "id") long id, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        return this.userService.editProfile(user, id, bindingResult, redirectAttributes);
    }
}
