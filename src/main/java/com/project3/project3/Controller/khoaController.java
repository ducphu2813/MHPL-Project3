package com.project3.project3.Controller;

import com.project3.project3.Model.khoa;
import com.project3.project3.Service.khoaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
@Controller
@RequestMapping("/khoa")
public class khoaController {

    khoaService khoaService;

    public khoaController(khoaService khoaService){
        this.khoaService = khoaService;
    }

    @GetMapping("/all")
    public String getAll(Model model){
        List<khoa> dsKhoa = khoaService.getAll();
        model.addAttribute("dsKhoa", dsKhoa);

        return "testKhoa";
    }

}
