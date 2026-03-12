package business;

import model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    //them hoa don
    boolean addInvoice(Invoice invoice);
    //hien thi tat ca hoa don
    List<Invoice> getAllInvoices();
    //tim hoa don ( tim theo ten && tim theo ngay)
    List<Invoice> searchByCustomerName(String Name);
    List<Invoice> searchByDate(LocalDate date);

    // thong ke doanh thu theo ngay, thang, nam
    double getRevenueByDate(java.time.LocalDate date);
    double getRevenueByMonth(int month, int year);
    double getRevenueByYear(int year);
}