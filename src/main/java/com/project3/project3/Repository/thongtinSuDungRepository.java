package com.project3.project3.Repository;

import com.project3.project3.Model.thongtin_sudung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface thongtinSuDungRepository extends JpaRepository<thongtin_sudung, Integer> {

    //hàm kiểm tra xem thiết bị có được đặt chỗ vào thời gian đó không
    @Query("SELECT tsd FROM thongtin_sudung tsd " +
            "WHERE tsd.thietbi.id = :tbId AND DATE(tsd.tg_datcho) = DATE(:tg_datcho)")
    List<thongtin_sudung> findByThietbiIdAndTg_datcho(@Param("tbId") Integer tbId, @Param("tg_datcho") LocalDateTime tg_datcho);


    //tìm tất cả thông tin sử dụng có ngày mượn không null, ngày trả null(nghĩa là đang được mượn)
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.ngaymuon IS NOT NULL AND tsd.ngaytra IS NULL")
    List<thongtin_sudung> findBorrowing();

    //tìm tất cả thông tin sử dụng có ngày mượn không null, ngày trả không null
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.ngaymuon IS NOT NULL AND tsd.ngaytra IS NOT NULL")
    List<thongtin_sudung> findReturned();

    //tìm tất cả thông tin sử dụng có thời gian đặt chỗ không null, ngày mượn null, ngày trả null
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.tg_datcho IS NOT NULL AND tsd.ngaymuon IS NULL AND tsd.ngaytra IS NULL")
    List<thongtin_sudung> findDatCho();

    //tìm những thông tin sử dụng đang được mượn theo id thành viên
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.thanhvien.id = :tvId AND tsd.ngaytra IS NULL AND tsd.tg_datcho IS NULL")
    List<thongtin_sudung> findBorrowingByThanhvienId(@Param("tvId") Long tvId);

    //tìm những thông tin sử dụng đã được trả theo id thành viên
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.thanhvien.id = :tvId AND tsd.ngaytra IS NOT NULL AND tsd.tg_datcho IS NULL")
    List<thongtin_sudung> findReturnedByThanhvien(@Param("tvId") Long tvId);

    //tìm những lần đặt chỗ theo id thành viên
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.thanhvien.id = :tvId AND tsd.tg_datcho IS NOT NULL AND tsd.ngaymuon IS NULL AND tsd.ngaytra IS NULL")
    List<thongtin_sudung> findDatChoByThanhvienId(@Param("tvId") Long tvId);

    //tìm những thông tin sử dụng đang được mượn theo id thiết bị
    @Query("SELECT tsd FROM thongtin_sudung tsd WHERE tsd.thietbi.id = :tbId AND tsd.ngaymuon IS NOT NULL AND tsd.ngaytra IS NULL")
    List<thongtin_sudung> findBorrowingByThietbiId(@Param("tbId") Integer tbId);

    // null id thành viên trước khi xóa thành viên
    @Modifying
    @Query("UPDATE thongtin_sudung ts SET ts.thanhvien = NULL WHERE ts.thanhvien.id = :id")
    void nullifyThanhvienInThongtinSudung(@Param("id") Long id);

    //xóa thông tin sử dụng theo id thành viên
    @Modifying
    @Query("DELETE FROM thongtin_sudung tsd WHERE tsd.thanhvien.id = :tvId")
    void deleteByThanhvienId(@Param("tvId") Long tvId);

    //xóa thông tin sử dụng theo id thiết bị
    @Modifying
    @Query("DELETE FROM thongtin_sudung tsd WHERE tsd.thietbi.id = :tbId")
    void deleteByThietbiId(@Param("tbId") Integer tbId);

}
