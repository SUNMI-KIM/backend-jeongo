package kr.kookmin.jeongo3.Post;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import kr.kookmin.jeongo3.Post.Dto.ResponsePostDto;
import kr.kookmin.jeongo3.PostLike.PostLikeRepository;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final EntityManager entityManager;

    public void savePost(RequestPostDto requestPostDto, String userId) { // 유저가 존재하는지 확인
        User user = userRepository.findById(userId).orElseThrow();
        Post post = requestPostDto.toEntity();
        post.setUser(user);
        postRepository.save(post);
    }

    public void deletePost(String id, String userId) { // 커스텀 예외처리
        if (!postRepository.findById(id).orElseThrow().getUser().getId().equals(userId)) {
            throw new RuntimeException();
        }
        postRepository.deleteById(id);
    }

    public List<PostMapping> findAllPost(PostType postType, Pageable pageable) {
        Slice<PostMapping> slice = postRepository.findAllByPostTypeOrderByTime(postType, pageable);
        return new ArrayList<>(slice.getContent().stream().toList());
    }

    @Transactional
    public void updatePost(RequestPostDto requestPost, String userId) { // 커스텀 예외처리, 유저가 존재하는지 확인
        if (!postRepository.findById(requestPost.getId()).orElseThrow().getUser().getId().equals(userId)) {
            throw new RuntimeException();
        }
        Post post = entityManager.find(Post.class, requestPost.getId());
        post.setTitle(requestPost.getTitle());
        post.setContent(requestPost.getContent());
        post.setImage(requestPost.getImage());
    }

    public ResponsePostDto findPost(String postId, String userId) { // 커스텀 예외처리
        Post post = postRepository.findById(postId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();
        ResponsePostDto responsePostDto = new ResponsePostDto(post);
        responsePostDto.setLike(postLikeRepository.existsByUserAndPost(user, post));
        responsePostDto.setLikeNumber(postLikeRepository.countByUserAndPost(user, post));
        return responsePostDto;
    }

    public PostMapping findHotPost(PostType postType) {
        return postRepository.findFirstHotPost(postType);
    }
}
