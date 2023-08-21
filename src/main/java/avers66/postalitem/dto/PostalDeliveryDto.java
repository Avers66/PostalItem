package avers66.postalitem.dto;

import avers66.postalitem.data.PostOffice;
import avers66.postalitem.data.PostalDelivery;
import avers66.postalitem.data.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * PostalItemDto
 *
 * @Author Tretyakov Alexandr
 */

@Data
@AllArgsConstructor
@Schema(description = "Dto для создания нового почтового отправления")
public class PostalDeliveryDto {

    private PostalDelivery.Type type;
    private String postalCode;
    private String address;
    private String recipientName;
    private Long postId;
}
