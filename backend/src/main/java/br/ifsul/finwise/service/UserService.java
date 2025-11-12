package br.ifsul.finwise.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import br.ifsul.finwise.model.UserModel;
import br.ifsul.finwise.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Encrypt / decrypt
    public String encryptUserPassword(String password) {
        return EncryptionService.encrypt(password);
    }

    public String decryptUserPassword(String encrypted) {
        return EncryptionService.decrypt(encrypted);
    }

    // Create
    public UserModel save(UserModel user) {
        try {
            String encryptedPassword = EncryptionService.encrypt(user.getPassword());
            user.setPassword(encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
        return userRepository.save(user);
    }

    // Login
    public Optional<UserModel> login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> {
                    try {
                        String decrypted = EncryptionService.decrypt(user.getPassword());
                        return decrypted.equals(rawPassword);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                });
    }

    // Read
    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserModel> findByNameContainingIgnoreCase(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public long count() {
        return userRepository.count();
    }

    public List<UserModel> findByNameIgnoreCase(String name) {
        return userRepository.findByNameIgnoreCase(name);
    }

    public List<UserModel> findByNameStartingWithIgnoreCase(String prefix) {
        return userRepository.findByNameStartingWithIgnoreCase(prefix);
    }

    public List<UserModel> findAllOrderByNameAsc() {
        return userRepository.findAllOrderByNameAsc();
    }

    public List<UserModel> findAllOrderByIdDesc() {
        return userRepository.findAllOrderByIdDesc();
    }

    public List<UserModel> findUsersWithPagination(int offset, int limit) {
        return userRepository.findUsersWithPagination(offset, limit);
    }

    public List<UserModel> findByIds(List<Long> ids) {
        return userRepository.findByIds(ids);
    }

    public List<UserModel> findByEmailContaining(String emailPart) {
        return userRepository.findByEmailContaining(emailPart);
    }

    public List<UserModel> findByNameOrEmailContaining(String searchText) {
        return userRepository.findByNameOrEmailContaining(searchText);
    }

    public byte[] findPhotoByUserId(Long id) {
        return userRepository.findPhotoByUserId(id);
    }

    public String findDescriptionByUserId(Long id) {
        return userRepository.findDescriptionByUserId(id);
    }

    // Update
    public int updateUserNameById(Long id, String newName) {
        return userRepository.updateUserNameById(id, newName);
    }

    public int updateUserEmailById(Long id, String newEmail) {
        return userRepository.updateUserEmailById(id, newEmail);
    }

    public int updateUserPasswordById(Long id, String newPassword) {
        try {
            String encryptedPassword = EncryptionService.encrypt(newPassword);
            return userRepository.updateUserPasswordById(id, encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }

    public int updateUserPhotoById(Long id, byte[] photo) {
        return userRepository.updateUserPhotoById(id, photo);
    }

    public int updateTeacherDescriptionById(Long id, String description) {
        return userRepository.updateTeacherDescriptionById(id, description);
    }

    // Delete
    public int deleteByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }

    public int deleteByName(String name) {
        return userRepository.deleteByName(name);
    }

    public int removeUserPhotoById(Long id) {
        return userRepository.removeUserPhotoById(id);
    }

    public int removeTeacherDescriptionById(Long id) {
        return userRepository.removeTeacherDescriptionById(id);
    }
}
