package com.project3.project3.Service;

import com.project3.project3.Model.thongtin_sudung;

import java.time.LocalDateTime;
import java.util.List;

public interface thongtinSuDungService {

    List<thongtin_sudung> getAll();

    thongtin_sudung findById(Integer id);

    void save(thongtin_sudung thongtin_sudung);

    boolean checkDatCho(Integer tbId, LocalDateTime tg_datcho);

    boolean isBorrowed(Integer tbId);

    boolean canBeBorrowed(Integer tbId);

    thongtin_sudung getBorrowingByThietbiId(Integer tbId);

    List<thongtin_sudung> findBorrowing();

    List<thongtin_sudung> findReturned();

    List<thongtin_sudung> findDatCho();

    List<thongtin_sudung> findBorrowingByThanhvienId(Long tvId);

    List<thongtin_sudung> findReturnedByThanhvien(Long tvId);

    List<thongtin_sudung> findDatChoByThanhvienId(Long tvId);

    void nullifyThanhvienInThongtinSudung(Long id);

    void deleteByThanhvienId(Long tvId);

    void deleteByThietbiId(Integer tbId);
}
