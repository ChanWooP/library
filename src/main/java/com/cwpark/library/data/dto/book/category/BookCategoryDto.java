package com.cwpark.library.data.dto.book.category;

import com.cwpark.library.data.entity.book.BookCategory;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class BookCategoryDto {
    private Long categoryId;

    @NotBlank(message = "이름은 필수 입력 사항 입니다")
    private String categoryName;

    @QueryProjection
    public BookCategoryDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public BookCategoryDto toDto(BookCategoryDto bookCategoryDto) {
        return BookCategoryDto.builder()
                .categoryId(bookCategoryDto.getCategoryId())
                .categoryName(bookCategoryDto.getCategoryName())
                .build();
    }
}
