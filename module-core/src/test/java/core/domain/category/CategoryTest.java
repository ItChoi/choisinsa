package core.domain.category;

import core.common.enumeration.category.CategoryAreaType;

import java.util.Set;

public class CategoryTest {
    public static Category initUnsortedTopCategory() {
        return new Category(
                1L,
                CategoryAreaType.MAIN,
                null,
                null,
                Set.of(
                        new Category(
                                2L,
                                CategoryAreaType.MAIN,
                                1L,
                                null,
                                Set.of(
                                        new Category(
                                                4L,
                                                CategoryAreaType.MAIN,
                                                2L,
                                                null,
                                                null,
                                                "event-c111",
                                                "이벤트-c111",
                                                "EVENT",
                                                3,
                                                1
                                        )
                                ),
                                "event-c11",
                                "이벤트-c11",
                                "EVENT",
                                2,
                                1
                        ),
                        new Category(
                                3L,
                                CategoryAreaType.MAIN,
                                1L,
                                null,
                                null,
                                "event-c22",
                                "이벤트-c22",
                                "EVENT",
                                2,
                                2
                        )

                ),
                "event",
                "이벤트",
                "EVENT",
                1,
                1
        );
    }

}