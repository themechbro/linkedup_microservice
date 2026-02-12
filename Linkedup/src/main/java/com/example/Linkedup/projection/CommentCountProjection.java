package com.example.Linkedup.projection;

import java.util.UUID;

public interface CommentCountProjection {
UUID getPostId();

long getCount();
    
} 
