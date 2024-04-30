package kr.kookmin.jeongo3.Comment;

import kr.kookmin.jeongo3.Comment.Dto.RequestCommentDto;
import kr.kookmin.jeongo3.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<Response> commentUpload(@RequestBody RequestCommentDto requestCommentDto, Authentication authentication) {
        commentService.saveComment(requestCommentDto, authentication.getName());
        Response response = Response.builder().message("게시글 수정").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Response> commentDelete(@RequestParam String commentId, Authentication authentication) {
        commentService.deleteComment(commentId, authentication.getName());
        Response response = Response.builder().message("게시글 수정").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
