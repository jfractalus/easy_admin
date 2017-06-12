package com.volia.eadmin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;

@Slf4j
@Controller
public class MainController {
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String mainView() throws IOException {
        return "main/base-tpl";
    }
}
