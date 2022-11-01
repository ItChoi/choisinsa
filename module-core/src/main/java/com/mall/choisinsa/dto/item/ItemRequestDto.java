package com.mall.choisinsa.dto.item;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class ItemRequestDto {
    @NumberFormat(pattern = "###,###")
    private int price;
}
