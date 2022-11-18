package pl.woelke.garbageapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.woelke.garbageapi.config.LoginCredentials;

@Controller
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials credentials) {
    }
}
