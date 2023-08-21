package avers66.postalitem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

/**
 * PostOfficeDto
 *
 * @Author Tretyakov Alexandr
 */

@Data
@AllArgsConstructor
@Schema(description = "Dto для создания нового почтового отделения")
public class PostOfficeDto {

    private String name;
    private String postalCode;
    private String address;
}
