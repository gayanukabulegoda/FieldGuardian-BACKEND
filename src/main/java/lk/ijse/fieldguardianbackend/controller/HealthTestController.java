package lk.ijse.fieldguardianbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Controller for handling health check requests.
 * This controller provides an endpoint to verify if the application is running.
 */
@Slf4j
@Controller
@RequestMapping("/api/v1/health-test")
public class HealthTestController {
    /**
     * {@code GET /api/v1/health-test} : Perform a health check.
     *
     * @param model the model to which the health check message is added
     * @return the name of the view to be rendered
     */
    @GetMapping
    public String healthCheck(Model model) {
        log.info("Health check endpoint accessed");
        model.addAttribute("message", "Field Guardian BACKEND is RUNNING!!");
        log.debug("Health check message set in model");
        return "health-check";
    }
}