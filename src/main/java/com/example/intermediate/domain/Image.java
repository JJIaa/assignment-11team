package com.example.intermediate.domain;

import com.example.intermediate.ObjectException.ObjectException;
import com.example.intermediate.ObjectException.URLException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String imgURL;

    public Image(Post post, String imageUrl){
        URLException.URLException(imageUrl);
        ObjectException.postValidate(post);
        this.post=post;
        this.imgURL = imageUrl;
    }

}