package exercise.controller;

import exercise.daytime.Day;
import exercise.daytime.Daytime;
import exercise.daytime.Night;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
public class WelcomeController {

    @Autowired
    Daytime getTrueBean;

    @GetMapping("/welcome")
    public String welcome() {
        return "It is " + getTrueBean.getName() + " now! Welcome to Spring!";
    }
}
// END
