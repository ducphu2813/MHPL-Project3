package com.project3.project3.Repository;

import com.project3.project3.Model.thanhvien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface thanhvienRepository extends JpaRepository<thanhvien, Long> {

    thanhvien getByTen(String ten);

    //kiểm tra đăng nhập bằng id và password
    // sử dụng JPQL, tạo câu truy vấn bằng JPQL
    @Query("SELECT tv FROM thanhvien tv " +
            "WHERE tv.id = :id AND tv.password = :password")
    thanhvien checkLogin(@Param("id") Long id, @Param("password") String password);

    thanhvien getByEmail(String email);

    //xóa thành viên theo năm
    @Modifying
    @Query("DELETE FROM thanhvien tv WHERE YEAR(tv.created_date) = :year")
    void deleteByYear(@Param("year") int year);


    //tìm tất cả thành viên theo năm
    @Query("SELECT tv FROM thanhvien tv WHERE YEAR(tv.created_date) = :year")
    List<thanhvien> findByYear(@Param("year") int year);
}
