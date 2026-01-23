package com.example.Linkedup.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.Linkedup.entity.Post;
import com.example.Linkedup.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    // ✅ FIX: Constructor injection for the final field
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElse(null);
    }

   public List<Post> getPostByOwner(UUID owner){
    return postRepository.findByOwner(owner);
   }

    public boolean deletePost(UUID postId) {
        if (!postRepository.existsById(postId)) {
            return false;
        }
        postRepository.deleteById(postId);
        return true;
    }

    public boolean updatePost(UUID postId, String content, String mediaUrl){
        Post post = postRepository.findById(postId).orElse(null);
        if(post==null) return false;

        post.setContent(content);
        post.setMediaUrl(mediaUrl);

        postRepository.save(post);
        return true;
    }

}





