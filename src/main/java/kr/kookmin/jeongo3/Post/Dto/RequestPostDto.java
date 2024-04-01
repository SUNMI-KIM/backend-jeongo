package kr.kookmin.jeongo3.Post.Dto;

import kr.kookmin.jeongo3.Post.Post;
import kr.kookmin.jeongo3.Post.PostType;
import lombok.Getter;

@Getter
public class RequestPostDto {

    private String id;
    private String title;
    private String content;
    private String image;
    private PostType postType;

    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .image(image)
                .postType(postType)
                .build();
    }
}
