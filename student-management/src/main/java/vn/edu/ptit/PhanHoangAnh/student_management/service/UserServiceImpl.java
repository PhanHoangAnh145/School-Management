package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.RoleRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.UserRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.UserProfileUpdateDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.UserResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Role;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.UserMapper;

import javax.sql.rowset.serial.SerialBlob;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder ,UserMapper userMapper) {
        this.userRepository =  userRepository;
        this.roleRepository =  roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }


    @Override
    public Role getOrCreateRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            return roleRepository.save(role);
        }
        return role;
    }

    @Override
    @Transactional
    public void register(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(getOrCreateRole("ROLE_STUDENT"));
        user.setEnabled(true);
        user.setRoleCollection(roles);
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void createUserByAdmin(User user, String role) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(getOrCreateRole(role));
        user.setRoleCollection(roles);
        this.userRepository.save(user);
    }

    @Override
    public UserResponseDTO findUserById(Long id) {
        return userMapper.toDTO(this.userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Khong tim thay user co id:" + id)));
    }

    @Override
    public List<UserResponseDTO> findAllUser() {
        return this.userRepository.findAll()
                .stream()
                .map(user -> userMapper.toDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponseDTO updateUserById(Long id, User user) {
        User userDb = this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Khong tim thay user co id:" + id));
        if (user.getUsername() != null) {
            userDb.setUsername(user.getUsername());
        }
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            userDb.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        if (user.getEnabled() != null) {
            userDb.setEnabled(user.getEnabled());
        }
        if (user.getFirstname() != null) {
            userDb.setFirstname(user.getFirstname());
        }
        if (user.getLastname() != null) {
            userDb.setLastname(user.getLastname());
        }
        if (user.getEmail() != null) {
            userDb.setEmail(user.getEmail());
        }
        if (user.getAvatar() != null) {
            userDb.setAvatar(user.getAvatar());
        }
        this.userRepository.save(userDb);
        return userMapper.toDTO(userDb);
    }

    @Override
    @Transactional
    public UserResponseDTO updateMyProfile(Long id, UserProfileUpdateDTO dto) {
        User userDb = this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Khong tim thay user co id:" + id));
        if (dto.getFirstname() != null) {
            userDb.setFirstname(dto.getFirstname());
        }
        if (dto.getLastname() != null) {
            userDb.setLastname(dto.getLastname());
        }
        if (dto.getEmail() != null) {
            userDb.setEmail(dto.getEmail());
        }
        if (dto.getAvatarBase64() != null) {
            if (dto.getAvatarBase64().isBlank()) {
                // Empty string means remove avatar
                userDb.setAvatar(null);
            } else {
                try {
                    byte[] bytes = decodeAvatarBase64(dto.getAvatarBase64());
                    if (bytes.length > 0) {
                        userDb.setAvatar(new SerialBlob(bytes));
                    } else {
                        userDb.setAvatar(null);
                    }
                } catch (Exception e) {
                    // Log error but don't fail the whole update
                    System.err.println("Could not store avatar: " + e.getMessage());
                    // Keep existing avatar on error
                }
            }
        }
        this.userRepository.save(userDb);
        return userMapper.toDTO(userDb);
    }

    private static byte[] decodeAvatarBase64(String input) {
        String base64 = input.trim();
        int comma = base64.indexOf(',');
        if (base64.startsWith("data:") && comma > 0) {
            base64 = base64.substring(comma + 1);
        }
        return Base64.getDecoder().decode(base64);
    }

    @Override
    @Transactional
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User userDb = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay user co id:" + id));

        if (oldPassword == null || oldPassword.isBlank()) {
            throw new IllegalArgumentException("Mật khẩu cũ không được để trống!");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Mật khẩu mới không được để trống!");
        }
        if (!this.bCryptPasswordEncoder.matches(oldPassword, userDb.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu cũ không chính xác!");
        }

        userDb.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
        this.userRepository.save(userDb);
    }

    //        @Override
//    @Transactional
//    public void assignRoleToUser(Long userId, Long roleId) {
//        // 1. Tìm User và Role theo ID
//        User user = userRepository.findById(userId).orElse(null);
//        Role role = roleRepository.findById(roleId).orElse(null);
//
//        if (user != null && role != null) {
//            // 2. Lấy danh sách quyền hiện tại và thêm quyền mới
//            Collection<Role> roles = user.getRoleCollection();
//            if (roles == null) {
//                roles = new ArrayList<>();
//            }
//
//            // Tránh add trùng lặp Role
//            if (!roles.contains(role)) {
//                roles.add(role);
//                user.setRoleCollection(roles);
//                userRepository.save(user);
//                System.out.println(">> Đã gán thành công Role " + role.getName() + " cho User " + user.getUsername());
//            }
//        } else {
//            System.out.println(">> Lỗi: Không tìm thấy User ID=" + userId + " hoặc Role ID=" + roleId + " trong Database!");
//        }
//    }
}
