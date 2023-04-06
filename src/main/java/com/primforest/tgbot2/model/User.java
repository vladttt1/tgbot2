package com.primforest.tgbot2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name="user")

public class User {
    public Timestamp getRegisteredAt() {
        return registeredAt;
    }



    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue
    private Long chatId;
    private String firstName;
    private  String lastName;
    private  String username;

    @Override
    public String toString() {
        return "User{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }

    private Timestamp registeredAt;


    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt=registeredAt;
    }
}
