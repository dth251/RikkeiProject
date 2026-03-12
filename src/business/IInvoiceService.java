package business;

import model.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    boolean addInvoice(Invoice invoice);
    List<Invoice> getAllInvoices();
    List<Invoice> searchByCustomerName(String Name);
    List<Invoice> searchByDate(LocalDate date);

    double getRevenueByDate(java.time.LocalDate date);
    double getRevenueByMonth(int month, int year);
    double getRevenueByYear(int year);
}