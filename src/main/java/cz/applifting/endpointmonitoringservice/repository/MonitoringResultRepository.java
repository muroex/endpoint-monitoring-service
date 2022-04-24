package cz.applifting.endpointmonitoringservice.repository;

import cz.applifting.endpointmonitoringservice.entity.MonitoredEndpoint;
import cz.applifting.endpointmonitoringservice.entity.MonitoringResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long> {

    @Transactional
    List<MonitoringResult> findTop10ByMonitoredEndpointOrderByDateOfCheckDesc(final MonitoredEndpoint monitoredEndpoint);

}
