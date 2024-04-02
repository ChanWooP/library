package com.cwpark.library.data.dto.book.category;

import com.cwpark.library.data.entity.book.BookCategory;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCategoryFormDto {
    private String postType;

    private Long categoryId;

    @NotBlank(message = "이름은 필수 입력 사항 입니다")
    private String categoryName;
}
