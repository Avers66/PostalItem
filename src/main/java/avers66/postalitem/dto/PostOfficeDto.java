package avers66.postalitem.dto;

import lombok.Data;

import javax.persistence.Column;

/**
 * PostOfficeDto
 *
 * @Author Tretyakov Alexandr
 */

@Data
public class PostOfficeDto {

    private String name;
    private String postalCode;
    private String address;
}
