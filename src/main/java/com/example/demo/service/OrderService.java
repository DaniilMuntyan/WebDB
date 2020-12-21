package com.example.demo.service;

import com.example.demo.controllers.EndPoints;
import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import java.awt.Color;
import com.example.demo.dto.NewOrderDto;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findOrdersByUser(Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(User::getOrderList).orElse(null);
    }

    public Page<Order> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.orderRepository.findAll(pageable);
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

}
