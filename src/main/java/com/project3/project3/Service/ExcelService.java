package com.project3.project3.Service;

import com.project3.project3.DTO.ThanhVienDTO;

import com.project3.project3.DTO.ThietBiDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService {



    public List<ThanhVienDTO> parseExcelFile(InputStream in){

        DecimalFormat df = new DecimalFormat("#");
        List<ThanhVienDTO> thanhVienDTOs = new ArrayList<>();

        try {
            //Tạo một workbook từ file excel truyền vào qua InputStream
            Workbook workbook = new XSSFWorkbook(in);

            // Lấy ra sheet đầu tiên từ workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Lấy ra tất cả các dòng của sheet
            Iterator<Row> rowIterator = sheet.iterator();

            rowIterator.next(); // bỏ qua dòng đầu tiên

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //Lặp qua từng cột của dòng
                Iterator<Cell> cellIterator = row.cellIterator();

                ThanhVienDTO tvDTO = new ThanhVienDTO();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    // check kiểu dữ liệu và thêm dữ liệu
                    switch (cell.getColumnIndex()) {
                        case 0:
                            switch (cell.getCellType()) {
                                case STRING:
                                    tvDTO.setTen(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    tvDTO.setTen(String.valueOf(cell.getNumericCellValue()));
                                    break;
                            }
                            break;
                        case 1:
                            switch (cell.getCellType()) {
                                case STRING:
                                    tvDTO.setTenKhoa(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    tvDTO.setTenKhoa(String.valueOf(cell.getNumericCellValue()));
                                    break;
                            }
                            break;
                        case 2:
                            switch (cell.getCellType()) {
                                case STRING:
                                    tvDTO.setTenNganh(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    tvDTO.setTenNganh(String.valueOf(cell.getNumericCellValue()));
                                    break;
                            }
                            break;
                        case 3:
                            switch (cell.getCellType()) {
                                case STRING:
                                    tvDTO.setSodienthoai(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    if (Math.floor(cell.getNumericCellValue()) == cell.getNumericCellValue()) {
                                        tvDTO.setSodienthoai(df.format(cell.getNumericCellValue()));
                                    } else {
                                        tvDTO.setSodienthoai(String.valueOf(cell.getNumericCellValue()));
                                    }
                                    break;
                            }
                            break;
                        case 4:
                            switch (cell.getCellType()) {
                                case STRING:
                                    tvDTO.setEmail(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    tvDTO.setEmail(String.valueOf(cell.getNumericCellValue()));
                                    break;
                            }
                            break;
                        case 5:
                            switch (cell.getCellType()) {
                                case STRING:
                                    tvDTO.setPassword(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    tvDTO.setPassword(String.valueOf(cell.getNumericCellValue()));
                                    break;
                            }
                            break;
                    }
                    tvDTO.setCreated_date(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
                }
                thanhVienDTOs.add(tvDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return thanhVienDTOs;
    }

    public List<ThietBiDTO> parseExcelFileThietBi(InputStream in){

        DecimalFormat df = new DecimalFormat("#");
        List<ThietBiDTO> ThietBiDTOs = new ArrayList<>();

        try {
            //Tạo một workbook từ file excel truyền vào qua InputStream
            Workbook workbook = new XSSFWorkbook(in);

            // Lấy ra sheet đầu tiên từ workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Lấy ra tất cả các dòng của sheet
            Iterator<Row> rowIterator = sheet.iterator();

            rowIterator.next(); // bỏ qua dòng đầu tiên

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //Lặp qua từng cột của dòng
                Iterator<Cell> cellIterator = row.cellIterator();

                ThietBiDTO tbDTO = new ThietBiDTO();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    // check kiểu dữ liệu và thêm dữ liệu
                    switch (cell.getColumnIndex()) {
                        case 0:
                            switch (cell.getCellType()) {
                                case STRING:
                                    tbDTO.setTen(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    tbDTO.setTen(String.valueOf(cell.getNumericCellValue()));
                                    break;
                            }
                            break;
                        case 1:
                            switch (cell.getCellType()) {
                                case STRING:
                                    tbDTO.setMota(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    tbDTO.setMota(String.valueOf(cell.getNumericCellValue()));
                                    break;
                            }
                            break;
                        case 2:
                            switch (cell.getCellType()) {
                                case STRING:
                                    tbDTO.setTenLoaiTb(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    tbDTO.setTenLoaiTb(String.valueOf(cell.getNumericCellValue()));
                                    break;
                            }
                            break;
                    }
                }
                ThietBiDTOs.add(tbDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ThietBiDTOs;
    }
}
