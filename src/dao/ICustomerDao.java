package dao;

import model.Customer;

import java.util.List;

public interface ICustomerDao {
    // them khach hang moi
    boolean addCustomer(Customer customer);
    boolean isEmailExist(String email); // kiem tra xem email da ton tai chua
    // hien thi danh sach khach hang
    List <Customer> getAllCustomers();
    // cap nhat thong tin khach hang
    Customer getCustomerById(int id);
    boolean updataCustomer(Customer customer);
    boolean isEmailExistForOtherCustomer(String email, int id); // kiem tra xem email da ton tai cho khach hang khac chua
    // xoa khach hang
    boolean deleteCustomer(int id);
}
