package com.project3.project3.Model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "hinhthuc_xuly")
@NoArgsConstructor
public class hinhthuc_xuly {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "hinhthuc")
    private String hinhthuc;

    @Column(name = "songay_ban")
    private Integer songay_ban;

    @OneToMany(mappedBy = "hinhthuc_xuly",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Set<xuly> xulys;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHinhthuc() {
        return hinhthuc;
    }

    public void setHinhthuc(String hinhthuc) {
        this.hinhthuc = hinhthuc;
    }

    public Integer getSongay_ban() {
        return songay_ban;
    }

    public void setSongay_ban(Integer songay_ban) {
        this.songay_ban = songay_ban;
    }
}
