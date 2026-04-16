package vn.edu.ptit.PhanHoangAnh.student_management.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.edu.ptit.PhanHoangAnh.student_management.entity.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        vn.edu.ptit.PhanHoangAnh.student_management.entity.User myUser = this.userService.findUserByUsername(username);

        if (myUser == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new org.springframework.security.core.userdetails.User(myUser.getUsername(), myUser.getPassword(),
                rolesToAuthorities(myUser.getRoleCollection()));

    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
