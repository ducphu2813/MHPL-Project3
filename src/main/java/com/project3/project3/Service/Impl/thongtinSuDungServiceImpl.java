package com.project3.project3.Service.Impl;

import com.project3.project3.Model.thongtin_sudung;
import com.project3.project3.Repository.thongtinSuDungRepository;
import com.project3.project3.Service.thongtinSuDungService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class thongtinSuDungServiceImpl implements thongtinSuDungService {

    thongtinSuDungRepository thongtinSuDungRepository;

    public thongtinSuDungServiceImpl(thongtinSuDungRepository thongtinSuDungRepository) {
        this.thongtinSuDungRepository = thongtinSuDungRepository;
    }

    @Override
    public List<thongtin_sudung> getAll() {
        return thongtinSuDungRepository.findAll();
    }

    @Override
    public thongtin_sudung findById(Integer id) {
        return thongtinSuDungRepository.findById(id).orElse(null);
    }

    @Override
    public void save(thongtin_sudung thongtin_sudung) {
        thongtinSuDungRepository.save(thongtin_sudung);
    }

    @Override
    public boolean checkDatCho(Integer tbId, LocalDateTime tg_datcho) {
        List<thongtin_sudung> tsdList = thongtinSuDungRepository.findByThietbiIdAndTg_datcho(tbId, tg_datcho);
        return tsdList.isEmpty();
    }

    @Override
    public List<thongtin_sudung> findBorrowingByThanhvienId(Long tvId) {
        return thongtinSuDungRepository.findBorrowingByThanhvienId(tvId);
    }

    @Override
    public List<thongtin_sudung> findReturnedByThanhvien(Long tvId) {
        return thongtinSuDungRepository.findReturnedByThanhvien(tvId);
    }

    @Override
    public List<thongtin_sudung> findDatChoByThanhvienId(Long tvId) {
        return thongtinSuDungRepository.findDatChoByThanhvienId(tvId);
    }
}
