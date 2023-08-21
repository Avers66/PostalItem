package avers66.postalitem;

import avers66.postalitem.data.PostOffice;
import avers66.postalitem.data.PostalDelivery;
import avers66.postalitem.data.Status;
import avers66.postalitem.data.StatusHistory;
import avers66.postalitem.dto.PostOfficeDto;
import avers66.postalitem.dto.PostalDeliveryDto;
import avers66.postalitem.dto.StatusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * PostalController
 *
 * @Author Tretyakov Alexandr
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Postal Item", description = "Сервис почтовых отправлений")
@ApiResponse(responseCode = "200", description = "Successful operation")
@ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
public class PostalController {

    private final PostalService postalService;

    @GetMapping("postalitem/all")
    @Operation(description = "Получение списка всех почтовых отправлений")
    public ResponseEntity<Iterable<PostalDelivery>> getPostalItemAll() {
        return ResponseEntity.ok(postalService.getPostalItemAll());
    }

    @GetMapping("postalitem/{id}")
    @Operation(description = "Получение почтового отправления по id")
    public  ResponseEntity<PostalDelivery> getPostalItemById(@PathVariable Long id){
        return ResponseEntity.ok(postalService.getPostalItemById(id));
    }

    @PostMapping("postalitem/create")
    @Operation(description = "Создание и регистрация нового почтового отправления")
    public ResponseEntity<PostalDelivery> createPostalItem(@RequestBody PostalDeliveryDto dto){
        log.info(dto.toString());
        return ResponseEntity.ok(postalService.createPostalItem(dto));
    }

    @PutMapping("postalitem/newstatus")
    @Operation(description = "Изменение статуса почтового отправления")
    public void postalItemChangeStatus(@RequestBody StatusDto dto) {
        log.info(dto.toString());
        postalService.newStatus(dto);
    }

    @DeleteMapping("postalitem/delete/{id}")
    @Operation(description = "Удаление почтового отправления по id")
    public void deletePostalItemById(@PathVariable Long id) {
        postalService.deletePostalItemById(id);
    }

    @GetMapping("postoffice/all")
    @Operation(description = "Получение всех почтовых отделений")
    public ResponseEntity<Iterable<PostOffice>> getPostOfficeAll() {
        return ResponseEntity.ok(postalService.getPostOfficeAll());
    }

    @GetMapping("postoffice/{id}")
    @Operation(description = "Получение почтового отделения по id")
    public  ResponseEntity<PostOffice> getPostOfficeById(@PathVariable Long id){
        return ResponseEntity.ok(postalService.getPostOfficeById(id));
    }

    @PostMapping("postoffice/create")
    @Operation(description = "Создание нового почтового отделения")
    public ResponseEntity<PostOffice> createPostOffice(@RequestBody PostOfficeDto dto) {
        return ResponseEntity.ok(postalService.createPostOffice(dto));
    }

    @DeleteMapping("postoffice/delete/{id}")
    @Operation(description = "Удаление почтового отделения по id")
    public void deletePostOfficeById(@PathVariable Long id) {
        postalService.deletePostOfficeById(id);
    }

    @GetMapping("statushistory/{id}")
    @Operation(description = "Получение истории и статусов почтового отправления по id")
    public ResponseEntity<Iterable<StatusHistory>> getStatusHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(postalService.getStatusHistoryById(id));
    }

}
