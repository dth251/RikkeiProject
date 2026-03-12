package business.impl;

import business.ICustomerService;
import dao.ICustomerDao;
import dao.impl.CustomerDaoImpl;
import model.Customer;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerDao customerDao = new CustomerDaoImpl();
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    //them khach hang moi
    @Override
    public boolean addCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            System.out.println(RED + "Lỗi logic: Tên khách hàng không được để trống!" + RESET);
            return false;
        }

        if (customer.getEmail() == null || !customer.getEmail().trim().isEmpty()) {
            if (customerDao.isEmailExist(customer.getEmail())) {
                System.out.println(RED + "Lỗi logic: Email đã tồn tại, vui lòng sử dụng email khác!" + RESET);
                return false;
            }
        }
        return customerDao.addCustomer(customer);
    }

    // hien thi danh sach khach hang
    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    // tim kiem khach hang theo id
    @Override
    public Customer getCustomerById(int id) {
        return customerDao.getCustomerById(id);
    }

    // cap nhat thong tin khach hang
    @Override
    public boolean updateCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            System.out.println(RED + "Lỗi logic: Tên khách hàng không được để trống!" + RESET);
            return false;
        }

        if (customer.getEmail() == null || !customer.getEmail().trim().isEmpty()) {
            if (customerDao.isEmailExistForOtherCustomer(customer.getEmail(), customer.getId())) {
                System.out.println(RED + "Lỗi logic: Email đã tồn tại, vui lòng sử dụng email khác!" + RESET);
                return false;
            }
        }
        return customerDao.updataCustomer(customer);
    }

    // xoa khach hang
    @Override
    public boolean deleteCustomer(int id) {
        return customerDao.deleteCustomer(id);
    }

}
