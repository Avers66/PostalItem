package avers66.postalitem;

import avers66.postalitem.data.PostOffice;
import avers66.postalitem.data.PostalDelivery;
import avers66.postalitem.data.Status;
import avers66.postalitem.data.StatusHistory;
import avers66.postalitem.dto.PostOfficeDto;
import avers66.postalitem.dto.PostalDeliveryDto;
import avers66.postalitem.dto.StatusDto;
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
public class PostalController {

    private final PostalService postalService;

    @GetMapping("postalitem/all")
    //@Operation(description = "Получение списка всех почтовых отправлений")
    public ResponseEntity<Iterable<PostalDelivery>> getPostalItemAll() {
        return ResponseEntity.ok(postalService.getPostalItemAll());
    }

    @GetMapping("postalitem/{id}")
    public  ResponseEntity<PostalDelivery> getPostalItemById(@PathVariable Long id){
        return ResponseEntity.ok(postalService.getPostalItemById(id));
    }

    @PostMapping("postalitem/create")
    public ResponseEntity<Long> createPostalItem(@RequestBody PostalDeliveryDto dto){
        log.info(dto.toString());
        return ResponseEntity.ok(postalService.createPostalItem(dto));
    }

    @PutMapping("postalitem/newstatus")
    public void postalItemChangeStatus(@RequestBody StatusDto dto) {
        log.info(dto.toString());
        postalService.newStatus(dto);
    }

    @DeleteMapping("postalitem/delete/{id}")
    public void deletePostalItemById(@PathVariable Long id) {
        postalService.deletePostalItemById(id);
    }

    @GetMapping("postoffice/all")
    public ResponseEntity<Iterable<PostOffice>> getPostOfficeAll() {
        return ResponseEntity.ok(postalService.getPostOfficeAll());
    }

    @GetMapping("postoffice/{id}")
    public  ResponseEntity<PostOffice> getPostOfficeById(@PathVariable Long id){
        return ResponseEntity.ok(postalService.getPostOfficeById(id));
    }

    @PostMapping("postoffice/create")
    public ResponseEntity<Long> createPostOffice(@RequestBody PostOfficeDto dto) {
        return ResponseEntity.ok(postalService.createPostOffice(dto));
    }

    @DeleteMapping("postoffice/delete/{id}")
    public void deletePostOfficeById(@PathVariable Long id) {
        postalService.deletePostOfficeById(id);
    }

    @GetMapping("statushistory/{id}")
    public ResponseEntity<Iterable<StatusHistory>> getStatusHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(postalService.getStatusHistoryById(id));
    }

}
