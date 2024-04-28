package com.project3.project3.Model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "thongtin_vao")
@NoArgsConstructor
public class thongtin_vao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_thanhvien")
    private thanhvien thanhvien;

    @Column(name = "thoigian_vao")
    private LocalDateTime thoigian_vao = LocalDateTime.now();

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

    public LocalDateTime getThoigian_vao() {
        return thoigian_vao;
    }

    public void setThoigian_vao(LocalDateTime thoigian_vao) {
        this.thoigian_vao = thoigian_vao;
    }
}
