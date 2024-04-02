package com.cwpark.library.data.dto.book.category;

import com.cwpark.library.data.entity.book.BookCategory;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCategoryInsUpdDto {
    private Long categoryId;

    @NotBlank(message = "이름은 필수 입력 사항 입니다")
    private String categoryName;

    public static BookCategoryInsUpdDto toDto(BookCategoryFormDto dto) {
        return BookCategoryInsUpdDto.builder()
                .categoryId(dto.getCategoryId())
                .categoryName(dto.getCategoryName())
                .build();
    }

    public static BookCategoryInsUpdDto selectToDto(BookCategoryDto dto) {
        return BookCategoryInsUpdDto.builder()
                .categoryId(dto.getCategoryId())
                .categoryName(dto.getCategoryName())
                .build();
    }
}
