package ru.msu.cmc.prak.controllers;


import lombok.NonNull;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.prak.DAO.BrandDAO;
import ru.msu.cmc.prak.entities.Brand;

import java.util.*;

@Controller
public class BrandController {
    @Autowired
    private BrandDAO brandDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping(value = "/brands")
    public String getBrands(@NonNull Model model, @RequestParam(required = false) String name) {
        if (name != null) {
            Brand brand = brandDAO.getBrandByName(name);
            if (brand == null) {
                model.addAttribute("brandsList", brandDAO.getAll());
            } else {
                List<Brand> brandList  = new ArrayList<>();
                brandList.add(brand);
                model.addAttribute("brandsList", brandList);
            }
        } else {
            model.addAttribute("brandsList", brandDAO.getAll());
        }
        return "brands";
    }

    @GetMapping(value = "/brands/model/{id}")
    public String getModels(@NonNull Model model, @PathVariable int id) {
        Brand brand = brandDAO.getEntityById(id);
        model.addAttribute("brand", brand.getName());
        return "models";
    }

    @PostMapping(value = "/brands/add")
    public String postBrandsAdd(@RequestParam String name) {
        if (name == null) {
            return "redirect:../brands";
        }
        if (brandDAO.getBrandByName(name) != null) {
            return "redirect:../brands";
        }

        Brand author = new Brand(null, name);
        brandDAO.save(author);

        return "redirect:../brands";
    }

    @PostMapping(value = "/brands/delete")
    public String postBrandsDelete(@RequestParam int id) {
        brandDAO.delete(id);

        return "redirect:../brands";
    }

    @PostMapping(value = "/brands/edit")
    public String postBrandsEdit(@NonNull Model model,
                                 @RequestParam String oldName,
                                 @RequestParam String newName) {
        Brand brand = brandDAO.getBrandByName(oldName);
        if (brand == null) {
            return "redirect:../brands";
        }
        Brand brand1 = brandDAO.getBrandByName(newName);
        if (brand1 != null && !brand1.equals(brand)) {
            return ChangeController.getError(model, 0, newName, oldName, null);
        }
        if (newName != null)
            if (!newName.isEmpty()) {
                brand.setName(newName);
            }

        brandDAO.update(brand);

        return "redirect:../brands";
    }
}
