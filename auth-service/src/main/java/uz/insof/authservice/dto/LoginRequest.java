package uz.insof.authservice.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private Integer code;
}
