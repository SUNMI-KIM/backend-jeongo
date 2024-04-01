package kr.kookmin.jeongo3.Comment;

import kr.kookmin.jeongo3.Comment.Dto.RequestCommentDto;
import kr.kookmin.jeongo3.Post.PostRepository;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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
}
