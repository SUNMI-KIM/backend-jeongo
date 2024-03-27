package kr.kookmin.jeongo3.Post;

import jakarta.persistence.*;
import kr.kookmin.jeongo3.Comment.Comment;
import kr.kookmin.jeongo3.User.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "POST_ID")
    private UUID uuid;

    @Column
    private PostType postType; // 게시판 이름

    @Column
    private String title;

    @Column(length = 1000)
    private String content;

    @Column
    private int likeNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    private int views;

    @CreationTimestamp
    @Column
    private LocalDateTime time;

    @Column
    private String image;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("time DESC")
    private List<Comment> comments;
}
