package cz.applifting.endpointmonitoringservice.service;

import cz.applifting.endpointmonitoringservice.entity.User;
import cz.applifting.endpointmonitoringservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository  userRepository;

    public Optional<User> findByAccessToken(final String accessToken) {
        return userRepository.findByAccessToken(accessToken);
    }

    public User addUser(User user){

        return userRepository.save(user);
    }

}
