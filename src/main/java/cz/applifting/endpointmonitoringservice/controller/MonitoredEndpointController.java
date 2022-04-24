package cz.applifting.endpointmonitoringservice.controller;

import cz.applifting.endpointmonitoringservice.entity.MonitoredEndpoint;
 import cz.applifting.endpointmonitoringservice.entity.User;
import cz.applifting.endpointmonitoringservice.security.service.UserDetailsImpl;
import cz.applifting.endpointmonitoringservice.service.MonitoredEndpointService;
import lombok.RequiredArgsConstructor;
 import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/monitoredEndpoint")
public class MonitoredEndpointController {

     private final MonitoredEndpointService monitoredEndpointService;


    @GetMapping
    public ResponseEntity<?>getAllMonitoredEndpoint() {
          return new ResponseEntity<>(monitoredEndpointService.findAllMonitoredEndpointByUser(), HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<?>addMonitoredEndpoint(@RequestBody @Valid MonitoredEndpoint monitoredEndpoint) {
        return new ResponseEntity<> (monitoredEndpointService.createMonitoredEndpoint(monitoredEndpoint),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  Optional<MonitoredEndpoint> getAllEndpointById(@PathVariable Long id) {
        return monitoredEndpointService.findMonitoredEndpointByIdAndUser(id);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?>  updateMonitoredEndpoint(@PathVariable Long id, @RequestBody MonitoredEndpoint monitoredEndpoint) {
        return new ResponseEntity<>(monitoredEndpointService.updateMonitoredEndpoint(id, monitoredEndpoint),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteMonitoredEndpoint(@PathVariable Long id) {
        monitoredEndpointService.deleteMonitoredEndpoint(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/results")
    public  ResponseEntity<?> getTop10Results(@PathVariable Long id) {
        return  new ResponseEntity<> (monitoredEndpointService.getTop10Results(id),HttpStatus.OK);
    }
}
