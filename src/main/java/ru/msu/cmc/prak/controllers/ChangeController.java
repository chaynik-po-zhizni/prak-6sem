package ru.msu.cmc.prak.controllers;

import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangeController {


    public static String getError(@NonNull Model model, int value, String newValue, String oldValue, String brandName) {
        String changeValue = null, preValue = null;
        if (value == 0) {
            changeValue = "название бренда";
            preValue = "бренд с таким названием";
        }
        if (value == 1) {
            changeValue = "название модели";
            preValue = "модель бренда " + brandName + " с таким названием";
        }
        if (value == 2) {
            changeValue = "номер телефона";
            preValue = "клиент с таким номером телефона";
        }
        model.addAttribute("changeValue", changeValue);
        model.addAttribute("preValue", preValue);
        model.addAttribute("newValue", newValue);
        model.addAttribute("oldValue", oldValue);
        return "noUniq";
    }
}
