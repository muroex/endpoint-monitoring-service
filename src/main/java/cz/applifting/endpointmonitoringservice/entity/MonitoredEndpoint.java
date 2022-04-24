package cz.applifting.endpointmonitoringservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Instant;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitoredEndpoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 45)
    private String name;

    @Column(name = "url")
    @URL(message = "URL format is wrong")
    private String url;

    @Column(updatable = false)
    @CreationTimestamp
    private OffsetDateTime dateOfCreation;

    @Positive
    private Integer monitoredInterval;

    private OffsetDateTime dateOfLastCheck;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "monitoredEndpoint", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MonitoringResult> monitoringResults = new ArrayList<>();
}

