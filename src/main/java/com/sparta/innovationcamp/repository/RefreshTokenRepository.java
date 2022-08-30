package com.sparta.innovationcamp.repository;

import com.sparta.innovationcamp.domain.Member;
import com.sparta.innovationcamp.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember(Member member);
}
