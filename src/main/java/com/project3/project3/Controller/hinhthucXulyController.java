package com.project3.project3.Controller;

import com.project3.project3.Service.hinhthucXuLyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hinhthuc")
public class hinhthucXulyController {

    hinhthucXuLyService hinhthucXuLyService;

    public hinhthucXulyController(hinhthucXuLyService hinhthucXuLyService){
        this.hinhthucXuLyService = hinhthucXuLyService;
    }

    @GetMapping("/all")
    public String getAll(Model model){

        model.addAttribute("dsHinhThuc", hinhthucXuLyService.getAll());

        return "test/test-hinhthucxuly";
    }
}
