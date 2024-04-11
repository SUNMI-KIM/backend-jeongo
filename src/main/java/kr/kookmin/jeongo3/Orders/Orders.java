package kr.kookmin.jeongo3.Orders;

import jakarta.persistence.*;
import kr.kookmin.jeongo3.Item.Item;
import kr.kookmin.jeongo3.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "ORDERS_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Builder
    public Orders(User user, Item item) {
        this.user = user;
        this.item = item;
    }
}
