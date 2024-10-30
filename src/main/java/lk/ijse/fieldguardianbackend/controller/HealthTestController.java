package lk.ijse.fieldguardianbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/health-test")
public class HealthTestController {
    @GetMapping
    public String healthCheck(Model model) {
        model.addAttribute("message", "Field Guardian BACKEND is RUNNING!!");
        return "health-check";
    }
}
