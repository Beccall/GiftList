package xyz.levell.christmaslist.Service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@NoArgsConstructor
public class AuthenticationService {

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
