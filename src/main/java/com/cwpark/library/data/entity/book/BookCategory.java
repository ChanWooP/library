package com.cwpark.library.data.entity.book;

import com.cwpark.library.data.dto.book.category.BookCategoryDto;
import com.cwpark.library.data.entity.BaseEntity;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "BOOK_CATEGORY")
public class BookCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "CATEGORY_NAME")
    private String categoryName;

    public static BookCategory toEntity(BookCategoryDto bookCategoryDto) {
        return BookCategory.builder()
                .categoryId(bookCategoryDto.getCategoryId())
                .categoryName(bookCategoryDto.getCategoryName())
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BookCategory that = (BookCategory) object;
        return Objects.equals(getCategoryId(), that.getCategoryId()) && Objects.equals(getCategoryName(), that.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryId(), getCategoryName());
    }
}
