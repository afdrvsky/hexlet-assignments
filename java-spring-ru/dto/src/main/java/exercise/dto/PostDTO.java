package exercise.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

// BEGIN
public class PostDTO {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String body;

    @Getter
    @Setter
    private List<CommentDTO> comments;
}
// END
