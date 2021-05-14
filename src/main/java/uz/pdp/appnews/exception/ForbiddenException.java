package uz.pdp.appnews.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.FORBIDDEN)
@AllArgsConstructor
@Data
public class ForbiddenException extends RuntimeException{
    private String type;

    private String message;
}
