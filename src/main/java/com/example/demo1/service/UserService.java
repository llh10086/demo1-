package com.example.demo1.service;

import com.example.demo1.entity.User;
import com.example.demo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService, CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createTestUser("张三", "123456", "USER", "zhangsan@example.com");
        createTestUser("李四", "123456", "USER", "lisi@example.com");
        createTestUser("王五", "123456", "ADMIN", "wangwu@example.com");
        System.out.println("✅ 测试用户初始化完成！");
    }
    
    private void createTestUser(String username, String password, String role, String email) {
        try {
            if (!userRepository.existsByUsername(username)) {
                // 检查邮箱是否已存在
                if (userRepository.existsByEmail(email)) {
                    System.out.println("⚠️ 邮箱 " + email + " 已存在，跳过创建用户: " + username);
                    return;
                }
                
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                user.setRole(role);
                user.setEmail(email);
                userRepository.save(user);
                System.out.println("✅ 创建用户: " + username);
            } else {
                System.out.println("ℹ️ 用户已存在: " + username);
            }
        } catch (Exception e) {
            System.out.println("❌ 创建用户失败: " + username + ", 错误: " + e.getMessage());
        }
    }
}
