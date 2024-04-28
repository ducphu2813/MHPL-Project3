package com.project3.project3.Controller;

import com.project3.project3.Model.nganh;
import com.project3.project3.Service.nganhService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/nganh")
public class nganhController {

    nganhService nganhService;

    public nganhController(nganhService nganhService){
        this.nganhService = nganhService;
    }

    @RequestMapping("/all")
    public String getAll(Model model){
        List<nganh> dsNganh = nganhService.getAll();
        model.addAttribute("dsNganh", dsNganh);

        return "testNganh";
    }
}
