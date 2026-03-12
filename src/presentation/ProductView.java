package presentation;

import business.IProductService;
import business.impl.ProductServiceImpl;
import model.Product;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ProductView {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    public static final IProductService productService = new ProductServiceImpl();
    public static void showMenuProduct(Scanner sc){
        int choice = 0;
        do {
            System.out.println("""
                    ===================== QUẢN LÝ SẢN PHẨM ====================
                    1. Hiển thị danh sách sản phẩm
                    2. Thêm sản phẩm mới
                    3. Cập nhật thông tin sản phẩm
                    4. Xóa sản phẩm theo ID
                    5. Tìm kiếm theo brand 
                    6. Tìm kiếm theo khoảng giá
                    7. Tìm kiếm theo tồn kho
                    8. Quay lại menu chính 
                    ===========================================================
                    """);
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            }catch (NumberFormatException e) {
                System.out.println(RED + "Lỗi: Vui lòng nhập một số nguyên !" + RESET);
                continue;
            }
            switch (choice){
                case 1:
                    System.out.println("========================================== DANH SÁCH SẢN PHẨM  ===========================================");
                    displayAllProducts();
                    break;
                case 2:
                    System.out.println("==================== THÊM SẢN PHẨM MỚI ===================");
                    addProductView(sc);
                    break;
                case 3:
                    System.out.println("==================== CẬP NHẬT THÔNG TIN SẢN PHẨM ===================");
                    updateProductView(sc);
                    break;
                case 4:
                    System.out.println("==================== XÓA SẢN PHẨM ===================");
                    deleteProductView(sc);
                    break;
                case 5:
                    System.out.println("==================== TÌM KIẾM SẢN PHẨM THEO HÃNG ===================");
                    searchProductsByBrandView(sc);
                    break;
                case 6:
                    System.out.println("==================== TÌM KIẾM SẢN PHẨM THEO KHOẢNG GIÁ ===================");
                    searchProductsByPriceRangeView(sc);
                    break;
                case 7:
                    System.out.println("==================== TÌM KIẾM SẢN PHẨM THEO TÊN ===================");
                     searchProductsByNameView(sc);
                    break;
                case 8:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, vui lòng thử lại !" + RESET);
            }
        }while(choice != 8);
    }
    // Hiển thị danh sách sản phẩm
    private static void displayAllProducts(){
        List<Product> list = productService.getAllProducts();

        if (list.isEmpty()){
            System.out.println(RED + "Hiện tại chưa có sản phẩm nào trong hệ thống!" + RESET);
        }
        System.out.println("==========================================================================================================");
        System.out.printf("%-5s | %-25s | %-15s | %-12s | %-10s\n","ID", "Tên sản phẩm", "Hãng", "Giá tiền", "Tồn kho");
        System.out.println("==========================================================================================================");

        for (Product p : list){
            System.out.printf("%-5d | %-25s | %-15s | %-12.2f | %-10d\n",
                    p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
        }
        System.out.println("==========================================================================================================");
    }


    // Thêm sản phẩm mới
    private static void addProductView(Scanner sc){
        try {
            System.out.print("Nhập tên sản phẩm: ");
            String name = sc.nextLine();

            System.out.print("Nhập tên hãng: ");
            String brand = sc.nextLine();

            System.out.print("Nhập giá sản phẩm: ");
            double price = Double.parseDouble(sc.nextLine());

            System.out.print("Nhập số lượng tồn kho: ");
            int stock = Integer.parseInt(sc.nextLine());

            Product newProduct = new Product(0, name, brand, price, stock);
            boolean isSuccess = productService.addProduct(newProduct);

            if (isSuccess) {
                System.out.println(GREEN + "Thêm sản phẩm thành công! " + RESET);
            } else {
                System.out.println(RED + "Thêm thất bại. Vui lòng kiểm tra lại thông tin sản phẩm! " + RESET);
            }
        }catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: Vui lòng không để trống (Giá tiền và tồn kho phải là số và lớn hơn 0) !" + RESET);
        }
    }

    // Cập nhật thông tin sản phẩm

    private static void updateProductView(Scanner sc){
        try{
            System.out.print("Nhập ID sản phẩm cần cập nhật: ");
            int id = Integer.parseInt(sc.nextLine());

            // kiểm tra xem sản phẩm có tồn tại không
            Product currentProduct = productService.getProductByid(id);
            if (currentProduct == null){
                System.out.println(RED + "Không tìm thấy sản phẩm nào có ID là : " + id + RESET);
                return;
            }

            // Cap Nhập thông tin mới
            System.out.println("===================== CẬP NHẬT THÔNG TIN SẢN PHẨM  ===================");
            System.out.println("(Ấn Enter để giữ nguyên thông tin cũ )");


            System.out.println("Tên sản phẩm hiện tại: " + currentProduct.getName());
            System.out.print("Nhập tên sản phẩm mới: ");
            String newName = sc.nextLine();
            if(!newName.trim().isEmpty()){
                currentProduct.setName(newName);
            }

            System.out.println("Hãng hiện tại: " + currentProduct.getBrand());
            System.out.print("Nhập tên hãng mới: ");
            String newBrand = sc.nextLine();
            if (!newBrand.trim().isEmpty()){
                currentProduct.setBrand(newBrand);
            }

            System.out.println("Giá sản phẩm hiện tại: " + currentProduct.getPrice());
            System.out.print("Nhập giá sản phẩm mới: ");
            String newPrice = sc.nextLine();
            if (!newPrice.trim().isEmpty()){
                currentProduct.setPrice(Double.parseDouble(newPrice));
            }

            System.out.println("Tồn kho hiện tại: " + currentProduct.getStock());
            System.out.print("Nhập số lượng tồn kho mới: ");
            String newStock = sc.nextLine();
            if (!newStock.trim().isEmpty()){
                currentProduct.setStock(Integer.parseInt(newStock));
            }

            boolean isSuccess = productService.updateProduct(currentProduct);

            if (isSuccess) {
                System.out.println(GREEN + "Cập nhật sản phẩm thành công! " + RESET);
            }else {
                System.out.println(RED + "Cập nhật thất bại. Vui lòng kiểm tra lại thông tin sản phẩm! " + RESET);
            }

        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: ID, Giá tiền hoặc Tồn kho phải là số!" + RESET);
        }
        }

       // Xóa sản phẩm theo ID
         private static void deleteProductView(Scanner sc){
        try{
            System.out.print("Nhập ID sản phẩm cần xóa: ");
            int id = Integer.parseInt(sc.nextLine());


            Product currentProduct = productService.getProductByid(id);
            if(currentProduct == null){
                System.out.println(RED + "Không tìm thấy sản phẩm nào có ID là : " + id + RESET);
                return;
            }

            System.out.println(GREEN + "Đã tìm thấy sản phẩm: " + currentProduct.getName() + " (Hãng: " + currentProduct.getBrand() + ", Giá: " + currentProduct.getPrice() + ", Tồn kho: " + currentProduct.getStock() + ")" + RESET);
            System.out.println(RED + "Bạn có chắc chắn muốn xóa sản phẩm này? (Y/N) " + RESET);
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                boolean isSuccess = productService.deleteProduct(id);
                if (isSuccess) {
                    System.out.println(GREEN + "Xóa sản phẩm thành công! " + RESET);
                } else {
                    System.out.println(RED + "Xóa sản phẩm thất bại! " + RESET);
                }
                 }else{
                    System.out.println(RED + "Hủy xóa sản phẩm! " + RESET);
                }

            } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: ID phải là một số nguyên!" + RESET);;
        }
    }


    // Tìm kiếm sản phầm theo Brand
    private static void searchProductsByBrandView(Scanner sc) {
        try {
            System.out.print("Nhập từ khóa tìm kiếm theo hãng: ");
            String keyword = sc.nextLine();

            if (keyword.trim().isEmpty()) {
                throw new IllegalArgumentException(RED + "Từ khóa tìm kiếm không được để trống!" + RESET);
            }

            // Nếu input hợp lệ, thực hiện tìm kiếm và in kết quả
            List<Product> list = productService.searchProductsByBrand(keyword);

            if (list.isEmpty()){
                System.out.println(RED + "Không tìm thấy sản phẩm nào phù hợp với từ khóa: " + keyword + RESET);
            } else {
                System.out.println(GREEN + "Đã tìm thấy " + list.size() + " sản phẩm phù hợp với từ khóa: " + keyword + RESET);

                System.out.println("==========================================================================================================");
                System.out.printf("%-5s | %-25s | %-15s | %-12s | %-10s\n", "ID", "Tên sản phẩm", "Hãng", "Giá tiền", "Tồn kho");
                System.out.println("==========================================================================================================");
                for (Product p : list) {
                    System.out.printf("%-5d | %-25s | %-15s | %-12.2f | %-10d\n",
                            p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
                }
                System.out.println("==========================================================================================================");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Lỗi: " + e.getMessage() + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Đã xảy ra lỗi hệ thống: " + e.getMessage() + RESET);
        }
    }

    // Tìm kiếm sản phẩm theo khoảng giá
    private static void searchProductsByPriceRangeView(Scanner sc){
        try{
            System.out.print("Nhập mức giá thấp nhất cần tìm kiếm: ");
            double startPrice = Double.parseDouble(sc.nextLine());
            System.out.print("Nhập mức giá cao nhất cần tìm kiếm: ");
            double endPrice = Double.parseDouble(sc.nextLine());

            List<Product> list = productService.searchProductsByPriceRange(startPrice, endPrice);

            if (list.isEmpty()) {
                System.out.println(RED + "Không tìm thấy sản phẩm nào trong khoảng giá từ " + startPrice + " đến " + endPrice + RESET);
            } else {
                System.out.println(GREEN + "Đã tìm thấy " + list.size() + " sản phẩm trong khoảng giá từ " + startPrice + " đến " + endPrice + RESET);

                System.out.println("==========================================================================================================");
                System.out.printf("%-5s | %-25s | %-15s | %-12s | %-10s\n", "ID", "Tên sản phẩm", "Hãng", "Giá tiền", "Tồn kho");
                System.out.println("==========================================================================================================");
                for (Product p : list) {
                    System.out.printf("%-5d | %-25s | %-15s | %-12.2f | %-10d\n",
                            p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
                }
                System.out.println("==========================================================================================================");
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: Vui lòng chỉ nhập số!" + RESET);
        }
    }

    // Tìm kiếm sản phẩm theo tên và tồn kho
    private static void searchProductsByNameView(Scanner sc){
        try {
            System.out.print("Nhập tên sản phẩm cần tìm kiếm: ");
            String keyword = sc.nextLine();

            System.out.print("Nhập số lượng tồn kho tối thiểu: ");
            int minStock = Integer.parseInt(sc.nextLine());

            List<Product> list = productService.searchProductsByName(keyword, minStock);

            if (list.isEmpty()) {
                System.out.println(RED + "Không tìm thấy sản phẩm nào phù hợp với tên: " + keyword + " và tồn kho >= " + minStock + RESET);
            } else {
                System.out.println(GREEN + "Đã tìm thấy " + list.size() + " sản phẩm phù hợp với tên: " + keyword + " và tồn kho >= " + minStock + RESET);

                System.out.println("==========================================================================================================");
                System.out.printf("%-5s | %-25s | %-15s | %-12s | %-10s\n", "ID", "Tên sản phẩm", "Hãng", "Giá tiền", "Tồn kho");
                System.out.println("==========================================================================================================");
                for (Product p : list) {
                    System.out.printf("%-5d | %-25s | %-15s | %-12.2f | %-10d\n",
                            p.getId(), p.getName(), p.getBrand(), p.getPrice(), p.getStock());
                }
                System.out.println("==========================================================================================================");
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: Tồn kho phải là một số nguyên!" + RESET);
        }
    }
}



