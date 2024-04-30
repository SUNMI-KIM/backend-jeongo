package kr.kookmin.jeongo3.Post;

import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import kr.kookmin.jeongo3.Post.Dto.ResponsePostDto;
import kr.kookmin.jeongo3.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Response<Object>> postUpload(@RequestBody RequestPostDto requestPostDto, Authentication authentication) {
        postService.savePost(requestPostDto, authentication.getName());
        Response response = Response.builder().message("게시글 저장").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/post")
    public ResponseEntity<Response<Object>> postDelete(@RequestParam String id, Authentication authentication) {
        postService.deletePost(id, authentication.getName());
        Response response = Response.builder().message("게시글 삭제").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/post")
    public ResponseEntity<Response<Object>> postModify(@RequestBody RequestPostDto requestPost, Authentication authentication) {
        postService.updatePost(requestPost, authentication.getName());
        Response response = Response.builder().message("게시글 수정").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/post")
    public ResponseEntity<Response<ResponsePostDto>> postDetails(@RequestParam String id, Authentication authentication) {
        ResponsePostDto responsePostDto = postService.findPost(id, authentication.getName());
        Response response = Response.builder().message("게시글 상세 내용").data(responsePostDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/posts")
    public ResponseEntity<Response<List<PostMapping>>> postList(@RequestParam PostType postType, @PageableDefault(size = 10)Pageable pageable) { // page = 10 이런 식으로 보내주기
        List<PostMapping> postList = postService.findAllPost(postType, pageable);
        Response response = Response.builder().message("모든 게시글").data(postList).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/hot-post")
    public ResponseEntity<Response<PostMapping>> postHotFind(PostType postType) { // 함수 이름 적당한걸로 바꾸기
        PostMapping post = postService.findHotPost(postType);
        Response response = Response.builder().message("핫 게시글").data(post).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
