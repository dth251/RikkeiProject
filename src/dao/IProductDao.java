package dao;

import model.Product;
import java.util.List;

public interface IProductDao {
    // them san pham moi
    boolean addProduct(Product product);
    // hien thi san pham
    List<Product> getAllProducts();

    // lay san pham theo id
    Product getProductById(int id);
    // cap nhat san pham
    boolean updateProduct(Product product);
    // xoa san pham
    boolean deleteProduct(int id);
    //tim kiem san pham theo brand
    List<Product> searchProductsByBrand(String keyword);
    //tim kiem san pham theo khoang gia
    List<Product> searchProductsByPriceRange (double startPrice, double endPrice);
    //tim kiem san pham theo ten
    List<Product> searchProductsByName(String keyword, int minStock );
}
