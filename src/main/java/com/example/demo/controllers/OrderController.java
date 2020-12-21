package com.example.demo.controllers;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.dto.NewOrderDto;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(EndPoints.USER_ORDERS)
    public String user_orders(Model model) {
        return findPaginatedUserOrders(1, model);
    }

    @GetMapping(EndPoints.USER_ORDERS_PAGE)
    public String findPaginatedUserOrders(@PathVariable (value = "pageNo") int pageNo, Model model) {
        int pageSize = 8;
        Page<Order> page = orderService.findPaginated(pageNo, pageSize);
        List<Order> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOrders", listOrders);
        return "user_order_list";
    }

    @GetMapping(EndPoints.NEW_ORDER_FORM)
    public String showNewOrderForm(Model model) {
        model.addAttribute("newOrderDto", orderService.getEmptyOrderDto());
        return "user_new_order";
    }

    @PostMapping(EndPoints.PROCESS_NEW_ORDER)
    public String processNewOrder(@Valid NewOrderDto newOrderDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        newOrderDto.setOrderStatus(OrderStatus.WAITING_FOR_CONFIRMATION);
        return this.orderService.newOrder(newOrderDto, bindingResult, redirectAttributes);
    }

    @GetMapping(EndPoints.SAVE_ORDER)
    public String saveOrder(@ModelAttribute("order") Order order) {
        this.orderService.save(order);
        return "redirect:" + EndPoints.USER_ORDERS;
    }

    @GetMapping(EndPoints.DELETE_ORDER)
    public String deleteOrder(@PathVariable("id") Long id) {
        this.orderService.deleteOrderById(id);
        return "redirect:" + EndPoints.USER_ORDERS;
    }

}
