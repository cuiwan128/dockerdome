package com.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {
    static Log log = LogFactory.getLog(DockerController.class);

    @RequestMapping("/")
    public String index() {
        log.info("Hello Docker!");
        return "Hello Docker!";
    }

    @RequestMapping("/test")
    public String test() {
        log.info("Hello Docker test!");
        return "Hello Docker test!";
    }

    @RequestMapping("/demo")
    public String demo() {
        log.info("Hello Docker demo!");
        return "Hello Docker demo!";
    }

    @RequestMapping("/ok")
    public String ok() {
        log.info("Hello Docker ok!");
        return "Hello Docker ok!";
    }

}
