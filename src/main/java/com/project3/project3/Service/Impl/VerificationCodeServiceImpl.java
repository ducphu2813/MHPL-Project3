package com.project3.project3.Service.Impl;

import com.project3.project3.Model.VerificationCode;
import com.project3.project3.Repository.VerificationCodeRepository;
import com.project3.project3.Service.VerificationCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    VerificationCodeRepository verificationCodeRepository;

    public VerificationCodeServiceImpl(VerificationCodeRepository verificationCodeRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
    }

    @Override
    public List<VerificationCode> getAll() {
        return verificationCodeRepository.findAll();
    }

    @Override
    public VerificationCode findByCodeAndThanhvienId(String code, Long thanhvienId) {
        return verificationCodeRepository.findByCodeAndThanhvienId(code, thanhvienId);
    }

    @Override
    @Transactional
    public void save(VerificationCode verificationCode) {
        verificationCodeRepository.save(verificationCode);
    }

    @Override
    @Transactional
    public void deleteByThanhvienId(Long id) {
        verificationCodeRepository.deleteByThanhvienId(id);
    }
}
