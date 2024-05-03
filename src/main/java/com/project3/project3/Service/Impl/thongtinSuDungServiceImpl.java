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
    public boolean isBorrowed(Integer tbId) {
        List<thongtin_sudung> tsdList = thongtinSuDungRepository.findBorrowingByThietbiId(tbId);
        return !tsdList.isEmpty();
    }

    //hàm kiểm tra xem thiết bị đó có thể cho mượn không
    @Override
    public boolean canBeBorrowed(Integer tbId) {

        List<thongtin_sudung> dangMuon = thongtinSuDungRepository.findBorrowingByThietbiId(tbId);
        List<thongtin_sudung> datCho = thongtinSuDungRepository.findByThietbiIdAndTg_datcho(tbId, LocalDateTime.now());

        for(thongtin_sudung tsd : dangMuon){
            if(tsd.getThietbi().getId().equals(tbId)){
                return false;
            }
        }

        for(thongtin_sudung tsd : datCho){
            if(tsd.getThietbi().getId().equals(tbId)){
                return false;
            }
        }
        return true;
    }

    //lấy thông tin sử dụng đang được mượn theo id thiết bị
    @Override
    public thongtin_sudung getBorrowingByThietbiId(Integer tbId) {

        List<thongtin_sudung> tsdList = thongtinSuDungRepository.findBorrowingByThietbiId(tbId);

        if(tsdList.isEmpty()){
            return null;
        }

        return tsdList.get(0);
    }

    //lấy tất cả thông tin sử dụng đang được mượn
    @Override
    public List<thongtin_sudung> findBorrowing() {
        return thongtinSuDungRepository.findBorrowing();
    }

    //lấy tất cả thông tin sử dụng đã được trả
    @Override
    public List<thongtin_sudung> findReturned() {
        return thongtinSuDungRepository.findReturned();
    }

    //lấy tất cả đặt chỗ
    @Override
    public List<thongtin_sudung> findDatCho() {
        return thongtinSuDungRepository.findDatCho();
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
