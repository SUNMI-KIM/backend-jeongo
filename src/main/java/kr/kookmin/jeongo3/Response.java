package kr.kookmin.jeongo3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private String message;
    private Object data;

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
