package avers66.postalitem.dto;

import avers66.postalitem.data.PostalDelivery;
import avers66.postalitem.data.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

/**
 * StatusDto
 *
 * @Author Tretyakov Alexandr
 */

@Data
@AllArgsConstructor
@Schema(description = "Dto для изменения статуса почтового отправления")
public class StatusDto {
    private Long postalId;
    private Long postId;
    private Status status;


}
