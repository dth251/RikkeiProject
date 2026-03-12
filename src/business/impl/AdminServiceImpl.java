package business.impl;

import business.IAdminService;
import dao.IAdminDao;
import dao.impl.AdminDaoImpl;
import model.Admin;

public class AdminServiceImpl implements IAdminService {
    public final IAdminDao Admindao = new AdminDaoImpl();
    @Override
    public Admin login(String username, String password){
        Admin ad = Admindao.findAdminByUsername(username);
        if (ad != null && ad.getPassword().equals(password)){
            return ad;
        } else {
            return null;
        }
    }
}
