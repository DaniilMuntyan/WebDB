package com.example.demo.controllers;

public class EndPoints {
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String PROCESS_REGISTER = "/process_register";

    public static final String ADMIN_USERS = "/admin/users";
    public static final String ADMIN_USERS_PAGE = "/admin/users/page/{pageNo}";
    public static final String ADMIN_UPDATE_USER = "/admin/showFormForUpdate/{id}";
    public static final String ADMIN_EDIT_USER = "/admin/editUser";
    public static final String ADMIN_DELETE_USER = "/admin/deleteUser/{id}";
    public static final String ADMIN_ORDERS = "/admin/orders";
    public static final String ADMIN_ORDERS_PAGE = "/admin/orders/page/{pageNo}";
    public static final String ADMIN_ACCEPT_ORDER = "/admin/orders/page/{pageNo}/acceptOrder/{id}";
    public static final String ADMIN_REJECT_ORDER = "/admin/orders/page/{pageNo}/rejectOrder/{id}";
    public static final String ADMIN_RELEASE_FORM = "/admin/orders/page/{pageNo}/releaseForm/{id}";
    public static final String ADMIN_RELEASE_PROCESS = "/admin/orders/page/{pageNo}/releaseOrder/{id}";
    public static final String ADMIN_NOTIFICATIONS = "/admin/notifications";
    public static final String ADMIN_NOTIFICATIONS_PAGE = "/admin/notifications/page/{pageNo}";
    public static final String ADMIN_NOTIFICATION_DELETE = "/admin/notifications/page/{pageNo}/delete/{id}";

    public static final String USER_ORDERS = "/user/orders";
    public static final String USER_ORDERS_PAGE = "/user/orders/page/{pageNo}";
    public static final String PROCESS_NEW_ORDER = "/user/process_new_order"; 
    public static final String NEW_ORDER_FORM = "/user/showNewOrderForm";
    public static final String SAVE_ORDER = "/user/saveOrder";
    public static final String DELETE_ORDER = "/user/orders/page/{pageNo}/deleteOrder/{id}";
    public static final String USER_NOTIFICATIONS = "/user/notifications";
    public static final String USER_NOTIFICATIONS_PAGE = "/user/notifications/page/{pageNo}";

    public static final String COLLECTOR_TASKS = "/collector/tasks";
    public static final String COLLECTOR_MY_TASKS = "/collector/myTasks";
    public static final String COLLECTOR_TASKS_PAGE = "/collector/tasks/page/{pageNo}";
    public static final String COLLECTOR_ACCEPT_TASK = "/collector/tasks/page/{pageNo}/acceptOrder/{id}";
    public static final String COLLECTOR_MY_TASKS_PAGE = "/collector/myTasks/page/{pageNo}";
    public static final String COLLECTOR_MY_TASK_DONE = "/collector/myTasks/page/{pageNo}/doneTask/{id}";

    public static final String USER_FORM_EDIT_PROFILE = "/user/formEditProfile";
    public static final String USER_EDIT_PROFILE = "/user/{id}/editProfile";
}
