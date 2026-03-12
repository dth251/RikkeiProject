package business;

import model.Customer;

import java.util.List;

public interface ICustomerService {
    // them khach hang moi
    boolean addCustomer(Customer customer);
    // hien thi danh sach khach hang
    List<Customer> getAllCustomers();
    // cap nhat thong tin khach hang
    Customer getCustomerById(int id);
    boolean updateCustomer(Customer customer);
    // xoa khach hang
    boolean deleteCustomer(int id);
}
