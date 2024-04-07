package kr.kookmin.jeongo3.Post;

import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import kr.kookmin.jeongo3.Post.Dto.ResponsePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @org.springframework.web.bind.annotation.PostMapping("/post")
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
    public ResponsePostDto postDetails(@RequestParam String id, Authentication authentication) {
        return postService.findPost(id, authentication.getName());
    }

    @GetMapping("/posts")
    public List<PostMapping> postList(@RequestParam PostType postType, Pageable pageable) {
        return postService.findAllPost(postType, pageable);
    }

    @GetMapping("/hot-post")
    public PostMapping postHotFind(PostType postType) { // 함수 이름 적당한걸로 바꾸기
        return postService.findHotPost(postType);
    }

}
