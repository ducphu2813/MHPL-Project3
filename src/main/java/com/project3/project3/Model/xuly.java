package com.project3.project3.Model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "xuly")
@NoArgsConstructor
public class xuly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_thanhvien")
    private thanhvien thanhvien;

    @Column(name = "lydo")
    private String lydo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hinhthucxl")
    private hinhthuc_xuly hinhthuc_xuly;

    @Column(name = "tienphat")
    private Double tienphat;

    @Column(name = "ngay_xuly")
    private LocalDateTime ngay_xuly;

    @Column(name = "trangthai")
    private boolean trangthai = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public thanhvien getThanhvien() {
        return thanhvien;
    }

    public void setThanhvien(thanhvien thanhvien) {
        this.thanhvien = thanhvien;
    }


    public String getLydo() {
        return lydo;
    }

    public void setLydo(String lydo) {
        this.lydo = lydo;
    }

    public hinhthuc_xuly getHinhthuc_xuly() {
        return hinhthuc_xuly;
    }

    public void setHinhthuc_xuly(hinhthuc_xuly hinhthuc_xuly) {
        this.hinhthuc_xuly = hinhthuc_xuly;
    }

    public Double getTienphat() {
        return tienphat;
    }

    public void setTienphat(Double tienphat) {
        this.tienphat = tienphat;
    }

    public LocalDateTime getNgay_xuly() {
        return ngay_xuly;
    }

    public void setNgay_xuly(LocalDateTime ngay_xuly) {
        this.ngay_xuly = ngay_xuly;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }
}
