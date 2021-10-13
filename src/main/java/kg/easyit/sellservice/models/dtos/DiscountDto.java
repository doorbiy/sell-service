package kg.easyit.sellservice.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountDto {

    Long id;
    double discount;
    Date startDate;
    Date endDate;
    ProductDto product;
}