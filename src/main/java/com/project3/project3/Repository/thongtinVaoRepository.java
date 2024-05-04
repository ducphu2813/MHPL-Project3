package com.project3.project3.Repository;

import com.project3.project3.Model.thongtin_vao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface thongtinVaoRepository extends JpaRepository<thongtin_vao, Integer> {


    // null id thành viên trước khi xóa thành viên
    @Modifying
    @Query("UPDATE thongtin_vao tv SET tv.thanhvien = NULL WHERE tv.thanhvien.id = :id")
    void nullifyThanhvienInThongtinVao(@Param("id") Long id);

    //xóa thông tin vào của thành viên
    @Modifying
    @Query("DELETE FROM thongtin_vao tv WHERE tv.thanhvien.id = :id")
    void deleteThongtinVaoByThanhvienId(@Param("id") Long id);
}
