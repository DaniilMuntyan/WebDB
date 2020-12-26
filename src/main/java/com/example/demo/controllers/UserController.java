package com.example.demo.controllers;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.domain.User;
import com.example.demo.dto.EditUserDto;
import com.example.demo.dto.NewOrderDto;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public UserController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping(EndPoints.USER_ORDERS)
    public String user_orders(Model model) {
        return findPaginatedUserOrders(1, model);
    }

    @GetMapping(EndPoints.USER_ORDERS_PAGE)
    public String findPaginatedUserOrders(@PathVariable(value = "pageNo") int pageNo, Model model) {
        return this.orderService.findPaginatedUserOrders(pageNo, model);
    }

    @GetMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        this.userService.save(user);
        return "redirect:/page/1";
    }

    @GetMapping(EndPoints.NEW_ORDER_FORM)
    public String showNewOrderForm(Model model) {
        model.addAttribute("newOrderDto", orderService.getEmptyOrderDto());
        return "user_new_order";
    }

    @PostMapping(EndPoints.PROCESS_NEW_ORDER)
    public String processNewOrder(@Valid NewOrderDto newOrderDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        newOrderDto.setOrderStatus(OrderStatus.CONSIDERING);
        return this.orderService.newOrder(newOrderDto, bindingResult, redirectAttributes);
    }

    @GetMapping(EndPoints.SAVE_ORDER)
    public String saveOrder(@ModelAttribute("order") Order order) {
        this.orderService.save(order);
        return "redirect:" + EndPoints.USER_ORDERS;
    }

    @GetMapping(EndPoints.DELETE_ORDER)
    public String deleteOrder(@PathVariable("id") Long id, @PathVariable("pageNo") int pageNo, RedirectAttributes redirectAttributes) {
        System.out.println("DELETE ORDER");
        return this.orderService.deleteOrder(id, pageNo, redirectAttributes);
    }
}
