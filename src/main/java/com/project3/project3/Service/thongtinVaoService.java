package com.project3.project3.Service;

import com.project3.project3.Model.thongtin_vao;

import java.util.HashMap;
import java.util.List;

public interface thongtinVaoService {

    List<thongtin_vao> getAll();

    HashMap<Integer, Integer> getData(int year);

    void save(thongtin_vao thongtin_vao);

    void nullifyThanhvienInThongtinVao(Long id);

    void deleteThongtinVaoByThanhvienId(Long id);
}
