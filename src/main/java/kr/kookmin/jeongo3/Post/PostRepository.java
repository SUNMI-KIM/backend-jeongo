package kr.kookmin.jeongo3.Post;

import kr.kookmin.jeongo3.Post.Dto.PostAllMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    List<PostAllMapping> findAllByPostTypeOrderByTime(PostType postType);

}
