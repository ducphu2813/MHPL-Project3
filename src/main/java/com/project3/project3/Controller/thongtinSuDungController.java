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
import java.util.NoSuchElementException;

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

    @GetMapping("/all")
    public String getAll(Model model){

        model.addAttribute("section", "ttsd");

        //danh sách tất cả
        model.addAttribute("dsThongTinSuDung", thongtinSuDungService.getAll());

        //danh sách đang được mượn
        model.addAttribute("dsDangMuon",  thongtinSuDungService.findBorrowing());

        //danh sách đã trả
        model.addAttribute("dsDaTra", thongtinSuDungService.findReturned());

        //danh sách đặt chỗ
        model.addAttribute("dsDatCho", thongtinSuDungService.findDatCho());

        return "layout/layout";
    }

    @GetMapping("/rentingForm")
    public String rentingForm(Model model){


        return "thietbi/renting-form";
    }

    //cái này của form mượn của thành viên
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
            response.put("message", "Không thể đặt chỗ vào thời gian này");
        }


        return response;
    }

    //tìm thành viên khi mượn ở admin
    @PostMapping("/findTV")
    @ResponseBody
    public Map<String, String> findTV(@RequestParam("TvId") String tvId,
                                      HttpSession session, Model model){

        Map<String, String> response = new HashMap<>();

        try{
            long tvIdLong = Long.parseLong(tvId);
            if(thanhvienService.isBanned(Long.parseLong(tvId))){
                response.put("status", "banned");
                response.put("message", "Thành viên này bị cấm mượn thiết bị");
                response.put("info", tvId + " - "+ thanhvienService.findById(tvIdLong).getTen());
                return response;
            }
            else{
                response.put("status", "success");
                response.put("message", "Thành viên có thể mượn thiết bị");
                response.put("info", tvId + " - "+ thanhvienService.findById(tvIdLong).getTen());
                return response;
            }
        }
        catch (NoSuchElementException e) {
            response.put("status", "error");
            response.put("message", "Không tìm thấy thành viên");
            return response;
        }
        catch (NumberFormatException e) {
            response.put("status", "error");
            response.put("message", "Mã chỉ chứa số, không chứa kí tự khác và không được để trống");
            return response;
        }
    }

    //tìm thiết bị khi mượn ở admin
    @PostMapping("/findTB")
    @ResponseBody
    public Map<String, String> findTB(@RequestParam("TbId") String tbId,
                                      HttpSession session, Model model){

        Map<String, String> response = new HashMap<>();

        try{
            int tbIdInt = Integer.parseInt(tbId);
            if(!thongtinSuDungService.canBeBorrowed(tbIdInt)){
                response.put("status", "borrowed");
                response.put("message", "Thiết bị không thể cho mượn(đang được mượn hoặc đã được đặt chỗ trước đó)");
                response.put("info", tbId + " - "+ thietbiService.findById(tbIdInt).getTen());
                return response;
            }
            else{
                response.put("status", "success");
                response.put("message", "Thiết bị có thể mượn");
                response.put("info", tbId + " - "+ thietbiService.findById(tbIdInt).getTen());
                return response;
            }
        }
        catch (NoSuchElementException | NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Không tìm thấy thiết bị");
            return response;
        }
        catch (NumberFormatException e) {
            response.put("status", "error");
            response.put("message", "Mã chỉ chứa số, không chứa kí tự khác và không được để trống");
            return response;
        }

    }

    //thực hiện cho mượn ở admin
    @PostMapping("/borrow")
    @ResponseBody
    public Map<String, Map> borrow(@RequestParam("TbId") String tbId,
                                      @RequestParam("TvId") String tvId,
                                      HttpSession session, Model model){

        Map<String, String> rentResponse = new HashMap<>();

        Map<String, String> tbResponse = findTB(tbId, session, model);
        Map<String, String> tvResponse = findTV(tvId, session, model);

        if(!tbResponse.get("status").equals("success") || !tvResponse.get("status").equals("success")){
            rentResponse.put("status", "failed");
            rentResponse.put("message", "Cho mượn thất bại");

        }
        else if(tbResponse.get("status").equals("success") && tvResponse.get("status").equals("success")){
            rentResponse.put("status", "success");
            rentResponse.put("message", "Cho mượn thành công");

            //khi xác thực thông tin thành công, thì thực hiện cho mượn
            thongtin_sudung tsd = new thongtin_sudung();
            tsd.setNgaymuon(LocalDateTime.now());
            tsd.setThanhvien(thanhvienService.findById(Long.parseLong(tvId)));
            tsd.setThietbi(thietbiService.findById(Integer.parseInt(tbId)));
            thongtinSuDungService.save(tsd);
        }

        Map<String, Map> response = new HashMap<>();
        response.put("rentResponse", rentResponse);
        response.put("tbResponse", tbResponse);
        response.put("tvResponse", tvResponse);


        return response;
    }

    //trả thiết bị ở admin
    @GetMapping("/returnOne")
    public String returnOneForm(@RequestParam("ttsdId") String ttsdId){

        thongtin_sudung tsd = thongtinSuDungService.findById(Integer.parseInt(ttsdId));
        tsd.setNgaytra(LocalDateTime.now());
        thongtinSuDungService.save(tsd);

        return "redirect:/thietbi/all";
    }

    @GetMapping("/return")
    public String returnForm(Model model){

        return "thietbi/return-form";
    }

    // xử lý trả thiết bị ở admin
    @PostMapping("/return")
    @ResponseBody
    public Map<String, String> returnTB(@RequestParam("TbId") String tbId,
                                      HttpSession session, Model model){

        Map<String, String> response = new HashMap<>();

        try{
            int tbIdInt = Integer.parseInt(tbId);
            thongtin_sudung tsd = thongtinSuDungService.getBorrowingByThietbiId(tbIdInt);

            if(tsd == null){
                response.put("status", "failed");
                response.put("message", "Thiết bị không được mượn");
                response.put("info", tbId + " - "+ thietbiService.findById(tbIdInt).getTen());
                return response;
            }

            tsd.setNgaytra(LocalDateTime.now());
            thongtinSuDungService.save(tsd);

            response.put("status", "success");
            response.put("message", "Trả thiết bị thành công");
            response.put("info", tbId + " - "+ thietbiService.findById(tbIdInt).getTen());
            return response;
        }
        catch (NoSuchElementException | NullPointerException e) {
            response.put("status", "error");
            response.put("message", "Không tìm thấy thiết bị");
            return response;
        }
        catch (NumberFormatException e) {
            response.put("status", "error");
            response.put("message", "Mã chỉ chứa số, không chứa kí tự khác");
            return response;
        }

    }
}
