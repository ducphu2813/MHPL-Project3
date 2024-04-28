package com.project3.project3.Service.Impl;

import com.project3.project3.DTO.ThietBiDTO;
import com.project3.project3.Model.thietbi;
import com.project3.project3.Repository.loaiThietBiRepository;
import com.project3.project3.Service.thietbiService;
import org.springframework.stereotype.Service;
import com.project3.project3.Repository.thietbiRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class thietbiServiceImpl implements thietbiService {

    thietbiRepository thietbiRepository;

    loaiThietBiRepository loaiThietBiRepository;

    public thietbiServiceImpl(thietbiRepository thietbiRepository,
                              loaiThietBiRepository loaiThietBiRepository) {
        this.thietbiRepository = thietbiRepository;
        this.loaiThietBiRepository = loaiThietBiRepository;
    }

    @Override
    public thietbi findByName(String name) {
        return thietbiRepository.getByTen(name);
    }

    @Override
    public List<thietbi> findByTenContaining(String name) {
        return thietbiRepository.findByTenContaining(name);
    }

    //tìm kiếm thiết bị chưa được mượn
    @Override
    public List<thietbi> findNotBorrowedByName(String name) {
        return thietbiRepository.findNotBorrowedByName(name);
    }

    //tìm kiếm thiết bị đang được mượn
    @Override
    public List<thietbi> findBorrowedByName(String name) {
        return thietbiRepository.findBorrowedByName(name);
    }

    @Override
    public List<thietbi> findAll() {
        return thietbiRepository.findAll();
    }

    @Override
    public thietbi findById(Integer id) {
        return thietbiRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void save(ThietBiDTO tbDto) {

        thietbi thietbi = new thietbi();

        if(tbDto.getId() != null){
            //có id nghĩa là đang update
            thietbi.setId(tbDto.getId());
        }
        thietbi.setTen(tbDto.getTen());
        thietbi.setMota(tbDto.getMota());
        thietbi.setLoai_thietbi(loaiThietBiRepository.findById(tbDto.getLoaiThietBiId()).orElse(null));

        thietbiRepository.save(thietbi);

    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        thietbiRepository.deleteById(id);
    }
}
