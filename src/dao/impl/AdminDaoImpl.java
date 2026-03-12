package dao.impl;

import dao.IAdminDao;
import model.Admin;
import utils.ConnectionDB;

import java.sql.*;


public class AdminDaoImpl implements IAdminDao {
    @Override
    public Admin findAdminByUsername(String username) {
        String sql = "SELECT * FROM Admin WHERE username = ?";
        try (
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement pre = conn.prepareStatement(sql)
                ){
            pre.setString(1, username);
            ResultSet rs = pre.executeQuery();
            if (rs.next()){
                int id = rs.getInt("id");
                String password = rs.getString("password");
                return new Admin(id, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}





