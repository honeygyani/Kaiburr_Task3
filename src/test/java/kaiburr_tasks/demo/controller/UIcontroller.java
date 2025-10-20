package kaiburr_tasks.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIcontroller {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Kaiburr Task 1");
        model.addAttribute("message", "Welcome to the Kaiburr Task 1 Application!");
        model.addAttribute("instructions", "Use the REST API to manage and execute tasks stored in MongoDB.");
        return "index"; // Renders index.html from templates folder
    }
}
