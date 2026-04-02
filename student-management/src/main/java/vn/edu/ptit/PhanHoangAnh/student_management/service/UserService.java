package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.UserResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Role;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public User findUserByUsername(String username);
    public void saveUser(User user);
    public Role getOrCreateRole(String roleName);
    public void register(User user);
    public void createUserByAdmin(User user, String role);
    public UserResponseDTO findUserById(Long id);
    public List<UserResponseDTO> findAllUser();
    public UserResponseDTO updateUserById(Long id, User user);
//    public void assignRoleToUser(Long userId, Long roleId);
}
