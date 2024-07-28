package kr.kookmin.jeongo3.Comment;

import kr.kookmin.jeongo3.Comment.Dto.RequestCommentDto;
import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Post.PostRepository;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kr.kookmin.jeongo3.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void saveComment(RequestCommentDto requestCommentDto, User user) {
        Comment comment = requestCommentDto.toEntity();
        comment.setPost(postRepository.findById(requestCommentDto.getPostId()).orElseThrow(() -> new MyException(POST_NOT_FOUND)));
        comment.setUser(user);
        commentRepository.save(comment);
    }

    public void deleteComment(String commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new MyException(COMMENT_NOT_FOUND)); // 커스텀 예외처리
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new MyException(BAD_REQUEST);
        }
        commentRepository.delete(comment);
    }
}
