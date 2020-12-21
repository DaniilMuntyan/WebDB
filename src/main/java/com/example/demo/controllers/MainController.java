package com.example.demo.controllers;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.dto.EditUserDto;
import com.example.demo.dto.NewOrderDto;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/process_register")
    public String processRegistration(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        return this.userService.register(user, bindingResult, redirectAttributes);
    }

    @GetMapping("/admin/users")
    public String list_users(Model model) {
        return findPaginatedAdminUsers(1, model);
    }

    @GetMapping("/admin/users/page/{pageNo}")
    public String findPaginatedAdminUsers(@PathVariable (value = "pageNo") int pageNo, Model model) {
        int pageSize = 8;
        Page<User> page = userService.findPaginated(pageNo, pageSize);
        List<User> listUsers = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        return "admin_user_list";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable (value = "id") long id) {
        this.userService.deleteUserById(id);
        return "redirect:/";
    }

    @GetMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        this.userService.save(user);
        return "redirect:/page/1";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable (value="id") Long id, Model model) {
        Optional<User> user = userService.findUserById(id);
        model.addAttribute("editUserDto", userService.userToDto(user.get()));
        return "update_user";
    }

    @PostMapping("/editUser")
    public String deleteUser(@ModelAttribute("editRoleDto") EditUserDto editUserDto) {
        this.userService.updateUser(editUserDto);
        return "redirect:/admin/users/page/1";
    }
}
