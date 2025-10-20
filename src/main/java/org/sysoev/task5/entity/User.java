package org.sysoev.task5.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;

    public User(String username){
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + '}';
    }
}
