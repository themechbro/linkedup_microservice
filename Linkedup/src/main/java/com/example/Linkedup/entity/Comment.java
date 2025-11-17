// package com.example.Linkedup.entity;
// import jakarta.persistence.*;  //JPA which means Jakarta Persistence formelry Java Persistence API. Used to manage relational data in Java applications by providing a standard for Object-Relational Mapping (ORM)
// import lombok.Data; //To reduce repetitive boilerplate code and improve code readability


// @Entity //same as “this Java class maps to a database table
// @Table(name="comments")  //table name in Supabase
// @Data
// public class Comment {
//     @Id 
//     @Column(name = "comment_id")
//     private String comment_id;
//     @Column(name = "likes")
//     private int likes;
// }


package com.example.Linkedup.entity;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @Column(name = "comment_id")
    private UUID commentId;

    @Column(name = "likes")
    private int likes;
}
