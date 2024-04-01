package kr.kookmin.jeongo3.Post;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import kr.kookmin.jeongo3.Comment.Comment;
import kr.kookmin.jeongo3.Post.Dto.RequestPostDto;
import kr.kookmin.jeongo3.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "POST_ID")
    private String id;

    @Column
    private PostType postType; // 게시판 이름

    @Column
    private String title;

    @Column(length = 1000)
    private String content;

    @Column
    private int likeNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    private int views;

    @CreationTimestamp
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column
    private LocalDateTime time;

    @Column
    private String image;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("time DESC")
    private List<Comment> comments;

    @Builder
    public Post(String title, String content, String image, PostType postType) {
        this.postType = postType;
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
