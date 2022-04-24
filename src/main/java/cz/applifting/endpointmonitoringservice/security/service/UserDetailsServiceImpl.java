package cz.applifting.endpointmonitoringservice.security.service;

import cz.applifting.endpointmonitoringservice.entity.User;
import cz.applifting.endpointmonitoringservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String accessToken) throws UsernameNotFoundException {
        User user = userService.findByAccessToken(accessToken).orElseThrow(() -> new UsernameNotFoundException("No user for access token: " + accessToken));
        return UserDetailsImpl.builder().user(user).build();
    }
}
