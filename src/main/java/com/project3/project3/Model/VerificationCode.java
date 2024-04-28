package com.project3.project3.Model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "VerificationCode")
@Entity
@NoArgsConstructor
public class VerificationCode {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code")
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_thanhvien")
    private thanhvien thanhvien;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "expiry_date")
    private LocalDateTime expiredDate;

    @Column(name = "status")
    private boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public com.project3.project3.Model.thanhvien getThanhvien() {
        return thanhvien;
    }

    public void setThanhvien(com.project3.project3.Model.thanhvien thanhvien) {
        this.thanhvien = thanhvien;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDateTime expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
