package com.p.srcm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class Routes {

    @RequestMapping({
        "/"
        // ,
        // "/bikes",
        // "/milages",
        // "/gallery",
        // "/tracks",
        // "/tracks/{id:\\w+}",
        // "/location",
        // "/about"
    })
    public String index() {
        return "forward:/index.html";
    }
}