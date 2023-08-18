package avers66.postalitem.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * PostOffice
 *
 * @Author Tretyakov Alexandr
 */

@Data
@Entity
@Table(name = "post_office")
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED, force=true)
public class PostOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "postal_code")
    private String postalCode;

    private String address;
}
