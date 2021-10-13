package kg.easyit.sellservice.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReceiptDetailsDto {

    String name;
    String barcode;
    int quantity;
    double price;
    double discount;
    double amount;
}