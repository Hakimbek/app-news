package uz.pdp.appnews.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
@Data
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;

    private String resourceField;

    private Object object;
}
