package com.project3.project3.Controller;

import com.project3.project3.DTO.ThietBiDTO;
import com.project3.project3.Model.loai_thietbi;
import com.project3.project3.Model.thietbi;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project3.project3.Service.thietbiService;
import com.project3.project3.Service.loaiThietBiService;

import java.util.List;

@Controller
@RequestMapping("/thietbi")
public class thietbiController {

    thietbiService thietbiService;
    loaiThietBiService loaiThietBiService;

    public thietbiController(thietbiService thietbiService, loaiThietBiService loaiThietBiService) {
        this.thietbiService = thietbiService;
        this.loaiThietBiService = loaiThietBiService;
    }

    @GetMapping("/all")
    public String getAll(Model model){
        List<thietbi> dsThietBi = thietbiService.findAll();

        model.addAttribute("dsThietBi", dsThietBi);

        return "layout/layout";
    }

    @ModelAttribute("dsLoaiThietBi")
    public List<loai_thietbi> getLoaiThietBiList() {
        return loaiThietBiService.findAll();
    }

    @GetMapping("/add")
    public String showAddForm(Model model){

        model.addAttribute("tb", new ThietBiDTO(null, null, null, null, null));

        return "thietbi/formThietBi";
    }

    @GetMapping("/search")
    public String search(HttpSession session, @RequestParam("ten") String ten, Model model){

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            return "redirect:/thanhvien/login";
        }

        //chỉ lấy những thiết bị đang không được mượn
        List<thietbi> dsThietBi = thietbiService.findNotBorrowedByName(ten);

        model.addAttribute("dsThietBi", dsThietBi);

        return "thanhvien/thanhvien-muon";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("tbId") Integer tbId, Model model){

        thietbi tb = thietbiService.findById(tbId);

        ThietBiDTO tbDTO = new ThietBiDTO(tb.getId(), tb.getTen(), tb.getMota(), tb.getLoai_thietbi().getId(), tb.getLoai_thietbi().getTen());

        model.addAttribute("tb", tbDTO);

        return "thietbi/formThietBi";
    }

    @PostMapping("/save")
    public String create(@ModelAttribute("tb") ThietBiDTO thietBiDTO){

        thietbiService.save(thietBiDTO);

        return "redirect:/thietbi/all";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("tbId") Integer tbId){

        thietbiService.deleteById(tbId);

        return "redirect:/thietbi/all";
    }


}
