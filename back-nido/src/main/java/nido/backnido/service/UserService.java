package nido.backnido.service;

import nido.backnido.entity.User;
import nido.backnido.entity.dto.AuthTokenDTO;
import nido.backnido.entity.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAll();
    UserDTO getById(Long id);
    void create(User newUser);
    void update(User updatedUser);
    void delete(Long id);
    User findByEmail(String email);
}
