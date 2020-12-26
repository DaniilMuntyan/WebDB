package com.example.demo.service;

import com.example.demo.controllers.EndPoints;
import com.example.demo.domain.*;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.TaskRepository;
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
public class CollectorService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;

    @Autowired
    public CollectorService(TaskRepository taskRepository, UserService userService, OrderRepository orderRepository) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    public String doneTask(Long taskId, int pageNo, RedirectAttributes redirectAttributes) {
        Optional<Task> task = this.taskRepository.findById(taskId);
        if (task.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Task №" + taskId + " does not exist");
        } else {
            task.get().setTaskStatus(TaskStatus.DONE);
            this.taskRepository.save(task.get());

            Order order = task.get().getOrder();
            order.setOrderStatus(OrderStatus.DONE);
            this.orderRepository.save(order);

            redirectAttributes.addFlashAttribute("message", "Task №" + taskId + " is done");
        }
        return "redirect:" + EndPoints.COLLECTOR_MY_TASKS + "/page/" + pageNo;
    }

    public String acceptOrder(Long id, int pageNo, RedirectAttributes redirectAttributes) {
        Optional<Order> order = this.orderRepository.findById(id);
        if (order.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Order №" + id + " is already executing");
        } else {
            User collector = this.userService.getCurrentUser().get();
            order.get().setCollector(collector);
            order.get().setOrderStatus(OrderStatus.EXECUTING);
            this.orderRepository.save(order.get());

            Task task = new Task(order.get(), collector, TaskStatus.WORK);
            this.taskRepository.save(task);

            redirectAttributes.addFlashAttribute("message", "Order №" + id + " has been added to your tasks");
        }
        return "redirect:" + EndPoints.COLLECTOR_TASKS + "/page/" + pageNo;
    }

    public Page<Order> findPaginatedOrdersByStatus(int pageNo, int pageSize, String orderStatus) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.orderRepository.findAllByStatus(orderStatus, pageable);
    }

    public String findPaginatedCollectorOrders(int pageNo, Model model) {
        int pageSize = 8;
        Page<Order> page = this.findPaginatedOrdersByStatus(pageNo, pageSize,
                String.valueOf(OrderStatus.ACCEPTED.ordinal()));
        List<Order> listOrders = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOrders", listOrders);
        return "collector_tasks";
    }

    public Page<Task> findPaginatedMyTasks(int pageNo, int pageSize, Long id) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.taskRepository.findAllOrdersByCollectorId(id, pageable);
    }

    public String findPaginatedCollectorMyTasks(int pageNo, Model model) {
        User collector = this.userService.getCurrentUser().get();
        int pageSize = 8;
        Page<Task> page = this.findPaginatedMyTasks(pageNo, pageSize, collector.getUserId());
        List<Task> listTasks = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listTasks", listTasks);
        return "collector_my_tasks";
    }
}
