package kr.kookmin.jeongo3.Post;

import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public String postModify(@RequestParam String id, @RequestBody RequestPostDto requestPost, Authentication authentication) {
        postService.updatePost(id, requestPost, authentication.getName());
        return "수정";
    }

    @GetMapping("/post")
    public Post postDetails(@RequestParam String id) {
        return postService.findPost(id);
    }

    @GetMapping("/posts")
    public Page<Post> postList(@RequestParam PostType postType, @PageableDefault(size=10) Pageable pageable) {
        return postService.findAllPost(postType, pageable);
    }

}
