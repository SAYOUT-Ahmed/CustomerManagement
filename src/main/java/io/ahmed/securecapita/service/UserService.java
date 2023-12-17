package io.ahmed.securecapita.service;

import io.ahmed.securecapita.dto.UserDTO;
import io.ahmed.securecapita.model.User;

public interface UserService {
    UserDTO createUser(User user);

}
