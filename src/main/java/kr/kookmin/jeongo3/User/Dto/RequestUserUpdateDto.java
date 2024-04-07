package kr.kookmin.jeongo3.User.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestUserUpdateDto {

    private String name;

    private String password;

    private String univ; // 희망 대학, 재학 대학

    private String image;

}
