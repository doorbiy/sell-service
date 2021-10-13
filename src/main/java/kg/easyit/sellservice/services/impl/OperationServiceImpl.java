package kg.easyit.sellservice.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import kg.easyit.sellservice.dao.OperationRepo;
import kg.easyit.sellservice.mappers.OperationMapper;
import kg.easyit.sellservice.mappers.UserMapper;
import kg.easyit.sellservice.models.dtos.*;
import kg.easyit.sellservice.models.entities.Operation;
import kg.easyit.sellservice.models.responses.ErrorResponse;
import kg.easyit.sellservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepo operationRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private OperationDetailService operationDetailService;

    @Value("${jwtSecret}")
    private String secretKey;

    @Override
    public ResponseEntity<?> provideOperation(String token, List<InputDataForOperation> operationList) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        ProductDto productDto;

        double price;
        double discount;
        double amount;              // for OperationDetail
        double totalAmount = 0;     // for Operation


        List<OperationDetailDto> operationDetailDtoList = new ArrayList<>();
        List<ReceiptDetailsDto> receiptDetailsDtoList = new ArrayList<>();


        // for show to the buyer receiptDetails
        ReceiptDto receiptDto = new ReceiptDto();


        // for set userId to the receipt
        UserDto userDto;

        for (InputDataForOperation inputData : operationList) {

            productDto =
                    productService
                            .findProductByBarcodeForOperationDetails(
                                    inputData
                                            .getBarcode()
                            );

            if (Objects.isNull(productDto)) {
                return new ResponseEntity<>(
                        new ErrorResponse("Некорректно введенные данный!"
                                , "Проверьте введенный штрихкод -> " + inputData.getBarcode())
                        , HttpStatus.NOT_FOUND);
            }

            OperationDetailDto operationDetailDto = new OperationDetailDto();

            operationDetailDto
                    .setProduct(productDto);

            operationDetailDto
                    .setQuantity(
                            inputData
                                    .getQuantity());

            price = priceService.findPriceByProductForOperationDetails(productDto);

            if (discountService.getDiscountByProduct(productDto) == 0) {

                amount = price * inputData.getQuantity();
            } else {

                discount = 1 - (discountService.getDiscountByProduct(productDto) / 100);

                amount = (price * discount) * inputData.getQuantity();
            }

            totalAmount += amount;

            operationDetailDto.setAmount(amount);

            operationDetailDtoList.add(operationDetailDto);

            ReceiptDetailsDto receiptDetailsDto = new ReceiptDetailsDto();

            receiptDetailsDto
                    .setName(
                            productDto
                                    .getName()
                    );

            receiptDetailsDto.setBarcode(
                    productDto
                            .getBarcode()
            );

            receiptDetailsDto
                    .setQuantity(
                            inputData
                                    .getQuantity()
                    );

            receiptDetailsDto
                    .setPrice(price);

            receiptDetailsDto
                    .setDiscount(
                            discountService
                                    .getDiscountByProduct(productDto)
                    );

            receiptDetailsDto
                    .setAmount(amount);

            receiptDetailsDtoList.add(receiptDetailsDto);
        }

        Jws<Claims> jwt =
                Jwts
                        .parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token);

        userDto =
                UserMapper
                        .INSTANCE
                        .mapToUserDto(
                                userService
                                        .findUserByLogin(
                                                (String) jwt.getBody().get("login")
                                        )
                        );

        receiptDto
                .setCashier(
                        userDto
                                .getName()
                );

        receiptDto
                .setTotalAmount(totalAmount);

        receiptDto
                .setReceiptDetailsDto(receiptDetailsDtoList);

        Operation operation = new Operation();

        operation
                .setTotalAmount(totalAmount);

        operation
                .setUser(
                        UserMapper
                                .INSTANCE
                                .mapToUser(userDto)
                );

        operationRepo
                .save(operation);

        for (OperationDetailDto element : operationDetailDtoList) {

            element
                    .setOperation(
                            OperationMapper
                                    .INSTANCE
                                    .mapToOperationDto(operation)
                    );
        }

        operationDetailService
                .saveOperationDetails(operationDetailDtoList);

        return ResponseEntity.ok(receiptDto);
    }

    @Override
    public ResponseEntity<?> payment(String token, Long operationId, double cash) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        Operation operation =
                operationRepo
                        .findOperationById(operationId);

        if (Objects.isNull(operation)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Не найдена операция!"
                            , "Вы ввели некорректный ID операции!")
                    , HttpStatus.NOT_FOUND);
        }

        double change = cash - operation.getTotalAmount();

        if (change < 0) {
            return new ResponseEntity<>(
                    new ErrorResponse("Недостаточно средств для проведения операции!", null)
                    , HttpStatus.CONFLICT);
        }

        operation.setCash(cash);
        operation.setChange(change);

        operationRepo.save(operation);

        return ResponseEntity
                .ok(OperationMapper
                        .INSTANCE
                        .mapToOperationDto(operation)
                );
    }
}