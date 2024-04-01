package kr.kookmin.jeongo3.Post;

import kr.kookmin.jeongo3.Post.Dto.PostAllMapping;
import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import kr.kookmin.jeongo3.Post.Dto.ResponsePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public String postUpload(@RequestBody RequestPostDto requestPostDto, Authentication authentication) {
        postService.savePost(requestPostDto, authentication.getName());
        return "저장";
    }

    @DeleteMapping("/post")
    public String postDelete(@RequestParam String id, Authentication authentication) {
        postService.deletePost(id, authentication.getName());
        return "삭제";
    }

    @PatchMapping("/post")
    public String postModify(@RequestBody RequestPostDto requestPost, Authentication authentication) {
        postService.updatePost(requestPost, authentication.getName());
        return "수정";
    }

    @GetMapping("/post")
    public ResponsePostDto postDetails(@RequestParam String id) {
        return postService.findPost(id);
    }

    @GetMapping("/posts")
    public List<PostAllMapping> postList(@RequestParam PostType postType) {
        return postService.findAllPost(postType);
    }

}
