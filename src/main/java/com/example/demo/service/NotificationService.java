package com.example.demo.service;

import com.example.demo.controllers.EndPoints;
import com.example.demo.domain.Notification;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderStatus;
import com.example.demo.domain.User;
import com.example.demo.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final int pageSize = 8;
    private final OrderService orderService;
    private final UserService userService;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(OrderService orderService, UserService userService, NotificationRepository notificationRepository) {
        this.orderService = orderService;
        this.userService = userService;
        this.notificationRepository = notificationRepository;
    }

    public Page<Notification> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.notificationRepository.findAll(pageable);
    }

    public String findPaginatedAdminNotifications(int pageNo, Model model) {
        Page<Notification> page = this.findPaginated(pageNo, this.pageSize);
        List<Notification> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listNotifications", listOrders);
        return "admin_notifications";
    }

    public Page<Notification> findPaginatedForUser(Long id, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.notificationRepository.findAllByUserId(id, pageable);
    }

    public String findPaginatedUserNotifications(int pageNo, Model model) {
        User user = this.userService.getCurrentUser().get();
        Page<Notification> page = this.findPaginatedForUser(user.getUserId(), pageNo, this.pageSize);
        List<Notification> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listNotifications", listOrders);
        return "user_notifications";
    }

    public String notificationDelete(Long notificationId, int pageNo, RedirectAttributes redirectAttributes) {
        Optional<Notification> notification = this.notificationRepository.findById(notificationId);
        if (notification.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Notification №" + notificationId + " does not exist");
        } else {
            Order order = notification.get().getOrder();

            this.notificationRepository.deleteById(notification.get().getNotificationId());

            order.setOrderStatus(OrderStatus.DONE);
            this.orderService.save(order);

            redirectAttributes.addFlashAttribute("message", "Notification №" + notificationId + " has been " +
                    "successfully deleted. Please, check 'Orders' page and release it again if necessary");
        }
        return "redirect:" + EndPoints.ADMIN_NOTIFICATIONS + "/page/" + pageNo;
    }
}
