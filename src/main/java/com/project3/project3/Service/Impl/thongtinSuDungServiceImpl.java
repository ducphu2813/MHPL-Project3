package com.project3.project3.Service.Impl;

import com.project3.project3.Model.thongtin_sudung;
import com.project3.project3.Repository.thongtinSuDungRepository;
import com.project3.project3.Service.thongtinSuDungService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class thongtinSuDungServiceImpl implements thongtinSuDungService {

    thongtinSuDungRepository thongtinSuDungRepository;

    public thongtinSuDungServiceImpl(thongtinSuDungRepository thongtinSuDungRepository) {
        this.thongtinSuDungRepository = thongtinSuDungRepository;
    }

    @Override
    public List<thongtin_sudung> getAll() {
        return thongtinSuDungRepository.findAll();
    }

    @Override
    public thongtin_sudung findById(Integer id) {
        return thongtinSuDungRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(thongtin_sudung thongtin_sudung) {
        thongtinSuDungRepository.save(thongtin_sudung);
    }

    @Override
    public boolean checkDatCho(Integer tbId, LocalDateTime tg_datcho) {
        List<thongtin_sudung> tsdList = thongtinSuDungRepository.findByThietbiIdAndTg_datcho(tbId, tg_datcho);

        if (tsdList.isEmpty()) {
            // Không có thời gian đặt chỗ nào trong ngày đó thì cho đặt chỗ thoải mái
            return true;
        }

        LocalDateTime start = tg_datcho.minus(1, ChronoUnit.HOURS);
        LocalDateTime end = tg_datcho.plus(1, ChronoUnit.HOURS);

        //Kiểm tra trước và sau thời gian đặt chỗ 1 tiếng xem có thời gian đặt chỗ nào khác không
        for (thongtin_sudung tsd : tsdList) {

            if ((tsd.getTg_datcho().isEqual(start) || tsd.getTg_datcho().isAfter(start)) &&
                    (tsd.getTg_datcho().isEqual(end) || tsd.getTg_datcho().isBefore(end))) {
                // Nếu có bất kỳ thời gian đặt chỗ nào trong khoảng 1 tiếng thì không cho đặt chỗ
                return false;
            }
        }

        // Không có thì trả về true, cho đặt chỗ
        return true;
    }

    @Override
    public boolean isBorrowed(Integer tbId) {
        List<thongtin_sudung> tsdList = thongtinSuDungRepository.findBorrowingByThietbiId(tbId);
        return !tsdList.isEmpty();
    }

    //hàm kiểm tra xem thiết bị đó có thể cho mượn không
    @Override
    public boolean canBeBorrowed(Integer tbId) {

        List<thongtin_sudung> dangMuon = thongtinSuDungRepository.findBorrowingByThietbiId(tbId);
        List<thongtin_sudung> datCho = thongtinSuDungRepository.findByThietbiIdAndTg_datcho(tbId, LocalDateTime.now());
        List<thongtin_sudung> allTTSD = thongtinSuDungRepository.findDatCho();

        //thời gian mượn
        LocalDateTime now = LocalDateTime.now();

        //kiểm tra xem thiết bị có đang được mượn hay không
        for(thongtin_sudung tsd : dangMuon){
            if(tsd.getThietbi().getId().equals(tbId)){
                return false;
            }
        }

        //duyệt qua tất cả danh sách thông tin đặt chỗ, nếu có bất cứ đặt chỗ nào mà sau ngày hiện tại thì không cho mượn
        for(thongtin_sudung tsd : allTTSD){
            if(tsd.getThietbi().getId().equals(tbId) && tsd.getTg_datcho().isAfter(now)){
                return false;
            }
        }

        //trong danh sách datCho, lấy ra thời gian đặt chỗ cuối cùng của ngày hôm đó
        LocalDateTime lastestDatCho = datCho.stream().filter(tsd -> tsd.getThietbi().getId().equals(tbId))
                .map(thongtin_sudung::getTg_datcho)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        //nếu không tìm thấy, có nghĩa là hôm đó chưa có ai đặt chỗ, cho mượn
        if(lastestDatCho == null){
            return true;
        }

        //thời gian đặt chỗ cuối cùng trong ngày hôm đó + 1 tiếng mà nhỏ hơn thời gian hiện tại thì cho mượn
        return lastestDatCho.plus(1, ChronoUnit.HOURS).isBefore(now);
    }

    //lấy thông tin sử dụng đang được mượn theo id thiết bị
    @Override
    public thongtin_sudung getBorrowingByThietbiId(Integer tbId) {

        List<thongtin_sudung> tsdList = thongtinSuDungRepository.findBorrowingByThietbiId(tbId);

        if(tsdList.isEmpty()){
            return null;
        }

        return tsdList.get(0);
    }

    //lấy tất cả thông tin sử dụng đang được mượn
    @Override
    public List<thongtin_sudung> findBorrowing() {
        return thongtinSuDungRepository.findBorrowing();
    }

    //lấy tất cả thông tin sử dụng đã được trả
    @Override
    public List<thongtin_sudung> findReturned() {
        return thongtinSuDungRepository.findReturned();
    }

    //lấy tất cả đặt chỗ
    @Override
    public List<thongtin_sudung> findDatCho() {
        return thongtinSuDungRepository.findDatCho();
    }

    @Override
    public List<thongtin_sudung> findBorrowingByThanhvienId(Long tvId) {
        return thongtinSuDungRepository.findBorrowingByThanhvienId(tvId);
    }

    @Override
    public List<thongtin_sudung> findReturnedByThanhvien(Long tvId) {
        return thongtinSuDungRepository.findReturnedByThanhvien(tvId);
    }

    @Override
    public List<thongtin_sudung> findDatChoByThanhvienId(Long tvId) {
        return thongtinSuDungRepository.findDatChoByThanhvienId(tvId);
    }

    @Override
    @Transactional
    public void nullifyThanhvienInThongtinSudung(Long id) {
        thongtinSuDungRepository.nullifyThanhvienInThongtinSudung(id);
    }

    @Override
    @Transactional
    public void deleteByThanhvienId(Long tvId) {
        thongtinSuDungRepository.deleteByThanhvienId(tvId);
    }

    @Override
    @Transactional
    public void deleteByThietbiId(Integer tbId) {
        thongtinSuDungRepository.deleteByThietbiId(tbId);
    }
}
