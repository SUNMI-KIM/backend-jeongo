package kr.kookmin.jeongo3.PostLike;

import jakarta.persistence.*;
import kr.kookmin.jeongo3.Post.Post;
import kr.kookmin.jeongo3.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PostLike {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "POSTLIKE_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    public PostLike(User user, Post post) {
        this.user = user;
        this.post = post;
    }

}
