package core.repository.item;

import core.domain.item.ItemReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemReviewRepository extends JpaRepository<ItemReview, Long> {

}