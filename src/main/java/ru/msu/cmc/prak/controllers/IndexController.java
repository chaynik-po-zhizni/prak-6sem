package ru.msu.cmc.prak.controllers;

import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @GetMapping(value = {"/index", "/"})
    public String getIndex() {
        return "index";
    }
}
