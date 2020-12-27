package com.example.demo.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public ModelAndView errorPage(HttpServletRequest httpServletRequest) {
        int errorCode = getErrorCode(httpServletRequest);
        if (errorCode == 404)
            return new ModelAndView("error/404");
        ModelAndView page = new ModelAndView("error");
        String msg = "Something went wrong";
        String comment = "Please, try again later";
        switch(errorCode) {
            case 400: {
                msg = "Http Error Code: 400. Bad Request";
                comment = "";
                break;
            }
            case 401: {
                msg = "Http Error Code: 401. Unauthorized";
                comment = "You need to login on site";
                break;
            }
            case 403: {
                msg = "Http Error Code: 403. Forbidden";
                comment = "Check your role or call on manager, if necessary";
                break;
            }
            case 500: {
                msg = "Http Error Code: 500. Internal Server Error";
                comment = "Please, try again later";
                break;
            }
        }
        page.addObject("errorMsg", msg);
        page.addObject("comment", comment);
        return page;
    }

    private int getErrorCode(HttpServletRequest httpServletRequest) {
        return (Integer) httpServletRequest.getAttribute("javax.servlet.error.status_code");
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
