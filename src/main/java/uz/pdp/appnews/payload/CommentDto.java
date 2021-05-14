package uz.pdp.appnews.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    @NotBlank
    private String text;

    @NotNull
    private Long postId;
}
