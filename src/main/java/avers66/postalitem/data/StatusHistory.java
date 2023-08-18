package avers66.postalitem.data;

import avers66.postalitem.data.PostOffice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * StatusHistory
 *
 * @Author Tretyakov Alexandr
 */

@Data
@Entity
@Table(name = "status_history")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postalId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostalDelivery postalDelivery;

    @Enumerated(EnumType.STRING)
    private Status status;
    private ZonedDateTime date;


    @ManyToOne
    @JoinColumn(name = "postId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostOffice postOffice;
}
