package com.project3.project3.Controller;

import com.project3.project3.DTO.ThanhVienDTO;
import com.project3.project3.Model.*;
import com.project3.project3.Service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/thanhvien")
public class thanhvienController {

    private final thanhvienService thanhvienService;
    private final khoaService khoaService;
    private final nganhService nganhService;
    private final thanhvienSequenceService thanhvienSequenceService;
    private final thongtinSuDungService thongtinSuDungService;
    private final xulyService xulyService;
    private final EmailService emailService;
    private final VerificationCodeService verificationCodeService;
    private final thongtinVaoSerivce thongtinVaoSerivce;
    private final ExcelService excelService;

    public thanhvienController(thanhvienService thanhvienService,
                               khoaService khoaService,
                               nganhService nganhService,
                               thanhvienSequenceService thanhvienSequenceService,
                               thongtinSuDungService thongtinSuDungService,
                               EmailService emailService,
                               VerificationCodeService verificationCodeService,
                               xulyService xulyService,
                               thongtinVaoSerivce thongtinVaoSerivce,
                               ExcelService excelService) {
        this.thanhvienService = thanhvienService;
        this.khoaService = khoaService;
        this.nganhService = nganhService;
        this.thanhvienSequenceService = thanhvienSequenceService;
        this.thongtinSuDungService = thongtinSuDungService;
        this.emailService = emailService;
        this.verificationCodeService = verificationCodeService;
        this.xulyService = xulyService;
        this.thongtinVaoSerivce = thongtinVaoSerivce;
        this.excelService = excelService;
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

        LocalDateTime now = LocalDateTime.now();

        model.addAttribute("tv", new ThanhVienDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                now));

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

    @GetMapping("/deleteByCondition")
    public String deleteByCondition(){

        return "thanhvien/delete-byCondition";
    }

    @PostMapping("/deleteByCondition")
    @ResponseBody
    public Map<String, String> deleteByCondition(@RequestParam("year") String yearInput){

        Map<String, String> response = new HashMap<>();

        int year = Integer.parseInt(yearInput);
        List<thanhvien> dsTvByYear = thanhvienService.findByYear(year);

        if(dsTvByYear.isEmpty()){
            response.put("message", "Không có thành viên nào được tạo vào năm "+year);
            response.put("status", "failed");
            return response;
        }
        else{
            //xóa luôn những thứ liên quan đến thành viên
            for(thanhvien tv : dsTvByYear){
                thongtinVaoSerivce.deleteThongtinVaoByThanhvienId(tv.getId());
                thongtinSuDungService.deleteByThanhvienId(tv.getId());
                xulyService.deleteByThanhvien(tv.getId());
            }

            thanhvienService.deleteByYear(year);
            response.put("message", "Xóa thành công tất cả thành viên được tạo vào năm "+year);
            response.put("status", "success");
            return response;
        }
    }

    //hiện trang home, thông tin cá nhân và trạng thái vi phạm
    @GetMapping("/home")
    public String home(HttpSession session, Model model){

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            return "redirect:/thanhvien/login";
        }

        ThanhVienDTO tvDTO = (ThanhVienDTO) session.getAttribute("thanhvien");
        List<xuly> dsChuaXuly = xulyService.getXulyFalseByThanhvien(tvDTO.getId());

        model.addAttribute("flag", "profile");
        model.addAttribute("dsXuly", dsChuaXuly);

