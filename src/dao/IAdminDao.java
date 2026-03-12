package dao;

import model.Admin;

public interface IAdminDao {
    Admin findAdminByUsername(String username);
}
