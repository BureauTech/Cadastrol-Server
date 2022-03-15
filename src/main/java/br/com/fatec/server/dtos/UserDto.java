package br.com.fatec.server.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long useCod;
    private String useName;
    private String useEmail;
    private String usePhone;
    private String usePassword;
    private Boolean useIsAdmin;
}
