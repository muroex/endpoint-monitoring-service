package cz.applifting.endpointmonitoringservice.entity;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitoringResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     private OffsetDateTime dateOfCheck;

     private Integer httpStatusCode;

     @Lob
     private String payload;

     @ManyToOne
     @JoinColumn(name = "monitored_endpoint_id")
     private MonitoredEndpoint monitoredEndpoint;

 }
