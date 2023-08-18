package avers66.postalitem.dto;

import avers66.postalitem.data.PostalDelivery;
import avers66.postalitem.data.Status;
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
public class StatusDto {
    private Long postalId;
    private Long postId;
    private Status status;


}
