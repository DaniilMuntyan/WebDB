package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.dto.EditUserDto;
import com.example.demo.service.NotificationService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final UserService userService;
    private final OrderService orderService;
    private final NotificationService notificationService;

    @Autowired
    public AdminController(UserService userService, OrderService orderService, NotificationService notificationService) {
        this.userService = userService;
        this.orderService = orderService;
        this.notificationService = notificationService;
    }

    @GetMapping(EndPoints.ADMIN_ORDERS)
    public String admin_orders(Model model) {
        return findPaginatedAdminOrders(1, model);
    }

    @GetMapping(EndPoints.ADMIN_ORDERS_PAGE)
    public String findPaginatedAdminOrders(@PathVariable (value = "pageNo") int pageNo, Model model) {
        return this.orderService.findPaginatedAdminOrders(pageNo, model);
    }

    @GetMapping(EndPoints.ADMIN_USERS)
    public String list_users(Model model) {
        return findPaginatedAdminUsers(1, model);
    }

    @GetMapping(EndPoints.ADMIN_USERS_PAGE)
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

    @GetMapping("/admin/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable (value="id") Long id, Model model) {
        Optional<User> user = userService.findUserById(id);
        model.addAttribute("editUserDto", userService.userToDto(user.get()));
        return "update_user";
    }

    @PostMapping("/admin/editUser")
    public String deleteUser(@ModelAttribute("editRoleDto") EditUserDto editUserDto) {
        this.userService.updateUser(editUserDto);
        return "redirect:/admin/users/page/1";
    }

    @GetMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable (value = "id") long id) {
        this.userService.deleteUserById(id);
        return "redirect:/";
    }

    @GetMapping(EndPoints.ADMIN_ACCEPT_ORDER)
    public String acceptOrder(@PathVariable (value = "id") long orderId, @PathVariable (value = "pageNo") int pageNo,
                              RedirectAttributes redirectAttributes) {
        return this.orderService.acceptOrder(orderId, pageNo, redirectAttributes);
    }

    @GetMapping(EndPoints.ADMIN_REJECT_ORDER)
    public String rejectOrder(@PathVariable (value = "id") long orderId, @PathVariable (value = "pageNo") int pageNo,
                              RedirectAttributes redirectAttributes) {
        return this.orderService.rejectOrder(orderId, pageNo, redirectAttributes);
    }

    @GetMapping(EndPoints.ADMIN_RELEASE_ORDER)
    public String releaseOrder(@PathVariable (value = "id") long orderId, @PathVariable (value = "pageNo") int pageNo,
                              RedirectAttributes redirectAttributes) {
        return this.orderService.releaseOrder(orderId, pageNo, redirectAttributes);
    }

    @GetMapping(EndPoints.ADMIN_NOTIFICATIONS)
    public String list_notifications(Model model) {
        return findPaginatedAdminNotifications(1, model);
    }

    @GetMapping(EndPoints.ADMIN_NOTIFICATIONS_PAGE)
    public String findPaginatedAdminNotifications(@PathVariable (value = "pageNo") int pageNo,
                                                  Model model) {
        return this.notificationService.findPaginatedAdminNotifications(pageNo, model);
    }

    @GetMapping(EndPoints.ADMIN_NOTIFICATION_DELETE)
    public String findPaginatedAdminNotifications(@PathVariable (value = "id") Long notificationId,
                                                  @PathVariable (value = "pageNo") int pageNo,
                                                  RedirectAttributes redirectAttributes) {
        return this.notificationService.notificationDelete(notificationId, pageNo, redirectAttributes);
    }
}
