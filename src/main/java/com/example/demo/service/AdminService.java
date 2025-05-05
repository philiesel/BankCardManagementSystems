package com.example.demo.service;

import com.example.demo.dto.RequestCreatUserDto;
import com.example.demo.dto.UpdateUserDto;
import com.example.demo.entity.ERole;
import com.example.demo.entity.User;
import com.example.demo.exceptions.UserWithEmailAlreadyExists;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final UserRepository userRepository;


    /**
     * Получение всех пользователей
     *
     * @return список всех пользователей
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Получение пользователея по email
     *
     * @return пользователь с данным email
     */
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден с данным email: " + email));
    }

    /**
     * Удалить пользователея по id
     *
     * @return пользователь с данным id
     */
    public User deleteUser(Long id) throws UsernameNotFoundException {
        Optional<User> existUser = userRepository.findById(id);
        if (!existUser.isPresent()) {
            throw new UsernameNotFoundException(String.format("Пользователь не найден с данным %s", id));
        }
        userRepository.deleteById(id);
        return existUser.get();
    }

    /**
     * Создать пользователея
     *
     * @return созданный пользователь
     */
    public User createUser(RequestCreatUserDto payload) {
        if (userRepository.existsByEmail(payload.getEmail())) {
            throw new UserWithEmailAlreadyExists(
                    "Данный пользователь с " + payload.getEmail() + " уже существует"
            );
        }
        var createUser = new User().builder()
                .email(payload.getEmail().toLowerCase())
                .password(payload.getPassword()) // TODO: хэшировать пароль
                .role(ERole.valueOf(payload.getRole()))
                .username(payload.getUsername())
                .build();
        var saveUser = userRepository.save(createUser);
        return saveUser;
    }

    /**
     * Обновление пользователея по id
     *
     * @return пользователь с данным id
     */
    public User updateUser(Long id, UpdateUserDto playload) {
        var user = getUserById(id);
        if (playload.getUsername() != null) {
            user.setUsername(playload.getUsername());
        }
        if (playload.getEmail() != null) {
            user.setEmail(playload.getEmail());
        }
        if (playload.getPassword() != null) {
            user.setPassword(playload.getPassword());
        }
        return userRepository.save(user);
    }

    public User getUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь не найден с данным %s", id)));
    }
}
