package presentation;

import business.ICustomerService;
import business.impl.CustomerServiceImpl;
import model.Customer;

import java.util.List;
import java.util.Scanner;

public class CustomerView {
    private static final ICustomerService customerService = new CustomerServiceImpl();
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    public static void showMenuCustomer(Scanner sc){
        int choice = 0;
        do {
            System.out.println("""
                    ===================== QUẢN LÝ KHÁCH HÀNG ====================
                    1. Hiển thị danh sách khách hàng 
                    2. Thêm khách hàng mới
                    3. Cập nhật thông tin khách hàng 
                    4. Xóa khách hàng theo ID
                    5. Quay lại menu chính 
                    =============================================================
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
                    System.out.println("==================== DANH SÁCH KHÁCH HÀNG ====================");
                    getAllCustomersView();
                    break;
                case 2:
                    System.out.println("==================== THÊM KHÁCH HÀNG MỚI ====================");
                    addCustomerView(sc);
                    break;
                case 3:
                    System.out.println("==================== CẬP NHẬT THÔNG TIN KHÁCH HÀNG ====================");
                    updateCustomerView(sc);
                    break;
                case 4:
                    System.out.println("==================== XÓA KHÁCH HÀNG  ====================");
                    deleteCustomerView(sc);
                    break;
                case 5:
                    return;
                default:
                    System.out.println(RED + "Lựa chọn không hợp lệ, vui lòng thử lại !" + RESET);
            }
        }while(choice != 5);
    }

    // them khach hang moi
    private static void addCustomerView(Scanner sc) {
        try {
            System.out.print("Nhập tên khách hàng: ");
            String name = sc.nextLine().trim();

            System.out.print("Nhập số điện thoại: ");
            String phone = sc.nextLine().trim();
            if (phone.isEmpty()) phone = null;

            System.out.print("Nhập email: ");
            String email = sc.nextLine().trim();
            if (email.isEmpty()) email = null;

            System.out.print("Nhập địa chỉ: ");
            String address = sc.nextLine().trim();
            if (address.isEmpty()) address = null;

            Customer newCustomer = new Customer(0, name, phone, email, address);


            boolean isSuccess = customerService.addCustomer(newCustomer);

            if (isSuccess) {
                System.out.println(GREEN + "Đã thêm khách hàng thành công!" + RESET);
            } else {
                System.out.println(RED + "Thêm thất bại. Vui lòng kiểm tra lại thông tin." + RESET);
            }

        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: Họ tên khách hàng không được để trống! " + RESET);
        }
     }


    // hien thi danh sach khach hang
    private static void getAllCustomersView() {
        List<Customer> list = customerService.getAllCustomers();

        if (list.isEmpty()) {
            System.out.println(RED + "Hiện tại chưa có khách hàng nào trong hệ thống!" + RESET);
            return;
        }

        System.out.println("===============================================================================================================");
        System.out.printf("%-5s | %-25s | %-15s | %-30s | %-20s\n", "ID", "Tên khách hàng", "Số điện thoại", "Email", "Địa chỉ");
        System.out.println("===============================================================================================================");

        for (Customer c : list) {
            String displayPhone = (c.getPhone() != null && !c.getPhone().isEmpty()) ? c.getPhone() : "Trống";
            String displayEmail = (c.getEmail() != null && !c.getEmail().isEmpty()) ? c.getEmail() : "Trống";
            String displayAddress = (c.getAddress() != null && !c.getAddress().isEmpty()) ? c.getAddress() : "Trống";

            System.out.printf("%-5d | %-25s | %-15s | %-30s | %-20s\n",
                    c.getId(),
                    c.getName(),
                    displayPhone,
                    displayEmail,
                    displayAddress);
        }
        System.out.println("===============================================================================================================");
    }

    // cap nhat thong tin khach hang
    public static  void updateCustomerView(Scanner sc) {
        try {
            System.out.print("Nhập ID khách hàng cần cập nhật: ");
            int id = Integer.parseInt(sc.nextLine());


            Customer currentCustomer = customerService.getCustomerById(id);
            if (currentCustomer == null) {
                System.out.println(RED + "Không tìm thấy khách hàng nào có ID là " + id + RESET);
                return;
            }


            System.out.println("======== Nhập thông tin mới (Ấn Enter để giữ nguyên thông tin cũ) ========");

            System.out.println("Tên khách hàng hiện tại: " + currentCustomer.getName());
            System.out.print("Tên khách hàng mới là: ");
            String newName = sc.nextLine().trim();
            if (!newName.isEmpty()) {
                currentCustomer.setName(newName);
            }

            System.out.println("Số điện thoại hiện tại: " + ((currentCustomer.getPhone() != null) ? currentCustomer.getPhone() : "Trống"));
            String oldPhone = (currentCustomer.getPhone() != null) ? currentCustomer.getPhone() : "Trống";
            System.out.print("Số điện thoại mới là: ");
            String newPhone = sc.nextLine().trim();
            if (!newPhone.isEmpty()) {
                currentCustomer.setPhone(newPhone);
            }

            System.out.println("Email hiện tại: " + ((currentCustomer.getEmail() != null) ? currentCustomer.getEmail() : "Trống"));
            String oldEmail = (currentCustomer.getEmail() != null) ? currentCustomer.getEmail() : "Trống";
            System.out.print("Email mới: ");
            String newEmail = sc.nextLine().trim();
            if (!newEmail.isEmpty()) {
                currentCustomer.setEmail(newEmail);
            }

            System.out.println("Địa chỉ hiện tại: " + ((currentCustomer.getAddress() != null) ? currentCustomer.getAddress() : "Trống"));
            String oldAddress = (currentCustomer.getAddress() != null) ? currentCustomer.getAddress() : "Trống";
            System.out.print("Địa chỉ mới: ");
            String newAddress = sc.nextLine().trim();
            if (!newAddress.isEmpty()) {
                currentCustomer.setAddress(newAddress);
            }


            boolean isSuccess = customerService.updateCustomer(currentCustomer);
            if (isSuccess) {
                System.out.println(GREEN + "Cập nhật thông tin khách hàng thành công!" + RESET);
            } else {
                System.out.println(RED + "Cập nhật thất bại. Vui lòng kiểm tra lại!" + RESET);
            }

        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: ID phải là một số nguyên!" + RESET);
        }
    }

    // xoa khach hang
    private static void deleteCustomerView(Scanner sc) {
        try {
            System.out.print("Nhập ID khách hàng cần xóa: ");
            int id = Integer.parseInt(sc.nextLine());

            Customer currentCustomer = customerService.getCustomerById(id);
            if (currentCustomer == null) {
                System.out.println(RED + "Không tìm thấy khách hàng nào có ID là " + id + RESET);
                return;
            }


            String displayEmail = (currentCustomer.getEmail() != null) ? currentCustomer.getEmail() : "Không có Email";
            System.out.println("Tìm thấy khách hàng: [" + currentCustomer.getName() + "] - Email: " + displayEmail);
            System.out.print(RED + "Bạn có chắc chắn muốn xóa khách hàng này không? (Y/N): " + RESET);

            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                boolean isSuccess = customerService.deleteCustomer(id);
                if (isSuccess) {
                    System.out.println(GREEN + "Đã xóa khách hàng thành công!" + RESET);
                } else {
                    System.out.println(RED + "Xóa thất bại. Có lỗi xảy ra ở hệ thống!" + RESET);
                }
            } else {
                System.out.println(GREEN + "Đã hủy thao tác xóa!" +RESET);
            }

        } catch (NumberFormatException e) {
            System.out.println(RED + "Lỗi nhập liệu: ID phải là một số nguyên!" + RESET);
        }
    }

}

