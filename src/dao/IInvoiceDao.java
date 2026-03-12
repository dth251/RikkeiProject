package dao;

import model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceDao {
    //them hoa don
    boolean addInvoice(Invoice invoice);
    //hien thi tat ca hoa don
    List<Invoice> getAllInvoices();
    //tim kiem hoa don theo ten khach hang va ten ngay
    List<Invoice> searchByCustomerName(String name);
    List<Invoice> searchByDate(LocalDate date);

    // thong ke doanh thu theo ngay, thang, nam
    double getRevenueByDate(java.time.LocalDate date);
    double getRevenueByMonth(int month, int year);
    double getRevenueByYear(int year);
}
