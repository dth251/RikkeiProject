package business;

import model.Product;

import java.util.List;

public interface IProductService {
    // them san pham moi
    boolean addProduct(Product product);
    // hien thi san pham
    List<Product> getAllProducts();
    // lay san pham theo id
    Product getProductByid(int id);
    // cap nhat san pham
    boolean updateProduct(Product product);
    // xoa san pham
    boolean deleteProduct(int id);
    // tim san pham theo brand
    List<Product> searchProductsByBrand(String keyword);
    // tim san pham theo khoang gia
    List<Product> searchProductsByPriceRange(double startPrice, double endPrice);
    // tim san pham theo ten
    List<Product> searchProductsByName(String keyword, int minStock);
}
