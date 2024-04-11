package kr.kookmin.jeongo3.Item;

import kr.kookmin.jeongo3.Item.Dto.RequestItemDto;
import kr.kookmin.jeongo3.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public ResponseEntity<Response> itemUpload(@RequestBody RequestItemDto requestItemDto) {
        Response response = itemService.saveItem(requestItemDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/item")
    public ResponseEntity<Response> itemDetail(@RequestParam String itemId) {
        Response response = itemService.findItem(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/items")
    public ResponseEntity<Response> itemList() {
        Response response = itemService.findAllItem();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
