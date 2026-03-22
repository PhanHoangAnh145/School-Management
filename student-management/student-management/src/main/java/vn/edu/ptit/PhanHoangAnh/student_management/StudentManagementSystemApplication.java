package vn.edu.ptit.PhanHoangAnh.student_management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.ptit.PhanHoangAnh.student_management.service.UserService;

@SpringBootApplication
public class StudentManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemApplication.class, args);
	}
//    @Bean
//    public CommandLineRunner run(UserService userService) {
//        return args -> {
//            System.out.println("--- Bắt đầu tiến trình gán quyền tự động ---");
//
//            // Gọi hàm gán User ID = 5 với Role ID = 2
//            userService.assignRoleToUser(5, 2);
//
//            System.out.println("--- Kết thúc tiến trình ---");
//        };
//    }
}
