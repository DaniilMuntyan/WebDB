package com.example.demo.controllers;

import com.example.demo.service.CollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CollectorController {
    private final CollectorService collectorService;

    @Autowired
    public CollectorController(CollectorService collectorService) {
        this.collectorService = collectorService;
    }

    @GetMapping(EndPoints.COLLECTOR_TASKS)
    public String list_tasks(Model model) {
        return findPaginatedCollectorOrders(1, model);
    }

    @GetMapping(EndPoints.COLLECTOR_TASKS_PAGE)
    public String findPaginatedCollectorOrders(@PathVariable(value = "pageNo") int pageNo, Model model) {
        return this.collectorService.findPaginatedCollectorOrders(pageNo, model);
    }

    @GetMapping(EndPoints.COLLECTOR_ACCEPT_TASK)
    public String acceptTask(@PathVariable(value = "id") Long id, @PathVariable(value = "pageNo") int pageNo,
                             RedirectAttributes redirectAttributes) {
        return this.collectorService.acceptOrder(id, pageNo, redirectAttributes);
    }

    @GetMapping(EndPoints.COLLECTOR_MY_TASKS)
    public String list_my_tasks(Model model) {
        return findPaginatedCollectorMyTasks(1, model);
    }

    @GetMapping(EndPoints.COLLECTOR_MY_TASKS_PAGE)
    public String findPaginatedCollectorMyTasks(@PathVariable(value = "pageNo") int pageNo, Model model) {
        return this.collectorService.findPaginatedCollectorMyTasks(pageNo, model);
    }

    @GetMapping(EndPoints.COLLECTOR_MY_TASK_DONE)
    public String doneTask(@PathVariable(value = "id") Long taskId, @PathVariable(value = "pageNo") int pageNo,
                           RedirectAttributes redirectAttributes) {
        return this.collectorService.doneTask(taskId, pageNo, redirectAttributes);
    }
}
