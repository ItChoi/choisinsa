package core.repository.item;

import core.domain.item.Item;
import core.dto.client.request.item.ItemDetailRequestDto;
import core.dto.client.response.item.ItemDetailInfoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

}