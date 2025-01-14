package com.project.module.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue
    private int id;
    private String token;
    private Date expireDate;
    private Integer userId;

    @OneToOne
    @JoinColumn(name="id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User userInfo;
}
