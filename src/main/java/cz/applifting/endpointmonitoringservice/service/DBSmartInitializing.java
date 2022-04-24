package cz.applifting.endpointmonitoringservice.service;

import cz.applifting.endpointmonitoringservice.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBSmartInitializing {

     private final UserService userService;


    @EventListener
    public void init(ApplicationReadyEvent event) {

        User applifting = User.builder()
                .username("Applifting").email( "info@applifting.cz")
                .accessToken("93f39e2f-80de-4033-99ee-249d92736a25").build();

        User batman = User.builder().username("batman").email("batman@example.com")
                .accessToken("dcb20f8a-5657-4f1b-9f7f-ce65739b359e").build();

        userService.addUser(applifting);
        userService.addUser(batman);

    }
}
