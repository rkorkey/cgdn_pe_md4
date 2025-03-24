package com.pe_md4.service;

import com.pe_md4.entity.GiaoDich;
import com.pe_md4.repository.GiaoDichRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiaoDichService {

    private final GiaoDichRepository giaoDichRepository;

    public GiaoDichService(GiaoDichRepository giaoDichRepository) {
        this.giaoDichRepository = giaoDichRepository;
    }

    // Lấy danh sách tất cả giao dịch
    public List<GiaoDich> getAllGiaoDich() {
        return giaoDichRepository.findAll();
    }

    // Tìm kiếm giao dịch theo tên khách hàng và loại dịch vụ (so sánh bằng)
    public List<GiaoDich> search(String tenKH, String loaiDV) {
        if (loaiDV == null || loaiDV.trim().isEmpty()) {
            return giaoDichRepository.findByKhachHang_TenKhachHangContainingIgnoreCase(tenKH);
        } else {
            return giaoDichRepository.findByKhachHang_TenKhachHangContainingIgnoreCaseAndLoaiDichVu(tenKH, loaiDV);
        }
    }

    // Lưu giao dịch mới
    public void save(GiaoDich giaoDich) {
        giaoDichRepository.save(giaoDich);
    }

    public GiaoDich findById(Long id) {
        return giaoDichRepository.findById(id).orElse(null);
    }
    public void deleteById(Long id) {
        giaoDichRepository.deleteById(id);
    }
}
