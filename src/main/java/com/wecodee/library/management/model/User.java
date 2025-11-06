package com.wecodee.library.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Users")
@Data
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long Userid;

    @Column(nullable = false,unique = true)
    private String userName;

    private  long  phoneNumber;

    private String role="STUDENT";

    @Column(nullable = false,unique = true)
    private  String email;

    private  String password;

}
