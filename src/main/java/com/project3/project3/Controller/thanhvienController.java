package com.project3.project3.Controller;

import com.project3.project3.DTO.ThanhVienDTO;
import com.project3.project3.Model.khoa;
import com.project3.project3.Model.nganh;
import com.project3.project3.Model.thanhvien;
import com.project3.project3.Model.thongtin_sudung;
import com.project3.project3.Service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@Controller
@RequestMapping("/thanhvien")
public class thanhvienController {

    thanhvienService thanhvienService;
    khoaService khoaService;
    nganhService nganhService;
    thanhvienSequenceService thanhvienSequenceService;
    thongtinSuDungService thongtinSuDungService;

    public thanhvienController(thanhvienService thanhvienService,
                               khoaService khoaService,
                               nganhService nganhService,
                               thanhvienSequenceService thanhvienSequenceService,
                               thongtinSuDungService thongtinSuDungService) {
        this.thanhvienService = thanhvienService;
        this.khoaService = khoaService;
        this.nganhService = nganhService;
        this.thanhvienSequenceService = thanhvienSequenceService;
        this.thongtinSuDungService = thongtinSuDungService;
    }

    @GetMapping("/all")
    public String getAll(Model model){

        List<thanhvien> dsThanhVien = thanhvienService.getAll();

        model.addAttribute("dsThanhVien", dsThanhVien);

//        return "testThanhVien";
        return "layout/layout";
    }

//    @GetMapping("/register")
//    public String register(Model model){
//
//
//        return "thanhvien/form-dangky";
//    }

    //
    @ModelAttribute("dsKhoa")
    public List<khoa> getKhoaList() {
        return khoaService.getAll();
    }

    @ModelAttribute("dsNganh")
    public List<nganh> getNganhList() {
        return nganhService.getAll();
    }

    //show form thêm
    @GetMapping("/add")
    public String addForm(Model model){

        model.addAttribute("tv", new ThanhVienDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        return "thanhvien/formThanhVien";
    }

    //show form edit
    @GetMapping("/edit")
    public String updateForm(@RequestParam("tvId") Long tvId, Model model){

        thanhvien tv = thanhvienService.findById(tvId);

        ThanhVienDTO tvDTO = thanhvienService.modelToDTO(tv);

        model.addAttribute("tv", tvDTO);

        return "thanhvien/formThanhVien";
    }

    //hàm xử lý thêm và update
    @PostMapping("/save")
    public String save(@ModelAttribute("tv") ThanhVienDTO tvDTO){

        thanhvienService.save(tvDTO);

        return "redirect:/thanhvien/all";
    }

    //Xử lý xóa, vì làm trên browser nên dùng get
    @GetMapping("/delete")
    public String delete(@RequestParam("tvId") Long tvId){

        thanhvienService.deleteById(tvId);

        return "redirect:/thanhvien/all";
    }

    //hiện trang home
    @GetMapping("/home")
    public String home(HttpSession session, Model model){

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            return "redirect:/thanhvien/login";
        }
        ThanhVienDTO tvDTO = (ThanhVienDTO) session.getAttribute("thanhvien");

        model.addAttribute("flag", "profile");

        return "thanhvien/home";
    }

    @GetMapping("/rent")
    public String rent(HttpSession session, Model model){

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            return "redirect:/thanhvien/login";
        }


//        ThanhVienDTO tvDTO = (ThanhVienDTO) session.getAttribute("thanhvien");
//
        model.addAttribute("flag", "muon");

