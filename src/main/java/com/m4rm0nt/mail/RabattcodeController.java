package com.m4rm0nt.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.validator.routines.EmailValidator;

@Controller
public class RabattcodeController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/rabattcode-anfordern")
    @ResponseBody
    public ResponseEntity<String> rabattcodeAnfordern(@RequestParam String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            return ResponseEntity.badRequest().body("Ungültige E-Mail-Adresse.");
        }
        emailService.sendeRabattcodeEmail(email);
        return ResponseEntity.ok("<p>Rabattcode wurde an " + email + " gesendet!</p>");
    }

}