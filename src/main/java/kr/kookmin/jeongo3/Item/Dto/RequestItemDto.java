package kr.kookmin.jeongo3.Item.Dto;

import kr.kookmin.jeongo3.Item.Item;
import lombok.Getter;

@Getter
public class RequestItemDto {

    private String name;
    private String image;
    private int price;

    public Item toEntity(String name, String image, int price) {
        return Item.builder()
                .name(name)
                .image(image)
                .price(price)
                .build();
    }

}
