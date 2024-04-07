package kr.kookmin.jeongo3.PostLike;

import kr.kookmin.jeongo3.Post.Post;
import kr.kookmin.jeongo3.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, String> {

    boolean existsByUserAndPost(User user, Post post);
    int countByUserAndPost(User user, Post post);

}
