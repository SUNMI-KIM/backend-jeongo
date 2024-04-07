package kr.kookmin.jeongo3.Comment;

import kr.kookmin.jeongo3.Comment.Dto.RequestCommentDto;
import kr.kookmin.jeongo3.Post.PostRepository;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void saveComment(RequestCommentDto requestCommentDto, String userId) { // 커스텀 예외처리
        Comment comment = requestCommentDto.toEntity();
        comment.setPost(postRepository.findById(requestCommentDto.getPostId()).orElseThrow());
        comment.setUser(userRepository.findById(userId).orElseThrow());
        commentRepository.save(comment);
    }

    public void deleteComment(String commentId, String userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(); // 커스텀 예외처리
        if (comment.getUser().getId() != userId) {
            throw new RuntimeException(); // 니가 쓴거 아니잖아 에러
        }
        commentRepository.delete(comment);
    }
}
