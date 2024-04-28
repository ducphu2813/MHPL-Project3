package com.project3.project3.Model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "nganh")
@NoArgsConstructor
public class nganh {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @OneToMany(mappedBy = "nganh",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Set<thanhvien> thanhviens;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
