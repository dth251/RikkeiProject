package business.impl;

import business.IInvoiceService;
import dao.IInvoiceDao;
import dao.impl.InvoiceDaoImpl;
import model.Invoice;

import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService {
    private final IInvoiceDao invoiceDao = new InvoiceDaoImpl();
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    //them hoa don
    @Override
    public boolean addInvoice(Invoice invoice) {
        if (invoice.getDetails().isEmpty()) {
            System.out.println(RED + "Lỗi: Hóa đơn không có sản phẩm nào!" + RESET);
            return false;
        }
        return invoiceDao.addInvoice(invoice);
    }

    // hien thi hoa don
    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceDao.getAllInvoices();
    }

    //tim kiem hoa don theo ten khach hang
    @Override
    public  List<Invoice> searchByCustomerName(String name) {
        if (name == null) name = "";
        return invoiceDao.searchByCustomerName(name.trim());
    }

    //tim kiem hoa don theo ngay thang nam
    @Override
    public List<Invoice> searchByDate(java.time.LocalDate date) {
        return invoiceDao.searchByDate(date);
    }

    // thong ke doanh thu theo ngay
    @Override
    public double getRevenueByDate(java.time.LocalDate date) {
        return invoiceDao.getRevenueByDate(date);
    }

    //thong ke doanh thu theo thang
    @Override
    public double getRevenueByMonth(int month, int year) {
        return invoiceDao.getRevenueByMonth(month, year);
    }

    // thong ke doanh thu theo nam
    @Override
    public double getRevenueByYear(int year) {
        return invoiceDao.getRevenueByYear(year);
    }
}