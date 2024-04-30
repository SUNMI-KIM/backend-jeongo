package kr.kookmin.jeongo3.Item;

import kr.kookmin.jeongo3.Item.Dto.RequestItemDto;
import kr.kookmin.jeongo3.Item.Dto.ResponseItemDto;
import kr.kookmin.jeongo3.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public ResponseEntity<Response<Object>> itemUpload(@RequestBody RequestItemDto requestItemDto) {
        itemService.saveItem(requestItemDto);
        Response response = Response.builder().message("상품 저장").data(null).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/item")
    public ResponseEntity<Response<ResponseItemDto>> itemDetail(@RequestParam String itemId) {
        ResponseItemDto responseItemDto = itemService.findItem(itemId);
        Response response = Response.builder().message("상품 상세 내용").data(responseItemDto).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/items")
    public ResponseEntity<Response<List<ResponseItemDto>>> itemList() {
        List<ResponseItemDto> itemList = itemService.findAllItem();
        Response response = Response.builder().message("게시글 수정").data(itemList).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
