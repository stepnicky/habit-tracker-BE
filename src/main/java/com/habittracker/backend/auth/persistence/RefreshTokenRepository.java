package com.habittracker.backend.auth.persistence;

import com.habittracker.backend.auth.model.RefreshToken;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

  List<RefreshToken> findByExpirationTimeLessThan(Date expirationTime);

}
