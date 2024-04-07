package kr.kookmin.jeongo3.Comment;

import kr.kookmin.jeongo3.Comment.Dto.RequestCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public String commentUpload(@RequestBody RequestCommentDto requestCommentDto, Authentication authentication) {
        commentService.saveComment(requestCommentDto, authentication.getName());
        return "저장";
    }

    @DeleteMapping("/comment")
    public String commentDelete(@RequestParam String commentId, Authentication authentication) {
        commentService.deleteComment(commentId, authentication.getName());
        return "삭제";
    }

}
