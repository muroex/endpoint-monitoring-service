package cz.applifting.endpointmonitoringservice.service;

import cz.applifting.endpointmonitoringservice.entity.MonitoredEndpoint;
import cz.applifting.endpointmonitoringservice.entity.User;
import cz.applifting.endpointmonitoringservice.repository.MonitoredEndpointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MonitoredEndpointServiceTest {

    @InjectMocks
    MonitoredEndpointService monitoredEndpointService;

    @Mock
    MonitoredEndpointRepository monitoredEndpointRepository;

    @Test
    void createMonitoredEndpoint() {

        MonitoredEndpoint monitoredEndpoint = MonitoredEndpoint.builder()
                .id(1L)
                .url("Test")
                .name("https://test.com")
                .monitoredInterval(60)
                .build();


        User user = new User();
        user.setId(1L);
        MonitoredEndpoint monitoredEndpointResult = new MonitoredEndpoint();
        monitoredEndpoint.setMonitoredInterval(60);
        monitoredEndpoint.setName("Test");
        monitoredEndpoint.setUser(user);
        monitoredEndpoint.setUrl("https://test.com");

         when(monitoredEndpointRepository.save(monitoredEndpoint)).thenReturn(monitoredEndpointResult);

        assertEquals(monitoredEndpointService.createMonitoredEndpoint(monitoredEndpoint).getUrl(),monitoredEndpointResult.getUrl());
        assertEquals(monitoredEndpointService.createMonitoredEndpoint(monitoredEndpoint).getName(),monitoredEndpointResult.getName());
        assertEquals(monitoredEndpointService.createMonitoredEndpoint(monitoredEndpoint).getMonitoredInterval(),monitoredEndpointResult.getMonitoredInterval());

    }


    @Test
    void updateMonitoredEndpoint() {

        MonitoredEndpoint newMonitoredEndpoint = MonitoredEndpoint.builder()
                .monitoredInterval(100)
                .name("test1")
                .url("http://abcd.com")
                .build();

        User user = new User();
        user.setId(1L);
        user.setAccessToken("93f39e2f-80de-4033-99ee-249d92736a25");

        Long id = 1L;

        MonitoredEndpoint oldMonitoredEndpoint = new MonitoredEndpoint();
        newMonitoredEndpoint.setUrl("http://abc.com");
        newMonitoredEndpoint.setName("test");
        newMonitoredEndpoint.setMonitoredInterval(30);

        MonitoredEndpoint monitoredEndpointResult = new MonitoredEndpoint();
        newMonitoredEndpoint.setUrl("http://abcd.com");
        newMonitoredEndpoint.setName("test1");
        newMonitoredEndpoint.setMonitoredInterval(100);

         lenient().when(monitoredEndpointRepository
                .findByIdAndUser(id,user)).thenReturn(Optional.of(oldMonitoredEndpoint));
         lenient().when(monitoredEndpointRepository
                .save(any())).thenReturn(monitoredEndpointResult);

         assertEquals(monitoredEndpointService
                 .updateMonitoredEndpoint(id,newMonitoredEndpoint)
                 .getMonitoredInterval(),monitoredEndpointResult.getMonitoredInterval());

         assertEquals(monitoredEndpointService
                 .updateMonitoredEndpoint(id,newMonitoredEndpoint).getUrl(),monitoredEndpointResult.getUrl());

         assertEquals(monitoredEndpointService
                 .updateMonitoredEndpoint(id,newMonitoredEndpoint).getName(),monitoredEndpointResult.getName());

    }

}