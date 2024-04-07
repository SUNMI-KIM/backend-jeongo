package kr.kookmin.jeongo3.PostLike;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/post-like")
    public String  postLikeSave(String postId, Authentication authentication) {
        postLikeService.savePostLike(postId, authentication.getName());
        return "저장";
    }

    @DeleteMapping("/post-like")
    public String postLikeDelete(String postId, Authentication authentication) {
        postLikeService.deletePostLike(postId, authentication.getName());
        return "삭제";
    }

}
