package com.project3.project3.Controller;

import com.project3.project3.DTO.ThietBiDTO;
import com.project3.project3.Model.loai_thietbi;
import com.project3.project3.Model.thietbi;
import com.project3.project3.Model.thongtin_sudung;
import com.project3.project3.Service.ExcelService;
import com.project3.project3.Service.thongtinSuDungService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project3.project3.Service.thietbiService;
import com.project3.project3.Service.loaiThietBiService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/thietbi")
public class thietbiController {

    private final thietbiService thietbiService;
    private final loaiThietBiService loaiThietBiService;
    private final thongtinSuDungService thongtinSuDungService;
    private final ExcelService excelService;

    public thietbiController(thietbiService thietbiService,
                             loaiThietBiService loaiThietBiService,
                             thongtinSuDungService thongtinSuDungService,
                             ExcelService excelService) {
        this.thietbiService = thietbiService;
        this.loaiThietBiService = loaiThietBiService;
        this.thongtinSuDungService = thongtinSuDungService;
        this.excelService = excelService;
    }

    @GetMapping("/all")
    public String getAll(Model model){

        List<thietbi> dsThietBi = thietbiService.findAll();
        List<thietbi> dsRanh = thietbiService.findAvailable();
        List<thongtin_sudung> dsDangMuon = thongtinSuDungService.findBorrowing();

        model.addAttribute("dsThietBi", dsThietBi);
        model.addAttribute("dsRanh", dsRanh);
        model.addAttribute("dsDangMuon", dsDangMuon);

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

    @GetMapping("/deleteByCondition")
    public String deleteByConditon(){

        return "thietbi/delete-byCondition";
    }

    @PostMapping("/deleteByCondition")
    @ResponseBody
    public Map<String, String> deleteByCondition(@RequestParam("loaiThietBiId") Integer loaiThietBiId){

        Map<String, String> response = new HashMap<>();

        List<thietbi> dsTheoLoai = thietbiService.findByLoaiThietBi(loaiThietBiId);
        loai_thietbi loaiThietBi = loaiThietBiService.findById(loaiThietBiId);
        if(dsTheoLoai.isEmpty()){
            response.put("message", "Không còn thiết bị nào thuộc loại thiết bị: " + loaiThietBi.getTen() + "!");
            response.put("status", "fail");
            return response;
        }

        //cần xóa những thông tin sử dụng của thiết bị
        for(thietbi tb : dsTheoLoai){
            thongtinSuDungService.deleteByThietbiId(tb.getId());
        }
        thietbiService.deleteByLoaiThietBi(loaiThietBiId);


        response.put("message", "Đã xóa tất cả thiết bị thuộc loại: " + loaiThietBi.getTen() + "!");
        response.put("status", "success");

//        thietbiService.deleteByLoaiThietBiId(loaiThietBiId);

        return response;
    }

    @GetMapping("/excel")
    public String excel(){

        return "thietbi/excel";
    }

    @PostMapping("/excel")
    @ResponseBody
    public Map<String, String> excel(@RequestParam("file") MultipartFile file){

        Map<String, String> response = new HashMap<>();

        try{
            InputStream in = file.getInputStream();
            List<ThietBiDTO> tbDtos = excelService.parseExcelFileThietBi(in);
            thietbiService.saveList(tbDtos);
            response.put("message", "Đã thêm thành công " + tbDtos.size() + " thiết bị!");
            response.put("status", "success");
        }
        catch (Exception e){
            response.put("message", "Lỗi khi đọc file!");
            response.put("status", "fail");
            return response;
        }

        return response;
    }

}
