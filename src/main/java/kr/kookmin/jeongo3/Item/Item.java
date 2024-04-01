package kr.kookmin.jeongo3.Item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "ITEM_ID")
    private String id;

    @Column
    private String name;

    @Column
    private String image;

    @Column
    private int price;
}
