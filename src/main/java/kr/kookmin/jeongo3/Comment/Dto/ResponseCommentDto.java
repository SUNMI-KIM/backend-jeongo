package kr.kookmin.jeongo3.Comment.Dto;

import kr.kookmin.jeongo3.Comment.Comment;
import kr.kookmin.jeongo3.Post.Dto.ResponsePostDto;
import lombok.Getter;

@Getter
public class ResponseCommentDto {
    private String userId;
    private String univ;
    private String email;
    private String content;

    public ResponseCommentDto(Comment comment) {
        this.userId = comment.getUser().getId();
        this.univ = comment.getUser().getUniv();
        this.email = comment.getUser().getEmail();
        this.content = comment.getContent();
    }
}
