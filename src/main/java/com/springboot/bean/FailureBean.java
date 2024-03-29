package com.springboot.bean;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 配置异常时统一跳转页面
 */
@Controller
public class FailureBean implements ErrorController {

    @RequestMapping(value = "/error")
    public String actionFailure() {
        return getErrorPath();
    }

    public String getErrorPath() {
        return "common/failure";
    }

}
