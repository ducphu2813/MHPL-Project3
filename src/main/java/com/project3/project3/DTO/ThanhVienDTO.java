package com.project3.project3.DTO;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ThanhVienDTO {
    private Long id;

    @NotBlank(message = "Tên không được để trống")
    private String ten;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(min = 10, max = 11, message = "Số điện thoại phải từ 10 đến 11 số")
    private String sodienthoai;
    private Integer khoaId;
    private Integer nganhId;
    private String tenKhoa;
    private String tenNganh;

    @Email(message = "Email không đúng định dạng")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Mật khẩu nhập lại không được để trống")
    private String repassword;

    private LocalDateTime created_date;

    public ThanhVienDTO() {
    }

    public ThanhVienDTO(Long id, String ten, String sodienthoai, Integer khoaId, Integer nganhId, String tenKhoa, String tenNganh, String email, String password, LocalDateTime created_date) {
        this.id = id;
        this.ten = ten;
        this.sodienthoai = sodienthoai;
        this.khoaId = khoaId;
        this.nganhId = nganhId;
        this.tenKhoa = tenKhoa;
        this.tenNganh = tenNganh;
        this.email = email;
        this.password = password;
        this.created_date = created_date;
    }



    // getters and setters

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public Integer getKhoaId() {
        return khoaId;
    }

    public void setKhoaId(Integer khoaId) {
        this.khoaId = khoaId;
    }

    public Integer getNganhId() {
        return nganhId;
    }

    public void setNganhId(Integer nganhId) {
        this.nganhId = nganhId;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    @AssertTrue(message = "Mật khẩu nhập lại không khớp")
    public boolean isPasswordMatching() {
        if (password == null || repassword == null) {
            return false;
        } else {
            return password.equals(repassword);
        }
    }
}
