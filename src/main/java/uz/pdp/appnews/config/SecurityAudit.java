package uz.pdp.appnews.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.appnews.entity.User;

import java.util.Optional;

public class SecurityAudit implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals("" + authentication.getPrincipal()))) {
            return Optional.of(((User)authentication.getPrincipal()).getId());
        }
        return Optional.empty();
    }
}
