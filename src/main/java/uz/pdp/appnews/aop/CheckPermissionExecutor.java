package uz.pdp.appnews.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.appnews.entity.User;
import uz.pdp.appnews.exception.ForbiddenException;

@Component
@Aspect
public class CheckPermissionExecutor {

    @Before(value = "@annotation(checkPermission)")
    public void checkUserPermission(CheckPermission checkPermission) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean exist = false;
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(checkPermission.permission())) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            throw new ForbiddenException(checkPermission.permission(), "Ruxsat yoq");
        }
    }
}
