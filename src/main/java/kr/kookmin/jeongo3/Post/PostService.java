package kr.kookmin.jeongo3.Post;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import kr.kookmin.jeongo3.Post.Dto.ResponsePostDto;
import kr.kookmin.jeongo3.PostLike.PostLikeRepository;
import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static kr.kookmin.jeongo3.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final EntityManager entityManager;

    public void savePost(RequestPostDto requestPostDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new MyException(USER_NOT_FOUND));
        Post post = requestPostDto.toEntity();
        post.setUser(user);
        postRepository.save(post);
    }

    public void deletePost(String id, String userId) { // 커스텀 예외처리
        if (!postRepository.findById(id).orElseThrow(() -> new MyException(POST_NOT_FOUND)).getUser().getId().equals(userId)) {
            throw new MyException(ACCESS_DENIED);
        }
        postRepository.deleteById(id);
    }

    public List<PostMapping> findAllPost(PostType postType, Pageable pageable) {
        Slice<PostMapping> slice = postRepository.findAllByPostTypeOrderByTimeDesc(postType, pageable);
        return new ArrayList<>(slice.getContent().stream().toList());
    }

    @Transactional
    public void updatePost(RequestPostDto requestPost, String userId) {
        if (!postRepository.findById(requestPost.getId()).orElseThrow(() -> new MyException(POST_NOT_FOUND)).getUser().getId().equals(userId)) {
            throw new RuntimeException();
        }
        Post post = entityManager.find(Post.class, requestPost.getId());
        post.setTitle(requestPost.getTitle());
        post.setContent(requestPost.getContent());
        post.setImage(requestPost.getImage());
    }

    public ResponsePostDto findPost(String postId, String userId) { // 커스텀 예외처리
        Post post = postRepository.findById(postId).orElseThrow(() -> new MyException(POST_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new MyException(USER_NOT_FOUND));
        ResponsePostDto responsePostDto = new ResponsePostDto(post);
        responsePostDto.setLike(postLikeRepository.existsByUserAndPost(user, post));
        responsePostDto.setLikeNumber(postLikeRepository.countByUserAndPost(user, post));
        return responsePostDto;
    }

    public PostMapping findHotPost(PostType postType) {
        return postRepository.findFirstHotPost(postType);
    }
}
