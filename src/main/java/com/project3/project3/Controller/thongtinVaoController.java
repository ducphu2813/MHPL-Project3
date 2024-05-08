package com.project3.project3.Controller;

import com.project3.project3.Service.thongtinVaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ttv")
public class thongtinVaoController {

    private final thongtinVaoService thongtinVaoService;

    public thongtinVaoController(com.project3.project3.Service.thongtinVaoService thongtinVaoService) {
        this.thongtinVaoService = thongtinVaoService;
    }

    @GetMapping("/analysis")
    public String analysis(Model model){

        HashMap<Integer, Integer> data = thongtinVaoService.getData(2023);

        model.addAttribute("data", data);

        return "thongke/thongkeVao";
    }

    @PostMapping("/analysis")
    @ResponseBody
    public HashMap<Integer, Integer> analysis(@RequestBody Map<String, Integer> payload){
        int year = payload.get("year");
        HashMap<Integer, Integer> data = thongtinVaoService.getData(year);
        return data;
    }
    //xử dụng @RequestBody để chuyển đổi json thành object
}
