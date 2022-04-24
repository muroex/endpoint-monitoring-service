package cz.applifting.endpointmonitoringservice.service;

import cz.applifting.endpointmonitoringservice.entity.MonitoredEndpoint;
import cz.applifting.endpointmonitoringservice.entity.MonitoringResult;
import cz.applifting.endpointmonitoringservice.repository.MonitoringResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringResultService {

     private final MonitoringResultRepository monitoringResultRepository;

    public List<MonitoringResult> findTop10ByMonitoredEndpoint(final MonitoredEndpoint monitoredEndpoint) {
        return monitoringResultRepository.findTop10ByMonitoredEndpointOrderByDateOfCheckDesc(monitoredEndpoint);
    }

}
