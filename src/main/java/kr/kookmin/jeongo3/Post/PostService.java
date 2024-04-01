package kr.kookmin.jeongo3.Post;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Post.Dto.PostAllMapping;
import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import kr.kookmin.jeongo3.Post.Dto.ResponsePostDto;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
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

    public List<PostAllMapping> findAllPost(PostType postType) { // 페이징 기법 몇 페이지씩 불러올지 처리 or 다른 방식
        return postRepository.findAllByPostTypeOrderByTime(postType);
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

    public ResponsePostDto findPost(String id) { // 커스텀 예외처리
        Post post = postRepository.findById(id).orElseThrow();
        return new ResponsePostDto(post);
    }

    /*public Post findHotPost() { // 한번에 주는게 좋은지 프론트 논의
        return postRepository.findFirstByOrderByLikeNumberDescAndOrderByTimeDesc();
    }*/
}
