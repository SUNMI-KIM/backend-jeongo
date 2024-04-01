package kr.kookmin.jeongo3.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    Page<Post> findAllByPostTypeOrderByTimeDesc(PostType postType, Pageable pageable);

}
