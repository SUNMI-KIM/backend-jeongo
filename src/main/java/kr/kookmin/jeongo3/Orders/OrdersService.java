package kr.kookmin.jeongo3.Orders;

import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Item.Item;
import kr.kookmin.jeongo3.Item.ItemRepository;
import kr.kookmin.jeongo3.Orders.Dto.RequestOrdersDto;
import kr.kookmin.jeongo3.Response;
import kr.kookmin.jeongo3.User.User;
import kr.kookmin.jeongo3.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static kr.kookmin.jeongo3.Exception.ErrorCode.ITEM_NOT_FOUND;
import static kr.kookmin.jeongo3.Exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrdersRepository ordersRepository;

    public void saveOrders(RequestOrdersDto requestOrdersDto) {
        User user = userRepository.findById(requestOrdersDto.getUserId()).orElseThrow(() -> new MyException(USER_NOT_FOUND));
        Item item = itemRepository.findById(requestOrdersDto.getItemId()).orElseThrow(() -> new MyException(ITEM_NOT_FOUND));

        Orders orders = new Orders(user, item);
        ordersRepository.save(orders);
    }

}
