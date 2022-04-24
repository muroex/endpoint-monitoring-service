package cz.applifting.endpointmonitoringservice;

import cz.applifting.endpointmonitoringservice.entity.User;
import cz.applifting.endpointmonitoringservice.repository.UserRepository;
import cz.applifting.endpointmonitoringservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EndpointMonitoringServiceApplication  {
    public static void main(String[] args) {
        SpringApplication.run(EndpointMonitoringServiceApplication.class, args);
    }

}
