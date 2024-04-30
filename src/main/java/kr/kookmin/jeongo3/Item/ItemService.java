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

    public void saveItem(RequestItemDto requestItemDto) {
        Item item = requestItemDto.toEntity(requestItemDto.getName(),
                                            requestItemDto.getImage(),
                                            requestItemDto.getPrice());
        itemRepository.save(item);
    }

    public ResponseItemDto findItem(String itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new MyException(ITEM_NOT_FOUND));
        return new ResponseItemDto(item);
    }

    public List<ResponseItemDto> findAllItem() {
        List<ResponseItemDto> items = itemRepository.findAll()
                .stream()
                .map(ResponseItemDto::new)
                .collect(Collectors.toList());
        return items;
    }
}
