package com.project3.project3.Service.Impl;

import com.project3.project3.Model.thongtin_vao;
import com.project3.project3.Service.thongtinVaoSerivce;
import com.project3.project3.Repository.thongtinVaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return thongtinVaoRepository.findAll();
    }

    @Override
    public HashMap<Integer, Integer> getData(int year) {
        return null;
    }

    @Override
    @Transactional
    public void save(thongtin_vao thongtin_vao) {
        thongtinVaoRepository.save(thongtin_vao);
    }

    @Override
    @Transactional
    public void nullifyThanhvienInThongtinVao(Long id) {
        thongtinVaoRepository.nullifyThanhvienInThongtinVao(id);
    }

    @Override
    @Transactional
    public void deleteThongtinVaoByThanhvienId(Long id) {
        thongtinVaoRepository.deleteThongtinVaoByThanhvienId(id);
    }
}
