package com.codinghavoc.monolith.listmanager.entity;

import com.codinghavoc.monolith.listmanager.dto.UserDto;
import com.codinghavoc.monolith.util.PasswordHashUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Table;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users", schema = "list_manager")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "email_address")
    private String emailAddress;

    @NonNull
    @Column(name = "password_hash")
    private String passwordHash;

    @NonNull
    @Column(name = "password_salt")
    private String passwordSalt;

    public User(UserDto dto){
        this.firstName = dto.firstName;
        this.lastName = dto.lastName;
        this.emailAddress = dto.email;
        if(dto.pwClear != null){
            String[] pass;
            pass = PasswordHashUtil.hashPWWPBKDF(dto.pwClear);
            this.passwordSalt = pass[0];
            this.passwordHash = pass[1];
        }
    }
    
}
