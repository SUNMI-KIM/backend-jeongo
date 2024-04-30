package kr.kookmin.jeongo3.PostLike;

import kr.kookmin.jeongo3.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/post-like")
    public ResponseEntity<Response<Object>> postLikeSave(String postId, Authentication authentication) {
        postLikeService.savePostLike(postId, authentication.getName());
        Response response = Response.builder().message("좋아요 저장").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/post-like")
    public ResponseEntity<Response<Object>> postLikeDelete(String postId, Authentication authentication) {
        postLikeService.deletePostLike(postId, authentication.getName());
        Response response = Response.builder().message("좋아요 삭제").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
