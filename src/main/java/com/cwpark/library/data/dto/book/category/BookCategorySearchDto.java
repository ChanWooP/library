package com.cwpark.library.data.dto.book.category;

import com.cwpark.library.data.entity.book.BookCategory;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class BookCategorySearchDto {
    private Long categoryId;

    private String categoryName;

    private Long categoryCount;

    @QueryProjection
    public BookCategorySearchDto(Long categoryId, String categoryName, Long categoryCount) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryCount = categoryCount;
    }

}
