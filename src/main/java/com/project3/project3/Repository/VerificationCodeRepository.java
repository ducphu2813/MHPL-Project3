package com.project3.project3.Repository;

import com.project3.project3.Model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {

    @Query("SELECT v FROM VerificationCode v WHERE v.code = :code AND v.thanhvien.id = :thanhvienId")
    VerificationCode findByCodeAndThanhvienId(@Param("code") String code, @Param("thanhvienId") Long thanhvienId);

    //xóa theo id thành viên
    @Query("DELETE FROM VerificationCode v WHERE v.thanhvien.id = :id")
    @Modifying
    void deleteByThanhvienId(@Param("id") Long id);
}
