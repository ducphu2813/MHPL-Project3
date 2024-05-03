package com.project3.project3.Repository;

import com.project3.project3.Model.xuly;
import org.springframework.data.jpa.repository.JpaRepository;

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
    
}
