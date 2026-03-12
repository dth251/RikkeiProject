package business.impl;

import business.IProductService;
import dao.IProductDao;
import dao.impl.ProductDaoImpl;
import model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements IProductService {
    public final IProductDao productDao = new ProductDaoImpl();
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    // Hiển thị danh sách sản phẩm
    @Override
    public List<Product> getAllProducts(){
        return productDao.getAllProducts();
    }


    // Thêm sản phẩm mới
    @Override
    public boolean addProduct(Product product){
        if((product.getName() == null  || product.getName().trim().isEmpty())){
            System.out.println(RED + "Lỗi: Tên sản phẩm không được để trống!" + RESET);
            return false;
        }
        if((product.getBrand() == null || product.getBrand().trim().isEmpty())){
            System.out.println(RED + "Lỗi: Tên hãng không được để trống!" + RESET);
            return false;
        }

        if((product.getPrice() < 0)){
            System.out.println(RED + "Lỗi: Giá sản phẩm không được âm!" + RESET);
            return false;
        }

        if (product.getStock() < 0){
            System.out.println(RED + "Lỗi: Số lượng tồn kho không được âm!" + RESET);
            return false;
        }

        return productDao.addProduct(product);
    }

    // Lấy thông tin sản phẩm theo ID
    @Override
    public Product getProductByid(int id){
        return productDao.getProductById(id);
    }

    // Cập nhật thông tin sản phẩm
    @Override
    public boolean updateProduct(Product product){
        if((product.getPrice() < 0)){
            System.out.println(RED + "Lỗi: Giá sản phẩm không được âm!" + RESET);
            return false;
        }

        if (product.getStock() < 0){
            System.out.println(RED + "Lỗi: Số lượng tồn kho không được âm!" + RESET);
            return false;
        }
        return productDao.updateProduct(product);
    }

    // Xóa sản phẩm
    @Override
    public boolean deleteProduct(int id){
        return productDao.deleteProduct(id);
    }


    // Tìm kiếm sản phẩm theo Brand
    @Override
    public List<Product> searchProductsByBrand(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println(RED + "Lỗi nhập liệu: Từ khóa tìm kiếm không được để trống!" + RESET);
            return new ArrayList<>();
        }
        keyword = keyword.trim();
        return productDao.searchProductsByBrand(keyword);
    }

    // Tìm kiếm sản phẩm theo khoảng giá
    @Override
    public List<Product> searchProductsByPriceRange(double startPrice, double endPrice) {
        if (startPrice < 0 || endPrice < 0) {
            System.out.println(RED + "Lỗi nhập liệu: Giá không được âm!" + RESET);
            return new ArrayList<>();
        }
        if (startPrice > endPrice) {
            System.out.println(RED + "Lỗi nhập liệu: Giá bắt đầu phải nhỏ hơn hoặc bằng giá kết thúc!" + RESET);
            return new ArrayList<>();
        }
        return productDao.searchProductsByPriceRange(startPrice, endPrice);
    }

    // Tìm kiếm sản phẩm  theo tên
    @Override
    public List<Product> searchProductsByName(String keyword, int minStock){
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println(RED + "Lỗi nhập liệu: Từ khóa tìm kiếm không được để trống!" + RESET);
            return new ArrayList<>();
        }
        if (minStock < 0) {
            System.out.println(RED + "Lỗi nhập liệu: Số lượng tồn kho tối thiểu không được âm!" + RESET);
            return new ArrayList<>();
        }
        keyword = keyword.trim();
        return productDao.searchProductsByName(keyword, minStock);
    }

}
