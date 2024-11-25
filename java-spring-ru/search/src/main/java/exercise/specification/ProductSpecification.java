package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component
public class ProductSpecification {
    public Specification<Product> build(ProductParamsDTO productParamsDTO) {
        return withCategory(productParamsDTO.getCategoryId()).and(withPriceGreaterThan(productParamsDTO.getPriceGt())
                .and(withPriceLessThan(productParamsDTO.getPriceLt())).and(withRankGreaterThan(productParamsDTO.getRatingGt()))
                .and(withTitleContains(productParamsDTO.getTitleCont())));
    }

    private Specification<Product> withCategory(Long category) {
        return ((root, query, criteriaBuilder) ->
                category == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("category").get("id"), category));
    }

    private Specification<Product> withPriceGreaterThan(Integer price) {
        return ((root, query, criteriaBuilder) ->
                price == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThan(root.get("price"), price));
    }

    private Specification<Product> withPriceLessThan(Integer price) {
        return ((root, query, criteriaBuilder) ->
                price == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThan(root.get("price"), price));
    }

    private Specification<Product> withRankGreaterThan(Double raiting) {
        return ((root, query, criteriaBuilder) ->
                raiting == null ? criteriaBuilder.conjunction() : criteriaBuilder.greaterThan(root.get("rating"), raiting));
    }

    private Specification<Product> withTitleContains(String title) {
        return ((root, query, criteriaBuilder) ->
                title == null ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("title"), title));
    }
}
// END
