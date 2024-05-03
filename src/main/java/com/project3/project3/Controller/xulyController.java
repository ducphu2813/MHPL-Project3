package com.project3.project3.Controller;

import com.project3.project3.Model.hinhthuc_xuly;
import com.project3.project3.Model.thanhvien;
import com.project3.project3.Model.xuly;
import com.project3.project3.Service.hinhthucXuLyService;
import com.project3.project3.Service.thanhvienService;
import com.project3.project3.Service.xulyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/xuly")
public class xulyController {

    private final xulyService xulyService;
    private final hinhthucXuLyService hinhthucXuLyService;
    private final thanhvienService thanhvienService;
    public xulyController(xulyService xulyService,
                          hinhthucXuLyService hinhthucXuLyService,
                          thanhvienService thanhvienService) {
        this.xulyService = xulyService;
        this.hinhthucXuLyService = hinhthucXuLyService;
        this.thanhvienService = thanhvienService;
    }

    @ModelAttribute("dsHinhThuc")
    public List<hinhthuc_xuly> getHinhThuc(){
        return hinhthucXuLyService.getAll();
    }

    @GetMapping("/all")
    public String getAll(Model model){

        model.addAttribute("section", "xuly");

        model.addAttribute("dsXuly", xulyService.getAll());

        model.addAttribute("dsXulyFalse", xulyService.getXulyFalse());

        model.addAttribute("dsXulyTrue", xulyService.getXulyTrue());


        return "layout/layout";
    }

    @GetMapping("/add")
    public String xulyForm(Model model){

        return "xuly/xuly-form";
    }

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Map> addXuly(Model model,
                                       @RequestParam("tvId") String tvId,
                                       @RequestParam("htxlId") String htxlId,
                                       @RequestParam("tienphat") String tienphat,
                                       @RequestParam("lydo") String lydo){

        Map<String, String> tvResponse = new HashMap<>();
        Map<String, String> lydoResponse = new HashMap<>();
        Map<String, String> tienphatResponse = new HashMap<>();
        Map<String, Map> response = new HashMap<>();

        try{
            Long tvIdLong = Long.parseLong(tvId);
            thanhvien thanhvien = thanhvienService.findById(tvIdLong);
            tvResponse.put("status", "success");
            tvResponse.put("info", "Thành viên: " + thanhvien.getTen() + " - " + thanhvien.getId());
            //check lý do
            if(lydo == null || lydo.isEmpty()){
                lydoResponse.put("status", "error");
                lydoResponse.put("message", "Lý do không được để trống");
            }
            else{
                lydoResponse.put("status", "success");
            }

            //check tiền phạt
            if(Double.parseDouble(tienphat) < 0){
                tienphatResponse.put("status", "error");
                tienphatResponse.put("message", "Tiền phạt không được âm");
            }
            else{
                tienphatResponse.put("status", "success");
            }
        }
        catch (NoSuchElementException e) {
            tvResponse.put("status", "error");
            tvResponse.put("message", "Không tìm thấy thành viên");
        }
        catch (NumberFormatException e) {
            tvResponse.put("status", "error");
            tvResponse.put("message", "Mã chỉ chứa số, không chứa kí tự khác và không được bỏ trống");
        }

        response.put("tvResponse", tvResponse);
        response.put("lydoResponse", lydoResponse);
        response.put("tienphatResponse", tienphatResponse);

        if(tvResponse.get("status").equals("success") && lydoResponse.get("status").equals("success") && tienphatResponse.get("status").equals("success")){
            xuly xuly = new xuly();
            xuly.setThanhvien(thanhvienService.findById(Long.parseLong(tvId)));
            xuly.setHinhthuc_xuly(hinhthucXuLyService.findById(Integer.parseInt(htxlId)));
            xuly.setTienphat(Double.parseDouble(tienphat));
            xuly.setLydo(lydo);
//            xulyService.saveOrUpdate(xuly);
            System.out.println(xuly.getThanhvien().getTen());
            System.out.println(xuly.getHinhthuc_xuly().getHinhthuc());
            System.out.println(xuly.getTienphat());
            System.out.println(xuly.getLydo());
        }


//        response.put("tvId", tvId);
//        response.put("htxlId", htxlId);
//        response.put("tienphat", tienphat);
//        response.put("lydo", lydo);

        return response;

    }

    @GetMapping("/handle")
    public String handleXuly(@RequestParam("id") String id, Model model){

        xuly xuly = xulyService.getById(Integer.parseInt(id));

        xuly.setTrangthai(true);

        xulyService.saveOrUpdate(xuly);

        return "redirect:/xuly/all";
    }

    @GetMapping("/delete")
    public String deleteXuly(@RequestParam("id") String id, Model model){

        xulyService.delete(Integer.parseInt(id));

        return "redirect:/xuly/all";
    }
}
