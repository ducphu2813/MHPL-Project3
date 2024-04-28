package com.project3.project3.Repository;

import com.project3.project3.Model.thietbi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface thietbiRepository extends JpaRepository<thietbi, Integer> {

    thietbi getByTen(String name);

    // tìm kiếm thiết bị theo tên
    List<thietbi> findByTenContaining(String name);

    //tìm kiếm thiết bị có thể cho đặt chỗ theo tên
    //thiết bị có thể cho đặt chỗ là thiết bị chưa được mượn hoặc đã được mượn nhưng đã trả hoặc đã hết hạn đặt chỗ hoặc đặt chỗ khác ngày đang được đặt chỗ
    @Query("SELECT tb FROM thietbi tb " +
            "WHERE tb.ten LIKE CONCAT('%', :name, '%') AND " +
            "tb.id NOT IN " +
            "(SELECT tsd.thietbi.id FROM thongtin_sudung tsd " +
            "WHERE tsd.ngaytra IS NULL AND " +
            "tsd.ngaymuon IS NOT NULL) ")
    List<thietbi> findNotBorrowedByName(@Param("name") String name);

    //tìm kiếm thiết bị đang được mượn theo tên, ko thể đặt chỗ
    @Query("SELECT tb FROM thietbi tb " +
            "WHERE tb.ten LIKE CONCAT('%', :name, '%') AND " +
            "tb.id IN (SELECT tsd.thietbi.id FROM thongtin_sudung tsd WHERE tsd.ngaytra IS NULL AND tsd.ngaymuon IS NOT NULL)")
    List<thietbi> findBorrowedByName(@Param("name") String name);

}
