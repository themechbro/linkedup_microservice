package com.example.Linkedup.service;
import com.example.Linkedup.entity.Comment;
import com.example.Linkedup.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentRepository repo;

    public int getLikes(UUID id) {
        return repo.findById(id)
                .map(Comment::getLikes)
                .orElse(0);
    }

    public int like(UUID id) {
        Comment comment = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setLikes(comment.getLikes() + 1);
        repo.save(comment);

        return comment.getLikes();
    }

    public int unlike(UUID id) {
        Comment comment = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (comment.getLikes() > 0) {
            comment.setLikes(comment.getLikes() - 1);
            repo.save(comment);
        }

        return comment.getLikes();
    }

    public int commentLength(UUID postId){
        List <Comment> comments= repo.findCommentsForSpecificPost(postId);
        if(comments==null) return 0;
        return comments.size();
        
    }
}
