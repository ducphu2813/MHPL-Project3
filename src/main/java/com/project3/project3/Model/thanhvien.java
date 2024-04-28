package com.project3.project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "thanhvien")
@NoArgsConstructor
public class thanhvien {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "ten")
    @NotBlank(message = "Tên không được để trống")
    private String ten;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_khoa")
    private khoa khoa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nganh")
    private nganh nganh;

    @Column(name = "sodienthoai")
    @NotBlank(message = "Số điện thoại không được để trống")
    private String sodienthoai;

    @Column(name = "email")
    @Email(message = "Email không đúng định dạng")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "thanhvien",
            cascade = CascadeType.REMOVE)
    private Set<thongtin_sudung> tt_sd;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "thanhvien",
            cascade = CascadeType.REMOVE)
    private Set<xuly> xulys;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "thanhvien",
            cascade = CascadeType.REMOVE)
    private Set<thongtin_vao> thongtinvaos;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "thanhvien",
            cascade = CascadeType.REMOVE)
    private Set<VerificationCode> verificationCodes;

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

    public com.project3.project3.Model.khoa getKhoa() {
        return khoa;
    }

    public void setKhoa(com.project3.project3.Model.khoa khoa) {
        this.khoa = khoa;
    }

    public com.project3.project3.Model.nganh getNganh() {
        return nganh;
    }

    public void setNganh(com.project3.project3.Model.nganh nganh) {
        this.nganh = nganh;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
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
}
