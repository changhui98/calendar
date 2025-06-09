package com.office.calendar;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public String home() {
        log.debug("test - debug - home()");
        log.info("test - info - home()");
        log.warn("test - warn - home()");
        log.error("test - error - home()");

        return "home";
    }

}
