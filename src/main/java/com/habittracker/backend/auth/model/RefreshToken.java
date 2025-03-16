package com.habittracker.backend.auth.model;

import com.habittracker.backend.feature.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {

  @Id
  @GeneratedValue
  @Column(name = "refresh_token_id")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "issued_for_user_id", foreignKey = @ForeignKey(name = "fk_refresh_token_on_user"), nullable = false)
  private User issuedFor;

  @Column(name = "expiration_time", nullable = false)
  private Date expirationTime;

  public RefreshToken(User user, Date date) {
    this.expirationTime = date;
    this.issuedFor = user;
  }
}
