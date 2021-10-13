package kg.easyit.sellservice.mappers;

import kg.easyit.sellservice.models.dtos.PriceDto;
import kg.easyit.sellservice.models.entities.Price;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceMapper {

    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    Price mapToPrice(PriceDto priceDto);

    PriceDto mapToPriceDto(Price price);
}