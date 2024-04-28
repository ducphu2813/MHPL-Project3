package com.project3.project3.Service;

import com.project3.project3.Model.thongtin_sudung;

import java.time.LocalDateTime;
import java.util.List;

public interface thongtinSuDungService {

    List<thongtin_sudung> getAll();

    thongtin_sudung findById(Integer id);

    void save(thongtin_sudung thongtin_sudung);

    boolean checkDatCho(Integer tbId, LocalDateTime tg_datcho);

    List<thongtin_sudung> findBorrowingByThanhvienId(Long tvId);

    List<thongtin_sudung> findReturnedByThanhvien(Long tvId);

    List<thongtin_sudung> findDatChoByThanhvienId(Long tvId);
}
