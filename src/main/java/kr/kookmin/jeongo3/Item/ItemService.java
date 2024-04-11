package kr.kookmin.jeongo3.Item;

import kr.kookmin.jeongo3.Exception.MyException;
import kr.kookmin.jeongo3.Item.Dto.RequestItemDto;
import kr.kookmin.jeongo3.Item.Dto.ResponseItemDto;
import kr.kookmin.jeongo3.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static kr.kookmin.jeongo3.Exception.ErrorCode.ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Response saveItem(RequestItemDto requestItemDto) {
        Item item = requestItemDto.toEntity(requestItemDto.getName(),
                                            requestItemDto.getImage(),
                                            requestItemDto.getPrice());
        itemRepository.save(item);
        return new Response("상품 저장", HttpStatus.OK);
    }

    public Response findItem(String itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new MyException(ITEM_NOT_FOUND));
        ResponseItemDto responseItemDto = new ResponseItemDto(item);
        return new Response("상품 상세 조회", responseItemDto);
    }

    public Response findAllItem() {
        List<ResponseItemDto> items = itemRepository.findAll()
                .stream()
                .map(ResponseItemDto::new)
                .collect(Collectors.toList());
        return new Response("상품 조회", items);
    }
}
