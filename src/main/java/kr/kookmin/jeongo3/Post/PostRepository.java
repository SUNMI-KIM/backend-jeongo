package kr.kookmin.jeongo3.Post;

import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query(value = "select p.id as id, p.title as title, p.content as content from Post p " +
            "inner join PostLike pl on p.id = pl.post.id where p.postType = :postType " +
            "group by p.id having count(pl.id) > 10") // 숫자 바꿔주기, 시간 추가하기
    PostMapping findFirstHotPost(PostType postType);

    Slice<PostMapping> findAllByPostTypeOrderByTime(PostType postType, Pageable pageable);

}
