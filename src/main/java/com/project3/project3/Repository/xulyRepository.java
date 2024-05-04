package com.project3.project3.Repository;

import com.project3.project3.Model.xuly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface xulyRepository extends JpaRepository<xuly, Integer> {

    //tìm tất cả xử lý có trạng thái false
    List<xuly> findByTrangthaiFalse();

    //tìm tất cả xử lý có trạng thái true
    List<xuly> findByTrangthaiTrue();


    //tìm xử lý theo id thành viên
    List<xuly> findByThanhvienId(Long id);

    //lấy những vi phạm chưa được xử lý theo thành viên
    List<xuly> findByThanhvienIdAndTrangthaiFalse(Long id);

    //null id thành viên trong xử lý trước khi xóa thành viên
    @Modifying
    @Query("UPDATE xuly x SET x.thanhvien = NULL WHERE x.thanhvien.id = :id")
    void nullifyThanhvienInXuly(@Param("id") Long id);

    //xóa xử lý theo id thành viên
    @Modifying
    @Query("DELETE FROM xuly x WHERE x.thanhvien.id = :id")
    void deleteByThanhvien(Long id);
    
}
