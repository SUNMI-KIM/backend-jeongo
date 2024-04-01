package kr.kookmin.jeongo3.Post;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final EntityManager entityManager;

    public void savePost(RequestPostDto requestPostDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = requestPostDto.toEntity();
        post.setUser(user);
        postRepository.save(post);
    }

    public void deletePost(String id, String userId) {
        if (!postRepository.findById(id).orElseThrow().getUser().getId().equals(userId)) {
            throw new RuntimeException();
        }
        postRepository.deleteById(id);
    }

    public Page<Post> findAllPost(PostType postType, Pageable pageable) {
        return postRepository.findAllByPostTypeOrderByTimeDesc(postType, pageable);
    }

    @Transactional
    public void updatePost(String id, RequestPostDto RequestPost, String userId) {
        if (!postRepository.findById(id).orElseThrow().getUser().getId().equals(userId)) {
            throw new RuntimeException();
        }
        Post post = entityManager.find(Post.class, id);
        post.setTitle(RequestPost.getTitle());
        post.setContent(RequestPost.getContent());
        post.setImage(RequestPost.getImage());
    }

    public Post findPost(String id) {
        return postRepository.findById(id).orElseThrow();
    }

    /*public Post findHotPost() {
        return postRepository.findFirstByOrderByLikeNumberDescAndOrderByTimeDesc();
    }*/

}
