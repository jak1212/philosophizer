package org.philosophizer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private static final Logger log = LoggerFactory.getLogger(SignupController.class);


    @GetMapping
    public String getSignupPage(){
        log.info("User visited signup page");

            return "signup";

    }

    @GetMapping("/thank-you")
    public String thankYou() {
        log.info("rendering thank you page");
        return "thank-you";
    }

}
