package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.io.Serial;
import java.io.Serializable;

/**
 * a class for storing coordinates data
 */
@Data
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "user",schema = "s409397")
public class User {
    @Id
    private String login;
    private String password;
    private String salt;

}
