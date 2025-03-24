package com.pe_md4.repository;

import com.pe_md4.entity.GiaoDich;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiaoDichRepository extends JpaRepository<GiaoDich, Long> {

    // Tìm kiếm theo tên khách hàng (sử dụng chứa, không phân biệt hoa thường)
    List<GiaoDich> findByKhachHang_TenKhachHangContainingIgnoreCase(String tenKH);

    // Tìm kiếm theo tên khách hàng và loại dịch vụ (so sánh bằng, không dùng chứa)
    List<GiaoDich> findByKhachHang_TenKhachHangContainingIgnoreCaseAndLoaiDichVu(String tenKH, String loaiDV);
}
