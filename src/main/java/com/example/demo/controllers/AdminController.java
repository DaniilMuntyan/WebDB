package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.dto.EditUserDto;
import com.example.demo.dto.ReleaseDto;
import com.example.demo.service.AdminService;
import com.example.demo.service.NotificationService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final UserService userService;
    private final OrderService orderService;
    private final NotificationService notificationService;
    private final AdminService adminService;

    @Autowired
    public AdminController(UserService userService, OrderService orderService, NotificationService notificationService, AdminService adminService) {
        this.userService = userService;
        this.orderService = orderService;
        this.notificationService = notificationService;
        this.adminService = adminService;
    }

    @GetMapping(EndPoints.ADMIN_ORDERS)
    public String admin_orders(Model model) {
        return findPaginatedAdminOrders(1, model);
    }

    @GetMapping(EndPoints.ADMIN_ORDERS_PAGE)
    public String findPaginatedAdminOrders(@PathVariable (value = "pageNo") int pageNo, Model model) {
        return this.adminService.findPaginatedAdminOrders(pageNo, model);
    }

    @GetMapping(EndPoints.ADMIN_USERS)
    public String list_users(Model model) {
        return findPaginatedAdminUsers(1, model);
    }

    @GetMapping(EndPoints.ADMIN_USERS_PAGE)
    public String findPaginatedAdminUsers(@PathVariable (value = "pageNo") int pageNo, Model model) {
        return this.adminService.findPaginatedAdminUsers(pageNo, model);
    }

    @GetMapping(EndPoints.ADMIN_UPDATE_USER)
    public String showFormForUpdate(@PathVariable (value="id") Long id, Model model) {
        return this.adminService.showFormForUpdate(id, model);
    }

    @GetMapping(EndPoints.ADMIN_EDIT_USER)
    public String editUser(@ModelAttribute("editRoleDto") EditUserDto editUserDto, RedirectAttributes redirectAttributes) {
        return this.userService.updateUser(editUserDto, redirectAttributes);
    }

    @GetMapping(EndPoints.ADMIN_DELETE_USER)
    public String deleteUser(@PathVariable (value = "id") long id) {
        return this.userService.deleteUserById(id);
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

    @GetMapping(EndPoints.ADMIN_RELEASE_FORM)
    public String releaseForm(@PathVariable (value = "id") long orderId, @PathVariable (value = "pageNo") int pageNo,
                              Model model) {
        return this.adminService.releaseForm(orderId, pageNo, model);
    }

    @PostMapping(EndPoints.ADMIN_RELEASE_PROCESS)
    public String releaseOrder(@Valid ReleaseDto releaseDto, @PathVariable (value = "id") long orderId,
                               @PathVariable (value = "pageNo") int pageNo,
                               RedirectAttributes redirectAttributes) {
        return this.orderService.releaseOrder(orderId, pageNo, releaseDto, redirectAttributes);
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
