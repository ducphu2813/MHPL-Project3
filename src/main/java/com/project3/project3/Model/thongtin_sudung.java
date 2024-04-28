package com.project3.project3.Model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "thongtin_sudung")
@NoArgsConstructor
public class thongtin_sudung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_thanhvien")
    private thanhvien thanhvien;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_thietbi")
    private thietbi thietbi;

    @Column(name = "ngaymuon")
    private LocalDateTime ngaymuon = null;

    @Column(name = "ngaytra")
    private LocalDateTime ngaytra = null;

    @Column(name = "Tg_datcho")
    private LocalDateTime tg_datcho = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public com.project3.project3.Model.thanhvien getThanhvien() {
        return thanhvien;
    }

    public void setThanhvien(com.project3.project3.Model.thanhvien thanhvien) {
        this.thanhvien = thanhvien;
    }

    public com.project3.project3.Model.thietbi getThietbi() {
        return thietbi;
    }

    public void setThietbi(com.project3.project3.Model.thietbi thietbi) {
        this.thietbi = thietbi;
    }

    public LocalDateTime getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(LocalDateTime ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public LocalDateTime getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(LocalDateTime ngaytra) {
        this.ngaytra = ngaytra;
    }

    public LocalDateTime getTg_datcho() {
        return tg_datcho;
    }

    public void setTg_datcho(LocalDateTime tg_datcho) {
        this.tg_datcho = tg_datcho;
    }


}
