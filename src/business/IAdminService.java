package business;

import model.Admin;

public interface IAdminService {
    Admin login(String username, String password);
}
