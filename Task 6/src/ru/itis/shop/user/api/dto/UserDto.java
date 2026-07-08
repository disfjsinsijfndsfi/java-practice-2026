package ru.itis.shop.user.api.dto;

//Data Transfer Object
public class UserDto {

    private Integer id;
    private String email;
    private String profileDescription;

    public UserDto(Integer id, String email, String profileDescription) {
        this.id = id;
        this.email = email;
        this.profileDescription = profileDescription;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileDescription() {
        return profileDescription;
    }
}
