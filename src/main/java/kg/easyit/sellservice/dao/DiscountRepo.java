package kg.easyit.sellservice.dao;

import kg.easyit.sellservice.models.entities.Discount;
import kg.easyit.sellservice.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepo extends JpaRepository<Discount, Long> {

    List<Discount> findAllByProduct(Product product);

    @Query(value = "select * from discounts where  product_id = ?1 and current_timestamp > start_date and current_timestamp < end_date"
            , nativeQuery = true)
    Discount findActualDiscount(Long id);
}