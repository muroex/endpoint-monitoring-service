package cz.applifting.endpointmonitoringservice.repository;

import cz.applifting.endpointmonitoringservice.entity.MonitoredEndpoint;
import cz.applifting.endpointmonitoringservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long> {

    List<MonitoredEndpoint> findAllByUser(final User user);
    Optional<MonitoredEndpoint> findByIdAndUser(final Long id, final User user);

 }
