package dao.impl;

import dao.IProductDao;
import model.Product;
import utils.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements IProductDao {
    // Thêm sản phẩm
    @Override
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO Product (name, brand, price, stock) VALUES (?, ?, ?, ?)";
        try ( Connection conn = ConnectionDB.getConnection();
              PreparedStatement pre = conn.prepareStatement(sql);
        ){
            pre.setString(1, product.getName());
            pre.setString(2, product.getBrand());
            pre.setDouble(3, product.getPrice());
            pre.setInt(4, product.getStock());

            int result = pre.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Hiển thị danh sách sản phẩm
    @Override
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product ORDER BY id ASC";
        try ( Connection conn = ConnectionDB.getConnection();
              PreparedStatement pre = conn.prepareStatement(sql);
              ResultSet rs = pre.executeQuery()
        ) {
            while(rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBrand(rs.getString("brand"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));

                list.add(p);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // Lấy thông tin sản phẩm theo ID
    @Override
    public Product getProductById(int id) {
        String sql = "SELECT * FROM Product Where id = ?";
        try(Connection conn = ConnectionDB.getConnection();
        PreparedStatement pre = conn.prepareStatement(sql);
        ){
            pre.setInt(1, id);
            try(ResultSet rs = pre.executeQuery()){
                if (rs.next()){
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setBrand(rs.getString("brand"));
                    p.setPrice(rs.getDouble("price"));
                    p.setStock(rs.getInt("stock"));

                    return p;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin sản phẩm
    @Override
    public boolean updateProduct(Product product){
        String sql = "UPDATE Product SET name = ?, brand = ?, price = ?, stock = ? WHERE id = ?";
        try(Connection conn = ConnectionDB.getConnection();
        PreparedStatement pre = conn.prepareStatement(sql)){
            pre.setString(1, product.getName());
            pre.setString(2,product.getBrand());
            pre.setDouble(3, product.getPrice());
            pre.setInt(4, product.getStock());
            pre.setInt(5, product.getId());

            int result = pre.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    // Xóa sản phẩm
    @Override
    public boolean deleteProduct(int id){
        String sql = "DELETE FROM Product WHERE id = ?";
        try(Connection conn = ConnectionDB.getConnection();
        PreparedStatement pre = conn.prepareStatement(sql)){
            pre.setInt(1, id);
            int result  = pre.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }


    // Tìm kiếm sản phẩm theo Brand
    @Override
    public List<Product> searchProductsByBrand(String keyword){
        List<Product> List = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE brand ILIKE ? ORDER BY id ASC";

        try(Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql);
        ){
            pre.setString(1, "%" + keyword + "%");
            try(ResultSet rs = pre.executeQuery()){
                while (rs.next()){
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setBrand(rs.getString("brand"));
                    p.setPrice(rs.getDouble("price"));
                    p.setStock(rs.getInt("Stock"));

                    List.add(p);
                }
            }

        } catch (SQLException e) {
           e.printStackTrace();
        }
        return List;
    }


    // Tìm kiếm theo khoảng giá
    @Override
    public List<Product> searchProductsByPriceRange(double startPrice, double endPrice){
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE price BETWEEN ? AND ? ORDER BY id ASC";
        try(Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        )
        {
            pre.setDouble(1,Double.valueOf(startPrice));
            pre.setDouble(2, Double.valueOf(endPrice));

            try(ResultSet rs = pre.executeQuery()){
                while (rs.next()){
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setBrand(rs.getString("brand"));
                    p.setPrice(rs.getDouble("price"));
                    p.setStock(rs.getInt("stock"));

                    list.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // Tìm kiếm sản phẩm theo tiền và số lượng tồn kho
    @Override
    public List<Product> searchProductsByName(String keyword, int minStock){
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE name ILIKE ? AND stock >= ? ORDER BY id ASC";
        try(Connection conn = ConnectionDB.getConnection();
            PreparedStatement pre = conn.prepareStatement(sql)
        ){
            pre.setString(1, "%" + keyword + "%");
            pre.setInt(2, minStock);

            try(ResultSet rs = pre.executeQuery()){
                while (rs.next()){
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setBrand(rs.getString("brand"));
                    p.setPrice(rs.getDouble("price"));
                    p.setStock(rs.getInt("stock"));

                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
