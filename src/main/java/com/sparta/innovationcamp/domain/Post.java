package com.sparta.innovationcamp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.innovationcamp.controller.request.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.SimpleTimeZone;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String s3Url;

    //  orphanRemoval = true //뭔가 이상함, post로 맵핑하는 게 아니라 코멘트로 변경해봄
    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

//  @Column
//  private int commentNum;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }

    public Post(String title, String content, String s3Url) {
        this.title = title;
        this.content = content;
        this.s3Url = s3Url;
    }

}