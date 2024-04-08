package kr.kookmin.jeongo3.Post;

import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import kr.kookmin.jeongo3.Post.Dto.ResponsePostDto;
import kr.kookmin.jeongo3.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @org.springframework.web.bind.annotation.PostMapping("/post")
    public ResponseEntity<Response> postUpload(@RequestBody RequestPostDto requestPostDto, Authentication authentication) {
        Response response = postService.savePost(requestPostDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/post")
    public ResponseEntity<Response> postDelete(@RequestParam String id, Authentication authentication) {
        Response response = postService.deletePost(id, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/post")
    public ResponseEntity<Response> postModify(@RequestBody RequestPostDto requestPost, Authentication authentication) {
        Response response = postService.updatePost(requestPost, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/post")
    public ResponseEntity<Response> postDetails(@RequestParam String id, Authentication authentication) {
        Response response = postService.findPost(id, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/posts")
    public ResponseEntity<Response> postList(@RequestParam PostType postType, Pageable pageable) {
        Response response = postService.findAllPost(postType, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/hot-post")
    public ResponseEntity<Response> postHotFind(PostType postType) { // 함수 이름 적당한걸로 바꾸기
        Response response = postService.findHotPost(postType);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
