package ru.msu.cmc.prak.controllers;


import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.prak.DAO.BrandDAO;
import ru.msu.cmc.prak.DAO.ModelDAO;
import ru.msu.cmc.prak.entities.Brand;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ModelContoller {
    @Autowired
    private BrandDAO brandDAO;
    @Autowired
    private ModelDAO modelDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping(value = "/models")
    public String getModels(@NonNull Model model,
                            @RequestParam String brandName,
                            @RequestParam(required = false) String name) {
        if (name != null) {
            ru.msu.cmc.prak.entities.Model mod = modelDAO.getModelByName(brandName, name);
            if (mod == null) {
                model.addAttribute("modelList", modelDAO.getModelsByBrand(brandName));
            } else {
                List<ru.msu.cmc.prak.entities.Model> modelList = new ArrayList<>();
                modelList.add(mod);
                model.addAttribute("modelList", modelList);
            }
        } else {
            model.addAttribute("modelList", modelDAO.getModelsByBrand(brandName));
        }
        model.addAttribute("brand", brandName);
        return "models";
    }

    @PostMapping(value = "/models/add")
    public String postModelsAdd(@NonNull Model model,
                                @RequestParam String brandName,
                                @RequestParam String name) {
        Brand brand = brandDAO.getBrandByName(brandName);
        if (name == null) {
            return "redirect:../models?brandName=" + brandName;
        }
        if (modelDAO.getModelByName(brandName, name) != null) {
            return "redirect:../models?brandName=" + brandName;
        }

        ru.msu.cmc.prak.entities.Model mod = new ru.msu.cmc.prak.entities.Model(null, brand, name);
        modelDAO.save(mod);
        model.addAttribute("brand", brandName);

        return "redirect:../models?brandName=" + brandName;
    }

    @PostMapping(value = "/models/delete")
    public String postModelsDelete(@NonNull Model model, @RequestParam int id) {
        String brandName = modelDAO.getEntityById(id).getBrand().getName();
        modelDAO.delete(id);
        model.addAttribute("brand", brandName);
        return "redirect:../models?brandName=" + brandName;
    }

    @PostMapping(value = "/models/edit")
    public String postModelsEdit(@NonNull Model model,
                                 @RequestParam String brandName,
                                 @RequestParam String oldName,
                                 @RequestParam String newName) {
        model.addAttribute("brand", brandName);
        ru.msu.cmc.prak.entities.Model mod = modelDAO.getModelByName(brandName, oldName);
        if (mod == null) {
            return "redirect:../models?brandName=" + brandName;
        }
        ru.msu.cmc.prak.entities.Model mod1 = modelDAO.getModelByName(brandName, newName);
        if (mod1 != null && !mod1.equals(mod)) {
            return ChangeController.getError(model, 1, newName, oldName, brandName);
        }
        if (newName != null)
            if (!newName.isEmpty()) {
                mod.setName(newName);
            }
        modelDAO.update(mod);
        return "redirect:../models?brandName=" + brandName;
    }
}
