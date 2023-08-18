package avers66.postalitem.data;

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
 * PostalDelivery
 *
 * @Author Tretyakov Alexandr
 */

@Data
@Entity
@Table(name = "postal_delivery")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
public class PostalDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "postal_code")
    private String postalCode;
    private String address;

    @Column(name = "recipient_name")
    private String recipientName;

    @Enumerated(EnumType.STRING)
    private Status currentStatus;
    private ZonedDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "postId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostOffice postOffice;
    //private Long postId;



    public enum Type {
        LETTER, PACKAGE
    }
}
