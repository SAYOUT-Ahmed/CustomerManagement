package io.ahmed.securecapita.service.implementation;

import io.ahmed.securecapita.dto.UserDTO;
import io.ahmed.securecapita.dtomapper.UserDTOMapper;
import io.ahmed.securecapita.model.User;
import io.ahmed.securecapita.repository.UserRepository;
import io.ahmed.securecapita.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
