package cz.applifting.endpointmonitoringservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
 public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;


    @NotBlank
     private String username;

    @Email
    @NotBlank
     private String email;

    @NotBlank
    @Column(unique = true)
    private String accessToken;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<MonitoredEndpoint> monitoredEndpoint;

}
