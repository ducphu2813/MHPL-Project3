package com.project3.project3.Model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "loai_thietbi")
@NoArgsConstructor
public class loai_thietbi {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @OneToMany(mappedBy = "loai_thietbi",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Set<thietbi> thietbis;

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
