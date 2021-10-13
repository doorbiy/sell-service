package kg.easyit.sellservice.mappers;

import kg.easyit.sellservice.models.dtos.DiscountDto;
import kg.easyit.sellservice.models.entities.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DiscountMapper {

    DiscountMapper INSTANCE = Mappers.getMapper(DiscountMapper.class);

    Discount mapToDiscount(DiscountDto discountDto);

    DiscountDto mapToDiscountDto(Discount discount);
}