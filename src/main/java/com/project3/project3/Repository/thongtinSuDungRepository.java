package com.project3.project3.Repository;

import com.project3.project3.Model.thongtin_sudung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface thongtinSuDungRepository extends JpaRepository<thongtin_sudung, Integer> {

    //hàm kiểm tra xem thiết bị có được đặt chỗ vào thời gian đó không
    @Query("SELECT tsd FROM thongtin_sudung tsd " +
            "WHERE tsd.thietbi.id = :tbId AND DATE(tsd.tg_datcho) = DATE(:tg_datcho)")
    List<thongtin_sudung> findByThietbiIdAndTg_datcho(@Param("tbId") Integer tbId, @Param("tg_datcho") LocalDateTime tg_datcho);


    //tìm những thông tin sử dụng đang được mượn theo id thành viên
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.thanhvien.id = :tvId AND tsd.ngaytra IS NULL AND tsd.tg_datcho IS NULL")
    List<thongtin_sudung> findBorrowingByThanhvienId(@Param("tvId") Long tvId);

    //tìm những thông tin sử dụng đã được trả theo id thành viên
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.thanhvien.id = :tvId AND tsd.ngaytra IS NOT NULL AND tsd.tg_datcho IS NULL")
    List<thongtin_sudung> findReturnedByThanhvien(@Param("tvId") Long tvId);

    //tìm những lần đặt chỗ theo id thành viên
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.thanhvien.id = :tvId AND tsd.tg_datcho IS NOT NULL AND tsd.ngaymuon IS NULL AND tsd.ngaytra IS NULL")
    List<thongtin_sudung> findDatChoByThanhvienId(@Param("tvId") Long tvId);
}