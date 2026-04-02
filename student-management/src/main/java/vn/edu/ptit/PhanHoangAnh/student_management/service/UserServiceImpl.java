package vn.edu.ptit.PhanHoangAnh.student_management.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.RoleRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dao.UserRepository;
import vn.edu.ptit.PhanHoangAnh.student_management.dto.UserResponseDTO;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Role;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.User;
import vn.edu.ptit.PhanHoangAnh.student_management.mapper.UserMapper;

import java.util.ArrayList;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username.....");
        }

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), rolesToAuthorities(user.getRoleCollection()));
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
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
    public UserResponseDTO updateUserById(Long id, User user) {
        User userDb = this.userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Khong tim thay user co id:" + id));
        userDb.setUsername(user.getUsername());
        userDb.setPassword(bCryptPasswordEncoder.encode(userDb.getPassword()));
        userDb.setEnabled(true);
        userDb.setFirstname(user.getFirstname());
        userDb.setLastname(user.getLastname());
        userDb.setEmail(user.getEmail());
        userDb.setAvatar(user.getAvatar());
        return userMapper.toDTO(userDb);
    }

    //    @Override
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
