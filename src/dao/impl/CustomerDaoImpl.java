package dao.impl;

import dao.ICustomerDao;
import model.Customer;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements ICustomerDao {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    // them khach hang moi
    @Override
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (name, phone, email,address) VALUES (?,?,?,?)";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, customer.getName());
            pre.setString(2, customer.getPhone());
            pre.setString(3, customer.getEmail());
            pre.setString(4, customer.getAddress());

            int result = pre.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    // kiem tra email da ton tai chua
    @Override
    public boolean isEmailExist(String email) {
        String sql = "SELECT COUNT(*) FROM Customer WHERE email = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, email);
            try (var rs = pre.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Nếu count > 0, email đã tồn tại
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // hien thi danh sach khach hang
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer ORDER BY id ASC";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setPhone(rs.getString("phone"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));

                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // tim kiem khach hang theo id
    @Override
    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM Customer WHERE id = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    c.setPhone(rs.getString("phone"));
                    c.setEmail(rs.getString("email"));
                    c.setAddress(rs.getString("address"));
                    return c;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // kiem tra email da ton tai cho khach hang khac chua
    @Override
    public boolean isEmailExistForOtherCustomer(String email, int id) {
        String sql = "SELECT COUNT(*) FROM Customer WHERE email = ? AND id != ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, email);
            pre.setInt(2, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Nếu count > 0, email đã tồn tại cho khách hàng khác
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // cap nhat thong tin
    @Override
    public boolean updataCustomer(Customer customer) {
        String sql = "UPDATE Customer SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setString(1, customer.getName());
            pre.setString(2, customer.getPhone());
            pre.setString(3, customer.getEmail());
            pre.setString(4, customer.getAddress());
            pre.setInt(5, customer.getId());

            int result = pre.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                System.out.println(RED + "Lỗi: Email đã tồn tại! Vui lòng sử dụng email khác." + RESET);
            } else {
                e.printStackTrace();
            }
        }
        return false;
    }

    // xoa khach hang
    @Override
    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM Customer WHERE id = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setInt(1, id);
            int result = pre.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

