package kr.kookmin.jeongo3.Orders;

import jakarta.persistence.*;
import kr.kookmin.jeongo3.Item.Item;
import kr.kookmin.jeongo3.User.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "ORDERS_ID")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

}
