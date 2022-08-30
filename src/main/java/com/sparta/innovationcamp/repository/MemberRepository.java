package com.sparta.innovationcamp.repository;

import com.sparta.innovationcamp.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);
}
