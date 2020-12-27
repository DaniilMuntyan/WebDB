package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.dto.ReleaseDto;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public final class AdminService {
    private final int pageSize = 8;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public AdminService(UserRepository userRepository, OrderRepository orderRepository, UserService userService, OrderService orderService) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    public Page<Order> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.orderRepository.findAll(pageable);
    }

    public String findPaginatedAdminOrders(int pageNo, Model model) {
        Page<Order> page = this.findPaginated(pageNo, this.pageSize);
        List<Order> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOrders", listOrders);
        return "admin_orders";
    }

    public String findPaginatedAdminUsers(int pageNo, Model model) {
        Page<User> page = userService.findPaginated(pageNo, pageSize);
        List<User> listUsers = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        return "admin_user_list";
    }
    
    public String showFormForUpdate(Long id, Model model) {
        Optional<User> user = userService.findUserById(id);
        model.addAttribute("editUserDto", userService.userToDto(user.get()));
        return "update_user";
    }

    public String releaseForm(long orderId, int pageNo, Model model) {
        model.addAttribute("releaseDto", new ReleaseDto());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("id", orderId);
        return "admin_release";
    }

}