        return "thanhvien/thanhvien-muon";
    }

    @GetMapping("/changePassword")
    public String changePassword(HttpSession session, Model model){

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            return "redirect:/thanhvien/login";
        }

        model.addAttribute("flag", "doimatkhau");

        return "thanhvien/thanhvien-doimatkhau";
    }

    @GetMapping("/rentHistory")
    public String rentHistory(HttpSession session, Model model){

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            return "redirect:/thanhvien/login";
        }

        ThanhVienDTO tvDTO = (ThanhVienDTO) session.getAttribute("thanhvien");

        List<thongtin_sudung> dsTTSDdangMuon = thongtinSuDungService.findBorrowingByThanhvienId(tvDTO.getId());

        List<thongtin_sudung> dsTTSDdaTra = thongtinSuDungService.findReturnedByThanhvien(tvDTO.getId());

        List<thongtin_sudung> dsTTSDdatCho = thongtinSuDungService.findDatChoByThanhvienId(tvDTO.getId());

        model.addAttribute("dsTTSDdangMuon", dsTTSDdangMuon);
        model.addAttribute("dsTTSDdaTra", dsTTSDdaTra);
        model.addAttribute("dsTTSDdatCho", dsTTSDdatCho);

        model.addAttribute("flag", "lichsumuon");

        return "thanhvien/home";
    }

    @GetMapping("/violations")
    public String violations(HttpSession session, Model model){

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            return "redirect:/thanhvien/login";
        }



        model.addAttribute("flag", "vipham");

        return "thanhvien/home";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model){

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng nhập
            return "redirect:/thanhvien/login";
        }

        model.addAttribute("flag", "profile");

        return "thanhvien/home";
    }

    @PostMapping("/update")
    public String updateProfile(
            @Valid @ModelAttribute("profile") ThanhVienDTO tvDTO,
            HttpSession session,
            BindingResult bindingResult,
            Model model) {

        if (session.getAttribute("thanhvien") == null) {

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng nhập
            return "redirect:/thanhvien/login";
        }
        //update thông tin thành viên
        if (bindingResult.hasErrors()) {
            return "thanhvien/home";
        }

        thanhvienService.save(tvDTO);

        model.addAttribute("message", "Cập nhật thông tin thành công");
        model.addAttribute("profile", tvDTO);
        session.setAttribute("thanhvien", tvDTO);
        return "redirect:/thanhvien/profile";
    }


    //hiện form đăng ký
    @GetMapping("/register")
    public String registerForm(Model model, HttpSession session){

        if(session.getAttribute("thanhvien") != null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu có thì chuyển hướng về trang home
            return "redirect:/thanhvien/home";
        }

        model.addAttribute("tv", new ThanhVienDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        return "thanhvien/form-dangky";
    }

    //đăng ký
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("tv") ThanhVienDTO tvDTO,
            BindingResult bindingResult,
            HttpSession session){

        if (bindingResult.hasErrors()) {
            return "thanhvien/form-dangky";
        }

        if(thanhvienService.save(tvDTO) != null){
            //lưu thông tin user vào session
            session.setAttribute("thanhvien", tvDTO);

        }

        return "redirect:/thanhvien/home";
    }

    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session){

        if(session.getAttribute("thanhvien") != null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu có thì chuyển hướng về trang home
            return "redirect:/thanhvien/home";
        }

        model.addAttribute("tv", new ThanhVienDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        return "thanhvien/form-dangnhap";
    }


    @PostMapping("/login")
    public String login(
            @ModelAttribute("tv") ThanhVienDTO tvDTO,
            HttpSession session,
            Model model){

        thanhvien tv = thanhvienService.checkLogin(tvDTO.getId(), tvDTO.getPassword());

        if(tv != null){

            //khi đăng nhập thành công, lưu thông tin thành viên vào session
            ThanhVienDTO newTvDTO = thanhvienService.modelToDTO(tv);

            session.setAttribute("thanhvien", newTvDTO);

            return "redirect:/thanhvien/home";
        }

        model.addAttribute("message", "Sai id hoặc mật khẩu");

        return "thanhvien/form-dangnhap";
    }

    //chức năng đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.removeAttribute("thanhvien");
        return "redirect:/thanhvien/login";

    }


    //hiện form kiểm tra vào khu học tập
    @GetMapping("/check")
    public String checkForm(Model model){

        String a = "0";

        model.addAttribute("tvId", Long.valueOf(a));

        model.addAttribute("check", 0);

        return "thanhvien/checkIn";
    }

    @PostMapping("/check")
    public String check(@RequestParam("tvId") Long tvId, Model model){

        thanhvien tv = new thanhvien();

        try{
            tv = thanhvienService.findById(tvId);

            model.addAttribute("check", 2);
            model.addAttribute("tv", tv);
            model.addAttribute("tvId", tvId);
            model.addAttribute("message", "this is a message");
        }
        catch (Exception e){
            model.addAttribute("check", 1);
            model.addAttribute("tvId", tvId);
        }

        return "thanhvien/checkIn";
    }

    @GetMapping("/forgotPassword")
    public String forgotPasswordForm(Model model){

        model.addAttribute("email", "");

        return "thanhvien/forgot-password";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam("email") String email, Model model){

        return "thanhvien/forgot-password";
    }
}
