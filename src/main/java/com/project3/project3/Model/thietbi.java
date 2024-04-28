package com.project3.project3.Model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "thietbi")
@NoArgsConstructor
public class thietbi {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "mota")
    private String mota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_loai")
    private loai_thietbi loai_thietbi;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "thietbi",
            cascade = CascadeType.REMOVE)
    private Set<thongtin_sudung> tt_sd;

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

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public com.project3.project3.Model.loai_thietbi getLoai_thietbi() {
        return loai_thietbi;
    }

    public void setLoai_thietbi(com.project3.project3.Model.loai_thietbi loai_thietbi) {
        this.loai_thietbi = loai_thietbi;
    }
}
