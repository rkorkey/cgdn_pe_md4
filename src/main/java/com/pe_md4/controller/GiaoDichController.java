package com.pe_md4.controller;

import com.pe_md4.entity.GiaoDich;
import com.pe_md4.entity.KhachHang;
import com.pe_md4.repository.KhachHangRepository;
import com.pe_md4.service.GiaoDichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/giao-dich")
public class GiaoDichController {

    private final GiaoDichService giaoDichService;
    private final KhachHangRepository khachHangRepository;

    @Autowired
    public GiaoDichController(GiaoDichService giaoDichService, KhachHangRepository khachHangRepository) {
        this.giaoDichService = giaoDichService;
        this.khachHangRepository = khachHangRepository;
    }

    @GetMapping
    public String viewList(@RequestParam(name = "keywordKH", defaultValue = "") String keywordKH,
                           @RequestParam(name = "loaiDV", defaultValue = "") String loaiDV,
                           Model model) {
        model.addAttribute("giaoDichList", giaoDichService.search(keywordKH, loaiDV));
        model.addAttribute("keywordKH", keywordKH);
        model.addAttribute("loaiDV", loaiDV);

        // Dropdown cho màn hình tìm kiếm: sử dụng 2 loại cố định
        List<String> loaiDichVuOptions = Arrays.asList("Nhà và đất", "Đất");
        model.addAttribute("loaiDichVuOptions", loaiDichVuOptions);

        return "giao_dich/list";
    }

    // Hiển thị form thêm mới giao dịch
    @GetMapping("/add")
    public String addForm(Model model) {
        // Tạo đối tượng giao dịch rỗng để binding form
        model.addAttribute("giaoDich", new GiaoDich());
        // Lấy danh sách khách hàng cho dropdown
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        model.addAttribute("khachHangList", khachHangList);
        // Cung cấp danh sách loại dịch vụ cố định
        List<String> loaiDichVuOptions = Arrays.asList("Nhà và đất", "Đất");
        model.addAttribute("loaiDichVuOptions", loaiDichVuOptions);
        return "giao_dich/add";
    }

    // Xử lý form thêm mới giao dịch
    @PostMapping("/add")
    public String addSubmit(@ModelAttribute("giaoDich") GiaoDich giaoDich, Model model) {
        String errorMsg = validateGiaoDich(giaoDich);
        if (!errorMsg.isEmpty()) {
            // Nếu có lỗi, tải lại danh sách khách hàng và loại dịch vụ cho form
            List<KhachHang> khachHangList = khachHangRepository.findAll();
            model.addAttribute("khachHangList", khachHangList);
            List<String> loaiDichVuOptions = Arrays.asList("Nhà và đất", "Đất");
            model.addAttribute("loaiDichVuOptions", loaiDichVuOptions);
            model.addAttribute("errorMsg", errorMsg);
            return "giao_dich/add";
        }
        // Nếu hợp lệ, lưu giao dịch và chuyển hướng về danh sách giao dịch
        giaoDichService.save(giaoDich);
        model.addAttribute("successMsg", "Thêm giao dịch thành công!");
        return "redirect:/giao-dich";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        GiaoDich gd = giaoDichService.findById(id);
        if (gd == null) {
            return "redirect:/giao-dich";
        }
        model.addAttribute("giaoDich", gd);
        return "giao_dich/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        giaoDichService.deleteById(id);
        return "redirect:/giao-dich";
    }

    // Phương thức kiểm tra lỗi dữ liệu nhập
    private String validateGiaoDich(GiaoDich gd) {
        StringBuilder sb = new StringBuilder();
        // Kiểm tra mã giao dịch
        if (gd.getMaGiaoDich() == null || gd.getMaGiaoDich().trim().isEmpty()) {
            sb.append("Mã giao dịch là bắt buộc. ");
        } else {
            if (!gd.getMaGiaoDich().matches("MGD-\\d{4}")) {
                sb.append("Mã giao dịch phải có định dạng MGD-XXXX, trong đó XXXX là 4 chữ số. ");
            }
        }
        // Kiểm tra tên khách hàng (dựa trên khachHang.id được binding)
        if (gd.getKhachHang() == null || gd.getKhachHang().getId() == null) {
            sb.append("Tên khách hàng là bắt buộc. ");
        }
        // Kiểm tra loại dịch vụ
        if (gd.getLoaiDichVu() == null || gd.getLoaiDichVu().trim().isEmpty()) {
            sb.append("Loại dịch vụ là bắt buộc. ");
        }
        // Kiểm tra ngày giao dịch
        if (gd.getNgayGiaoDich() == null || gd.getNgayGiaoDich().trim().isEmpty()) {
            sb.append("Ngày giao dịch là bắt buộc. ");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                Date ngayGD = sdf.parse(gd.getNgayGiaoDich());
                if (!ngayGD.after(new Date())) {
                    sb.append("Ngày giao dịch phải lớn hơn thời gian hiện tại. ");
                }
            } catch (ParseException e) {
                sb.append("Ngày giao dịch phải theo định dạng dd/MM/yyyy. ");
            }
        }
        // Kiểm tra đơn giá
        if (gd.getDonGia() == null) {
            sb.append("Đơn giá là bắt buộc. ");
        } else {
            if (gd.getDonGia() <= 500000) {
                sb.append("Đơn giá phải lớn hơn 500.000 VND. ");
            }
        }
        // Kiểm tra diện tích
        if (gd.getDienTich() == null) {
            sb.append("Diện tích là bắt buộc. ");
        } else {
            if (gd.getDienTich() <= 20) {
                sb.append("Diện tích phải lớn hơn 20 m2. ");
            }
        }
        return sb.toString();
    }
}
