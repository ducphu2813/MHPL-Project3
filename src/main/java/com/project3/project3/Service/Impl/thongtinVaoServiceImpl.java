package com.project3.project3.Service.Impl;

import com.project3.project3.Model.thongtin_vao;
import com.project3.project3.Service.thongtinVaoSerivce;
import com.project3.project3.Repository.thongtinVaoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class thongtinVaoServiceImpl implements thongtinVaoSerivce {

    thongtinVaoRepository thongtinVaoRepository;

    public thongtinVaoServiceImpl(thongtinVaoRepository thongtinVaoRepository) {
        this.thongtinVaoRepository = thongtinVaoRepository;
    }

    @Override
    public List<thongtin_vao> getAll() {
        return null;
    }

    @Override
    public HashMap<Integer, Integer> getData(int year) {
        return null;
    }

    @Override
    public void save(thongtin_vao thongtin_vao) {

    }
}
