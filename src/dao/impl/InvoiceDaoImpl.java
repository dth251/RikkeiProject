package dao.impl;

import dao.IInvoiceDao;
import model.Invoice;
import model.InvoiceDetail;
import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDaoImpl implements IInvoiceDao {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";


    // them hoa don
    @Override
    public boolean addInvoice(Invoice invoice) {
        String sqlInv = "INSERT INTO invoice (customer_id, total_amount) VALUES (?, ?)";
        String sqlDetail = "INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        String sqlUpdateStock = "UPDATE product SET stock = stock - ? WHERE id = ?";


        Connection conn = ConnectionDB.getConnection();
        if (conn == null) return false;

        try {

            conn.setAutoCommit(false);


            try (PreparedStatement preInv = conn.prepareStatement(sqlInv, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement preDetail = conn.prepareStatement(sqlDetail);
                 PreparedStatement preUpdateStock = conn.prepareStatement(sqlUpdateStock)) {


                preInv.setInt(1, invoice.getCustomerId());
                preInv.setDouble(2, invoice.getTotalAmount());
                preInv.executeUpdate();

                ResultSet rs = preInv.getGeneratedKeys();
                int newInvoiceId = 0;
                if (rs.next()) {
                    newInvoiceId = rs.getInt(1);
                }


                for (InvoiceDetail detail : invoice.getDetails()) {

                    preDetail.setInt(1, newInvoiceId);
                    preDetail.setInt(2, detail.getProductId());
                    preDetail.setInt(3, detail.getQuantity());

                    preDetail.setDouble(4, detail.getUnitPrice());
                    preDetail.addBatch();


                    preUpdateStock.setInt(1, detail.getQuantity());
                    preUpdateStock.setInt(2, detail.getProductId());
                    preUpdateStock.addBatch();
                }

                preDetail.executeBatch();
                preUpdateStock.executeBatch();
            }


            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {

                if (conn != null) {
                    conn.rollback();

                    System.out.println(RED + "Đã Rollback dữ liệu an toàn do có lỗi xảy ra ở hệ thống!" + RESET);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //hien thi hoa don
    @Override
    public List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT i.id, i.customer_id, i.created_at, i.total_amount, c.name AS customer_name " +
                "FROM invoice i " +
                "JOIN customer c ON i.customer_id = c.id " +
                "ORDER BY i.created_at DESC";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql);
             ResultSet rs = pre.executeQuery()) {

            while (rs.next()) {
                Invoice inv = new Invoice();
                inv.setId(rs.getInt("id"));
                inv.setCustomerId(rs.getInt("customer_id"));

                Timestamp timestamp = rs.getTimestamp("created_at");
                if (timestamp != null) {
                    inv.setCreatedAt(timestamp.toLocalDateTime());
                }

                inv.setTotalAmount(rs.getDouble("total_amount"));

                // Đổ tên khách hàng lấy từ câu JOIN vào thuộc tính phụ
                inv.setCustomerName(rs.getString("customer_name"));

                list.add(inv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //tim kiem hoa don theo ten khach hang
    @Override
    public List<Invoice> searchByCustomerName(String name) {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT i.id, i.customer_id, i.created_at, i.total_amount, c.name AS customer_name " +
                "FROM invoice i " +
                "JOIN customer c ON i.customer_id = c.id " +
                "WHERE c.name ILIKE ? " +
                "ORDER BY i.created_at DESC";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setString(1, "%" + name + "%"); // Thêm % để tìm gần đúng

            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    Invoice inv = new Invoice();
                    inv.setId(rs.getInt("id"));
                    inv.setCustomerId(rs.getInt("customer_id"));

                    Timestamp timestamp = rs.getTimestamp("created_at");
                    if (timestamp != null) {
                        inv.setCreatedAt(timestamp.toLocalDateTime());
                    }

                    inv.setTotalAmount(rs.getDouble("total_amount"));
                    inv.setCustomerName(rs.getString("customer_name"));

                    list.add(inv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // tim kiem khach hang theo ngay/thang/nam
    @Override
    public List<Invoice> searchByDate(java.time.LocalDate date) {
        List<Invoice> list = new ArrayList<>();

        String sql = "SELECT i.id, i.customer_id, i.created_at, i.total_amount, c.name AS customer_name " +
                "FROM invoice i " +
                "JOIN customer c ON i.customer_id = c.id " +
                "WHERE CAST(i.created_at AS DATE) = ? " +
                "ORDER BY i.created_at DESC";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setDate(1, java.sql.Date.valueOf(date));

            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    Invoice inv = new Invoice();
                    inv.setId(rs.getInt("id"));
                    inv.setCustomerId(rs.getInt("customer_id"));

                    Timestamp timestamp = rs.getTimestamp("created_at");
                    if (timestamp != null) {
                        inv.setCreatedAt(timestamp.toLocalDateTime());
                    }

                    inv.setTotalAmount(rs.getDouble("total_amount"));
                    inv.setCustomerName(rs.getString("customer_name"));

                    list.add(inv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // thong ke doanh thu theo ngay
    @Override
    public double getRevenueByDate(java.time.LocalDate date) {
        // Ép kiểu created_at về DATE để so sánh chính xác theo ngày
        String sql = "SELECT SUM(total_amount) FROM invoice WHERE CAST(created_at AS DATE) = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // thong ke doanh thu theo thang
    @Override
    public double getRevenueByMonth(int month, int year) {
        String sql = "SELECT SUM(total_amount) FROM invoice WHERE EXTRACT(MONTH FROM created_at) = ? AND EXTRACT(YEAR FROM created_at) = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setInt(1, month);
            pre.setInt(2, year);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // thong ke doanh thu theo nam
    @Override
    public double getRevenueByYear(int year) {
        String sql = "SELECT SUM(total_amount) FROM invoice WHERE EXTRACT(YEAR FROM created_at) = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setInt(1, year);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