        return "thanhvien/home";
    }

    @GetMapping("/rent")
    public String rent(HttpSession session, Model model){

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            return "redirect:/thanhvien/login";
        }

        if(thanhvienService.isBanned(((ThanhVienDTO) session.getAttribute("thanhvien")).getId())){

            List<xuly> dsXuly = xulyService.getXulyFalseByThanhvien(((ThanhVienDTO) session.getAttribute("thanhvien")).getId());
            model.addAttribute("dsXuly", dsXuly);
            return "thanhvien/violations-list";
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

        return "thanhvien/ChangePass";
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public Map<String, String> changePassword(@RequestParam("password") String newPass,
                                              @RequestParam("repassword") String rePass,
                                              HttpSession session,
                                              Model model){

        Map<String, String> response = new HashMap<>();

        if(session.getAttribute("thanhvien") == null){

            //kiểm tra trong session có thông tin thành viên không,
            // nếu không có thì chuyển về trang đăng ký
            response.put("message", "Hết phiên làm việc, vui lòng đăng nhập lại");
            response.put("status", "notlogin");
            return response;
        }

        ThanhVienDTO tvDTO = (ThanhVienDTO) session.getAttribute("thanhvien");

        if(!newPass.equals(rePass)){
            response.put("message", "Mật khẩu nhập lại không khớp");
            response.put("status", "failed");
            return response;
        }

        tvDTO.setPassword(newPass);

        thanhvienService.save(tvDTO);

        response.put("message", "Đổi mật khẩu thành công");
        response.put("status", "success");
        return response;
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

        ThanhVienDTO tvDTO = (ThanhVienDTO) session.getAttribute("thanhvien");

        List<xuly> dsXuly = xulyService.getByThanhvienId(tvDTO.getId());

        model.addAttribute("dsXuly", dsXuly);

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
                null,
                LocalDateTime.now()));

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

        thanhvien tv = thanhvienService.save(tvDTO);
        ThanhVienDTO newTvDTO = thanhvienService.modelToDTO(tv);

        //lưu thông tin user vào session
        session.setAttribute("thanhvien", newTvDTO);

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
    public String check(@RequestParam("tvId") String tvId, Model model){

        thanhvien tv = new thanhvien();

        try{
            Long tvIdLong = Long.valueOf(tvId);
            tv = thanhvienService.findById(tvIdLong);

            model.addAttribute("tv", tv);
            model.addAttribute("tvId", tvId);
            List<xuly> dsChuaXuly = xulyService.getXulyFalseByThanhvien(tvIdLong);

            //check thành viên có vi phạm nào chưa xử lý không
            if(!dsChuaXuly.isEmpty()){
                model.addAttribute("dsXuly", dsChuaXuly);
                model.addAttribute("check", 4);
            }
            else{
                //nếu không thì thêm thông tin vào
                model.addAttribute("check", 2);
                thongtin_vao thongtinVao = new thongtin_vao();
                thongtinVao.setThanhvien(tv);
                thongtinVaoSerivce.save(thongtinVao);
            }
        }
        catch (NumberFormatException e){
            model.addAttribute("check", 3);
            model.addAttribute("tvId", tvId);
        }
        catch (NoSuchElementException e){
            model.addAttribute("check", 1);
            model.addAttribute("tvId", tvId);
        }

        return "thanhvien/checkIn";
    }

    //trang nhập email để lấy mã xác thực
    @GetMapping("/forgotPassword")
    public String forgotPasswordForm(Model model){

        model.addAttribute("email", "");

        return "thanhvien/forgot-password";
    }

    //đây là sau khi nhập mail xong, lưu id của thành viên đang đổi mật khẩu vào session
    @PostMapping("/forgotPassword")
    @ResponseBody
    public Map<String, String> forgotPassword(@RequestParam("email") String email,
                                              Model model,
                                              HttpSession session){

        Map<String, String> response = new HashMap<>();

        thanhvien tv = thanhvienService.findByEmail(email);

        if(tv == null){
            response.put("status", "failed");
            response.put("message", "Email không tồn tại");
            return response;
        }
        else{
            response.put("status", "success");
            response.put("message", "Email tồn tại");
            email = email.trim();

            //xử lý tạo và lưu mã xác thực vào db
            String code = generateVerificationCode();
            VerificationCode verificationCode = new VerificationCode();
            verificationCode.setCode(code);
            verificationCode.setThanhvien(tv);
            LocalDateTime now = LocalDateTime.now();
            verificationCode.setCreatedDate(now);
            verificationCode.setExpiredDate(now.plusMinutes(30));
            verificationCode.setStatus(false);

            verificationCodeService.save(verificationCode);

            //lưu id của thành viên đang đổi mật khẩu vào session
            session.setAttribute("newPassTv", verificationCode.getThanhvien().getId());

            //gửi email chứa mã xác thực
            emailService.sendSimpleMessage(email, "Your verification code", "Your verification code is: "+code);

            return response;
        }

    }

    //kiểm tra mail xong thì chuyển hướng đến trang nhập mã xác thực
    @GetMapping("/verify")
    public String verifyForm(Model model, HttpSession session){

        if(session.getAttribute("newPassTv") == null){
            return "redirect:/thanhvien/forgotPassword";
        }

        return "thanhvien/VerificateCode";
    }

    //đây là sau khi nhập mã xác thực xong, kiểm tra mã xác thực và chuyển hướng đến trang đổi mật khẩu
    @PostMapping("/verify")
    @ResponseBody
    public Map<String, String> verify(@RequestParam("code") String code,
                                      Model model,
                                      HttpSession session){

        Map<String, String> response = new HashMap<>();

        //kiếm mã dựa trên cả id của thành viên đang đổi mật khẩu và mã xác thực
        VerificationCode verificationCode = verificationCodeService.findByCodeAndThanhvienId(code, (Long) session.getAttribute("newPassTv"));

        if(verificationCode == null){
            response.put("status", "failed");
            response.put("message", "Mã xác thực không chính xác");
            return response;
        }

        if(verificationCode.isStatus()){
            response.put("status", "failed");
            response.put("message", "Mã xác thực đã được sử dụng");
            return response;
        }

        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(verificationCode.getExpiredDate())){
            response.put("status", "failed");
            response.put("message", "Mã xác thực đã hết hạn");
            return response;
        }

        verificationCode.setStatus(true);
        verificationCodeService.save(verificationCode);

        session.setAttribute("isVerified", true);

        response.put("status", "success");
        response.put("message", "Xác thực thành công");
        return response;
    }

    //hiện trang đổi mật khẩu
    @GetMapping("/changeForgotPassword")
    public String changePasswordForm(Model model, HttpSession session){

        if(session.getAttribute("isVerified") == null || session.getAttribute("newPassTv") == null){
            return "redirect:/thanhvien/forgotPassword";
        }

        return "thanhvien/thanhvien-doimatkhau";
    }

    //đổi mật khẩu, kiểm tra trong session có id thành viên đang đổi mật khẩu không
    @PostMapping("/changeForgotPassword")
    @ResponseBody
    public Map<String, String> changeForgotPassword(@RequestParam("password") String newPass,
                                 @RequestParam("repassword") String confirmPass,
                                 HttpSession session,
                                 Model model){

        Map<String, String> response = new HashMap<>();

        if(session.getAttribute("isVerified") == null || session.getAttribute("newPassTv") == null){
            response.put("status", "failed");
            response.put("message", "Không thể đổi mật khẩu, phiên làm việc đã hết hạn");
        }

        if(!newPass.equals(confirmPass)){
            response.put("status", "failed");
            response.put("message", "Mật khẩu nhập lại không khớp");
            return response;
        }

        Long tvId = (Long) session.getAttribute("newPassTv");

        thanhvien tv = thanhvienService.findById(tvId);

        tv.setPassword(newPass);

        thanhvienService.save(thanhvienService.modelToDTO(tv));

        //sau khi đổi mật khẩu xong, xóa id thành viên và trạng thái xác thực trong session
        session.removeAttribute("isVerified");
        session.removeAttribute("newPassTv");

        response.put("status", "success");
        response.put("message", "Đổi mật khẩu thành công");
        return response;
    }

    @GetMapping("/excel")
    public String excel(){

        return "thanhvien/excel";
    }

    @PostMapping("/excel")
    @ResponseBody
    public Map<String, String> uploadExcel(@RequestParam("file") MultipartFile file) {

        Map<String, String> response = new HashMap<>();

        try {
            InputStream in = file.getInputStream();
            List<ThanhVienDTO> thanhVienDTOs = excelService.parseExcelFile(in);
            for(ThanhVienDTO tv : thanhVienDTOs){
                System.out.println(tv.getTen());
                System.out.println(tv.getTenKhoa());
                System.out.println(tv.getTenNganh());
                System.out.println(tv.getSodienthoai());
                System.out.println(tv.getEmail());
                System.out.println(tv.getPassword());
                System.out.println(tv.getCreated_date());
                System.out.println("====================================");
            }


            response.put("message", "Thêm thành công");
            response.put("status", "success");
            String addStatus = thanhvienService.saveList(thanhVienDTOs);

            if(addStatus.equals("error")){
                response.put("message", "Thêm thất bại, kiểm tra lại file excel của bạn");
                response.put("status", "failed");
            }
            else{
                response.put("message", "Thêm thành công");
                response.put("status", "success");
            }
        } catch (Exception e) {
            response.put("message", "Lỗi khi đọc file excel, hãy kiểm tra lại file excel của bạn");
            response.put("status", "failed");
            e.printStackTrace();
        }
        return response;
    }

    public String generateVerificationCode(){
        Random random = new Random();
        String generatedCode;

        // Lấy tất cả các mã xác thực trong db
        Set<String> existingCodes = verificationCodeService.getAll().stream()
                .map(VerificationCode::getCode)
                .collect(Collectors.toSet());

        do {
            int randomNum = 1000 + random.nextInt(9000); // Tạo 1 mã random từ 1000 đến 9999
            generatedCode = String.valueOf(randomNum);
        } while (existingCodes.contains(generatedCode));

        return generatedCode;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Model model) {
        model.addAttribute("message", "ID không hợp lệ");
        return "error";
    }
}
