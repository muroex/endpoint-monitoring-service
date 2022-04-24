package cz.applifting.endpointmonitoringservice.repository;

import cz.applifting.endpointmonitoringservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccessToken (String accessToken);
}
