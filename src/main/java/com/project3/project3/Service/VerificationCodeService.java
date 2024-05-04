package com.project3.project3.Service;

import com.project3.project3.Model.VerificationCode;
import java.util.List;

public interface VerificationCodeService {

    List<VerificationCode> getAll();

    VerificationCode findByCodeAndThanhvienId(String code, Long thanhvienId);

    void save(VerificationCode verificationCode);

    void deleteByThanhvienId(Long id);
}
