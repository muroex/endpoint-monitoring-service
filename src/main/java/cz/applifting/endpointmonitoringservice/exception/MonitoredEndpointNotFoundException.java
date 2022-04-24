package cz.applifting.endpointmonitoringservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MonitoredEndpointNotFoundException extends RuntimeException {
    public MonitoredEndpointNotFoundException(String message) {
        super(message);
    }
}
