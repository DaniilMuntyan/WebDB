package com.example.demo.service;

import com.example.demo.controllers.EndPoints;
import com.example.demo.domain.Notification;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.domain.User;
import com.example.demo.dto.NewOrderDto;
import com.example.demo.dto.ReleaseDto;
import com.example.demo.repositories.NotificationRepository;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final int pageSize = 8;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final NotificationRepository notificationRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService, NotificationRepository notificationRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.notificationRepository = notificationRepository;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    public String deleteOrder(Long id, int pageNo, RedirectAttributes redirectAttributes) {
        Order order = findOrderById(id).get();
        if (order.getOrderStatus() != OrderStatus.CONSIDERING &&
                order.getOrderStatus() != OrderStatus.ACCEPTED) {
            redirectAttributes.addFlashAttribute("error", "Order №" + id + " has already been accepted");
        } else {
            this.deleteOrderById(id);
            redirectAttributes.addFlashAttribute("message", "Order №" + id + " has been deleted successfully");
        }
        return "redirect:" + EndPoints.USER_ORDERS + "/page/" + pageNo;
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Page<Order> findPaginatedUsersById(int pageNo, int pageSize, Long userId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.orderRepository.findAllByUserId(userId, pageable);
    }

    public String findPaginatedUserOrders(int pageNo, Model model) {
        User currentUser = this.userService.getCurrentUser().get();
        Page<Order> page = this.findPaginatedUsersById(pageNo, this.pageSize, currentUser.getUserId());
        List<Order> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOrders", listOrders);
        return "user_order_list";
    }

    public NewOrderDto getEmptyOrderDto() {
        return new NewOrderDto(userService.getCurrentUser().get());
    }

    public Order dtoToOrder(NewOrderDto newOrderDto) {
        return new Order(newOrderDto);
    }

    private Optional<String> validateDimensions(String dimensions) {
        String[] numbers = dimensions.split("x");
        String exception = "Please enter correct value (AxBxC). A, B, C must be from 5 to 50 (cm)";
        if (numbers.length != 3) {
            return Optional.of(exception);
        }
        try {
            for (String n : numbers) {
                int x = Integer.parseInt(n);
                if (x < 5 || x > 50)
                    return Optional.of(exception);
            }
        } catch (Exception e) {
            return Optional.of(exception);
        }
        return Optional.empty();
    }

    public String newOrder(NewOrderDto newOrderDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Optional<String> validateDimensions = validateDimensions(newOrderDto.getDimensions());

        validateDimensions.ifPresent(s -> bindingResult.addError(new FieldError("newOrderDto",
                "dimensions", s)));

        if (bindingResult.hasErrors()) {
            return "user_new_order";
        }

        redirectAttributes.addFlashAttribute("message", "Success! Wait for order confirmation");
        Order order = dtoToOrder(newOrderDto);
        order.setUser(userService.getCurrentUser().get());
        this.save(order);
        return "redirect:" + EndPoints.USER_ORDERS;
    }

    public String acceptOrder(Long orderId, int pageNo, RedirectAttributes redirectAttributes) {
        Optional<Order> order = this.findOrderById(orderId);
        if (order.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Order №" + orderId + " does not exist");
        } else {
            if (order.get().getOrderStatus() == OrderStatus.ACCEPTED) {
                redirectAttributes.addFlashAttribute("error", "Order №" + orderId + " is in process");
            } else {
                redirectAttributes.addFlashAttribute("message", "Order №" + orderId + " was accepted");
                order.get().setOrderStatus(OrderStatus.ACCEPTED);
                
                this.save(order.get());
            }
        }
        return "redirect:" + EndPoints.ADMIN_ORDERS + "/page/" + pageNo;
    }

    public String rejectOrder(Long orderId, int pageNo, RedirectAttributes redirectAttributes) {
        Optional<Order> order = this.findOrderById(orderId);
        if (order.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Order №" + orderId + " does not exist");
        } else {
            if (order.get().getOrderStatus() == OrderStatus.REJECTED) {
                redirectAttributes.addFlashAttribute("error", "Order №" + orderId + " is rejected");
            } else {
                redirectAttributes.addFlashAttribute("message", "Order №" + orderId + " was rejected");
                order.get().setOrderStatus(OrderStatus.REJECTED);
                this.save(order.get());
            }
        }
        return "redirect:" + EndPoints.ADMIN_ORDERS + "/page/" + pageNo;
    }

    public String releaseOrder(Long orderId, int pageNo, ReleaseDto releaseDto, RedirectAttributes redirectAttributes) {
        Optional<Order> order = this.orderRepository.findById(orderId);
        if (order.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Order №" + orderId + " does not exist");
        } else {
            Notification notification = new Notification(order.get().getUser(), order.get(), releaseDto.getName(),
                    releaseDto.getEmail(), releaseDto.getMessage());
            order.get().setOrderStatus(OrderStatus.RELEASE);

            this.notificationRepository.save(notification);
            this.orderRepository.save(order.get());

            redirectAttributes.addFlashAttribute("message", "Success! Order №" + orderId + " has been released" +
                    " (see notifications)");
        }
        return "redirect:" + EndPoints.ADMIN_ORDERS + "/page/" + pageNo;
    }
}
