package cz.applifting.endpointmonitoringservice.service;

import cz.applifting.endpointmonitoringservice.entity.MonitoredEndpoint;
import cz.applifting.endpointmonitoringservice.entity.MonitoringResult;
import cz.applifting.endpointmonitoringservice.entity.User;
import cz.applifting.endpointmonitoringservice.exception.MonitoredEndpointNotFoundException;
import cz.applifting.endpointmonitoringservice.repository.MonitoredEndpointRepository;
import cz.applifting.endpointmonitoringservice.repository.MonitoringResultRepository;
import cz.applifting.endpointmonitoringservice.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MonitoredEndpointService {

     private final MonitoredEndpointRepository monitoredEndpointRepository;
     private final MonitoringResultRepository monitoringResultRepository;
     private final RestTemplate restTemplate = new RestTemplate();
     private final MonitoringResultService monitoringResultService;


    private User getUser() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    @Transactional
    public List<MonitoredEndpoint> findAllMonitoredEndpointByUser() {
        return monitoredEndpointRepository.findAllByUser(getUser());
    }

    public MonitoredEndpoint createMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint) {

        monitoredEndpoint.setUser(getUser());
        monitoredEndpoint.setDateOfCreation(OffsetDateTime.now());
        monitoredEndpoint.setDateOfLastCheck(null);
        monitoredEndpoint = monitoredEndpointRepository.save(monitoredEndpoint);
        return monitoredEndpoint;
    }

    public Optional<MonitoredEndpoint> findMonitoredEndpointByIdAndUser(Long id) {
        return monitoredEndpointRepository.findByIdAndUser(id, getUser());
    }



    public MonitoredEndpoint updateMonitoredEndpoint(Long id,MonitoredEndpoint monitoredEndpoint) {

        Optional<MonitoredEndpoint> endpointOpt=monitoredEndpointRepository.findByIdAndUser(id, getUser());

        if(endpointOpt.isPresent()){
            MonitoredEndpoint endpoint=endpointOpt.get();
            endpoint.setUrl(monitoredEndpoint.getUrl());
            endpoint.setName(monitoredEndpoint.getName());
            endpoint.setMonitoredInterval(monitoredEndpoint.getMonitoredInterval());
            monitoredEndpoint=monitoredEndpointRepository.save(endpoint);
        }
         else throw new  MonitoredEndpointNotFoundException("monitored endpoint with ID: " + id + " was not found");

        return monitoredEndpoint;
    }

    public void deleteMonitoredEndpoint(Long id) {
      if(monitoredEndpointRepository.existsById(id))
       monitoredEndpointRepository.deleteById(id);
       else
         throw new MonitoredEndpointNotFoundException("monitored endpoint with ID: " + id + " was not found");
    }

    public List<MonitoringResult> getTop10Results(Long id) {
        return findMonitoredEndpointByIdAndUser(id)
                .map(monitoringResultService::findTop10ByMonitoredEndpoint)
                .orElse(Collections.emptyList());
    }

    @Scheduled(fixedRate = 1000)
    @Async
     void monitorEndpoints() {

        List<MonitoredEndpoint> monitoredEndpoints = monitoredEndpointRepository.findAll();
        monitoredEndpoints.forEach(monitoredEndpoint -> {
            try {
                checkEndpoint(monitoredEndpoint, OffsetDateTime.now());
            } catch (Exception ex) {
                log.error("An error occurred during endpoint checking: " + ex.getMessage());
            }
        });
    }



    private void checkEndpoint(MonitoredEndpoint monitoredEndpoint, OffsetDateTime currentDate)  {

        Instant lastCheck=monitoredEndpoint.getDateOfLastCheck()!=null?
                monitoredEndpoint.getDateOfLastCheck().toInstant(): null;

          if (lastCheck == null || (currentDate.toInstant().getEpochSecond() -lastCheck.getEpochSecond())  >= monitoredEndpoint.getMonitoredInterval()) {
            monitoredEndpoint.setDateOfLastCheck(currentDate);

             String url = monitoredEndpoint.getUrl();

             ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

              MonitoringResult monitoringResult = MonitoringResult
                      .builder()
                     .dateOfCheck(OffsetDateTime.now()).payload(response.getBody())
                     .httpStatusCode(response.getStatusCode().value())
                     .monitoredEndpoint(monitoredEndpoint).build();
             monitoringResultRepository.save(monitoringResult);

             monitoredEndpoint.setDateOfLastCheck(monitoringResult.getDateOfCheck());
             monitoredEndpointRepository.save(monitoredEndpoint);
        }
    }

}
