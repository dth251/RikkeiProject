package presentation;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import business.ICustomerService;
import business.IInvoiceService;
import business.IProductService;
import business.impl.CustomerServiceImpl;
import business.impl.InvoiceServiceImpl;
import business.impl.ProductServiceImpl;
import model.Customer;
import model.Invoice;
import model.InvoiceDetail;
import model.Product;


public class InvoiceView {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    private final IInvoiceService invoiceService = new InvoiceServiceImpl();
    private final ICustomerService customerService = new CustomerServiceImpl();
    private final IProductService productService = new ProductServiceImpl();



    public void showInvoiceMenu(Scanner sc) {
        int choice = 0;
        do {
            System.out.println("""
                    =============== QUẢN LÝ HÓA ĐƠN ====================
                    1. Hiển thị danh sách hóa đơn
                    2. Thêm mới hóa đơn
                    3. Tìm kiếm hóa đơn
                    4. Quay lại menu chính
                    ====================================================
                    """);
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException e) {
                System.out.println(RED + "Lỗi: Vui lòng nhập một số nguyên !" + RESET);
                continue;
            }

            switch (choice) {
                    case 1:
                        System.out.println("==================== HIỂN THỊ DANH SÁCH HÓA ĐƠN ================");
                        getAllInvoicesView();
                        break;
                    case 2:
                        System.out.println("==================== THÊM HÓA ĐƠN ===================");
                        addInvoiceView(sc);
                        break;
                    case 3:
                        showSearchMenu(sc);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ. Vui lòng thử lại!" + RESET);
                }

        } while (choice != 4);
    }

    // menu con tim kiem
    private void showSearchMenu(Scanner sc) {
        int choice = 0;
        do {
            System.out.println(" Menu tìm kiếm hóa đơn");
            System.out.println("1. Tìm theo tên khách hàng");
            System.out.println("2. Tìm theo ngày/tháng/năm");
            System.out.println("3. Quay lại menu hóa đơn");
            System.out.print("Nhập lựa chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("=========== TÌM KIẾM HÓA ĐƠN THEO TÊN KHÁCH HÀNG ==============");
                        searchByCustomerNameView(sc);
                        break;
                    case 2:
                        System.out.println("========== TÌM KIẾM HÓA ĐƠN THEO NGÀY/ THÁNG /NĂM ==============");
                        searchByDateView(sc);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println(RED + "Lựa chọn không hợp lệ!" + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Lỗi: Vui lòng nhập một số nguyên!" + RESET);
                choice = 0;
            }
        } while (choice != 3);
    }





    // them hoa don
    private void addInvoiceView(Scanner sc) {
        try {
            System.out.print("Nhập ID khách hàng mua hàng: ");
            int customerId = Integer.parseInt(sc.nextLine());

            Customer customer = customerService.getCustomerById(customerId);
            if (customer == null) {
                System.out.println(RED + "Không tìm thấy khách hàng nào có ID là " + customerId + RESET);
                return;
            }
            System.out.println(GREEN + "Khách hàng: " + customer.getName() + RESET);

            // tao hoa don tron g
            Invoice invoice = new Invoice();
            invoice.setCustomerId(customerId);
            double totalAmount = 0;

            // vong lap de chon san pham
            while (true) {
                System.out.println("\n--- Thêm sản phẩm vào giỏ ---");
                System.out.print("Nhập ID điện thoại (Nhấn 0 để dừng chọn): ");
                int productId = Integer.parseInt(sc.nextLine());

                if (productId == 0) break; // thoat khoi vong lap

                Product product = productService.getProductByid(productId);
                if (product == null) {
                    System.out.println(RED + "Sản phẩm không tồn tại!" + RESET);
                    continue; // quay lai vong lap de them san pham khac
                }

                System.out.println("Đang chọn: " + product.getName() + " - Tồn kho: " + product.getStock() + " - Giá: " + product.getPrice());

                System.out.print("Nhập số lượng muốn mua: ");
                int quantity = Integer.parseInt(sc.nextLine());

                if (quantity <= 0) {
                    System.out.println(RED + "Số lượng mua phải lớn hơn 0!" + RESET);
                    continue;
                }
                if (quantity > product.getStock()) {
                    System.out.println(RED + "Số lượng trong kho không đủ để bán!" + RESET);
                    continue;
                }

                // tao hoa don
                InvoiceDetail detail = new InvoiceDetail();
                detail.setProductId(productId);
                detail.setQuantity(quantity);
                detail.setUnitPrice(product.getPrice());

                // tinh tong tien
                totalAmount += (product.getPrice() * quantity);

                // them vao danh sach chi tiet
                invoice.getDetails().add(detail);

                System.out.println(GREEN + "Đã thêm " + quantity + " " + product.getName() + " vào hóa đơn." + RESET);
            }


            if (invoice.getDetails().isEmpty()) {
                System.out.println(RED + "Hóa đơn trống. Đã hủy quá trình tạo hóa đơn!" + RESET);
                return;
            }

            invoice.setTotalAmount(totalAmount);
            System.out.println("===============================");
            System.out.println("Tổng tiền thanh toán: " + totalAmount + " VND");

            boolean isSuccess = invoiceService.addInvoice(invoice);

            if (isSuccess) {
                System.out.println(GREEN + "Tạo hóa đơn thành công! " + RESET);
            } else {
                System.out.println(RED + "Tạo hóa đơn thất bại!" + RESET);
            }

        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: ID sản phẩm  và Số lượng phải là số nguyên!" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Lỗi hệ thống: " + e.getMessage() + RESET);
        }
    }


    //hien thi hoa don
    private void getAllInvoicesView() {
        List<Invoice> list = invoiceService.getAllInvoices();

        if (list.isEmpty()) {
            System.out.println(RED + "Hiện tại chưa có hóa đơn nào trong hệ thống!" + RESET);
            return;
        }

        System.out.println("==========================================================================================");
        System.out.printf("%-5s | %-25s | %-20s | %-15s\n", "ID", "Tên khách hàng", "Ngày tạo", "Tổng tiền");
        System.out.println("==========================================================================================");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Invoice inv : list) {
            String dateStr = (inv.getCreatedAt() != null) ? inv.getCreatedAt().format(formatter) : "N/A";

            System.out.printf("%-5d | %-25s | %-20s | %-15.2f\n",
                    inv.getId(),
                    inv.getCustomerName(),
                    dateStr,
                    inv.getTotalAmount());
        }
        System.out.println("==========================================================================================");
    }

    //hien thi tim kiem hoa don
    private void printInvoiceList(List<Invoice> list) {
        if (list == null || list.isEmpty()) {
            System.out.println(RED + "Không tìm thấy hóa đơn nào phù hợp!" + RESET);
            return;
        }

        System.out.println("==========================================================================================");
        System.out.printf("%-5s | %-25s | %-20s | %-15s\n", "ID", "Tên khách hàng", "Ngày tạo", "Tổng tiền");
        System.out.println("==========================================================================================");

        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Invoice inv : list) {
            String dateStr = (inv.getCreatedAt() != null) ? inv.getCreatedAt().format(formatter) : "N/A";
            System.out.printf("%-5d | %-25s | %-20s | %-15.2f\n",
                    inv.getId(), inv.getCustomerName(), dateStr, inv.getTotalAmount());
        }
        System.out.println("==========================================================================================");
    }


    // tim kiem theo ten khach hang
    private void searchByCustomerNameView(Scanner sc) {
        System.out.print("Nhập tên khách hàng cần tìm: ");
        String name = sc.nextLine();

        System.out.println(GREEN + "Kết quả tìm kiếm hóa đơn của khách hàng: " + name + RESET);
        List<Invoice> list = invoiceService.searchByCustomerName(name);
        printInvoiceList(list);
    }


    // tim kiem theo ngay
    private void searchByDateView(Scanner sc) {
        System.out.print("Nhập ngày tạo hóa đơn (Định dạng: dd/MM/yyyy, ví dụ: 25/12/2025): ");
        String dateStr = sc.nextLine().trim();

        try {
            java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.LocalDate searchDate = java.time.LocalDate.parse(dateStr, dateFormatter);

            System.out.println(GREEN + "Kết quả tìm kiếm hóa đơn trong ngày: " + dateStr + RESET);
            List<Invoice> list = invoiceService.searchByDate(searchDate);
            printInvoiceList(list);

        } catch (java.time.format.DateTimeParseException e) {
            System.out.println(RED + "Lỗi nhập liệu: Định dạng ngày không hợp lệ. Vui lòng nhập đúng dd/MM/yyyy!" + RESET);
        }
    }
}