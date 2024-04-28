package com.project3.project3.Controller;

import com.project3.project3.DTO.ThanhVienDTO;
import com.project3.project3.Model.thietbi;
import com.project3.project3.Model.thongtin_sudung;
import com.project3.project3.Service.thanhvienService;
import com.project3.project3.Service.thietbiService;
import com.project3.project3.Service.thongtinSuDungService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ttsd")
public class thongtinSuDungController {

    thongtinSuDungService thongtinSuDungService;

    thietbiService thietbiService;

    thanhvienService thanhvienService;

    public thongtinSuDungController(thongtinSuDungService thongtinSuDungService, thietbiService thietbiService, thanhvienService thanhvienService) {
        this.thongtinSuDungService = thongtinSuDungService;
        this.thietbiService = thietbiService;
        this.thanhvienService = thanhvienService;
    }

    @PostMapping("/book")
    @ResponseBody
    public Map<String, String> book(@RequestParam("tbId") Integer tbId,
                       @RequestParam("tg_datcho") LocalDateTime tg_datcho,
                       HttpSession session, Model model){
        Map<String, String> response = new HashMap<>();
        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            response.put("status", "notloggedin");
            response.put("message", "Bạn cần đăng nhập để đặt chỗ");
            return response;
        }

        ThanhVienDTO tvDTO = (ThanhVienDTO) session.getAttribute("thanhvien");

        System.out.println("tb id : "+tbId);
        System.out.println("tg datcho : "+tg_datcho);

        if(thongtinSuDungService.checkDatCho(tbId, tg_datcho)){
            System.out.println("Đặt chỗ thành công");
        }

        if(thongtinSuDungService.checkDatCho(tbId, tg_datcho)){
            thongtin_sudung tsd = new thongtin_sudung();
            tsd.setTg_datcho(tg_datcho);
            tsd.setThanhvien(thanhvienService.findById(tvDTO.getId()));
            tsd.setThietbi(thietbiService.findById(tbId));
            thongtinSuDungService.save(tsd);

            response.put("tbid", tbId.toString());
            response.put("status", "success");
            response.put("message", "Đặt chỗ thành công");
        } else {
            response.put("tbid", tbId.toString());
            response.put("status", "failed");
            response.put("message", "Thiết bị đã được đặt chỗ vào ngày này");
        }


        return response;
    }
}
