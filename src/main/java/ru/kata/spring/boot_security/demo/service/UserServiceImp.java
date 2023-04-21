package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.P6Spy;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.P6SpyRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.io.IOUtils.toByteArray;
@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;
    private P6SpyRepository p6SpyRepository;


    public UserServiceImp(P6SpyRepository p6SpyRepository) {
        this.p6SpyRepository = p6SpyRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.saveAndFlush(user);

        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("random-file.txt")) {
            byte[] reportContent = toByteArray(requireNonNull(resourceAsStream));
            P6Spy p6Spy = new P6Spy (reportContent);
            p6SpyRepository.save(p6Spy);
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        User userFromDB = getUserById(user.getId());
        if (userFromDB.getPassword().equals(user.getPassword())){
            userRepository.save(user);
        } else {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        }
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            return userById.get();
        } else {
            throw new UsernameNotFoundException(String.format("User with %s not found", id));
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }


}
