package com.project3.project3.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "thanhvien_sequence")
@NoArgsConstructor
public class thanhvien_sequence {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "num")
    private Long num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }
}
