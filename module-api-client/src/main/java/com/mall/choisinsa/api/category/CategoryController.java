package com.mall.choisinsa.api.category;

import core.dto.ResponseWrapper;
import com.mall.choisinsa.enumeration.category.CategoryAreaType;
import com.mall.choisinsa.enumeration.category.CategoryType;
import core.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{areaType}/{categoryType}")
    public ResponseWrapper getAllByAreaTypeAndCategoryType(@PathVariable CategoryAreaType areaType,
                                                           @PathVariable CategoryType categoryType) {
        return ResponseWrapper.ok(categoryService.findTopCategoryResponseDtoBy(areaType, categoryType));
    }

    @GetMapping("/all-type")
    public ResponseWrapper getCategoryTypeAll() {
        return ResponseWrapper.ok(categoryService.findCategoryTypeAll());
    }

}
