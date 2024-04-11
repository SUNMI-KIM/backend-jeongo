package kr.kookmin.jeongo3.Item.Dto;

import kr.kookmin.jeongo3.Item.Item;
import lombok.Getter;

@Getter
public class ResponseItemDto {

    private String id;
    private String name;
    private String image;
    private int price;

    public ResponseItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.image = item.getImage();
        this.price = item.getPrice();
    }

}
