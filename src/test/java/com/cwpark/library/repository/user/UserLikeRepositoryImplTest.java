package com.cwpark.library.repository.user;

import com.cwpark.library.config.TestRepositoryConfig;
import com.cwpark.library.data.dto.user.UserLikeDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.UserLike;
import com.cwpark.library.data.entity.book.Book;
import com.cwpark.library.data.entity.book.BookCategory;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.repository.book.book.BookRepository;
import com.cwpark.library.repository.book.category.BookCategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestRepositoryConfig.class)
class UserLikeRepositoryImplTest {

    @Autowired
    UserLikeRepository userLikeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookCategoryRepository bookCategoryRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("조회")
    void searchPage() {
        User user = new User(
                "id", "password", "name", "M", "941212", UserAuthority.USER, 0 , UserOauthType.EMALE, "N");
        userRepository.save(user);

        BookCategory category = new BookCategory(null, "name");
        bookCategoryRepository.save(category);

        Book book = new Book("isbn", category, "제목", "작가", "출판사", "유통사", "출판년도", "목차", "책소개", "작가소개",
                "책대표이미지", 5, 5, 0, 0, 0, 0);
        bookRepository.save(book);

        UserLike userLike = new UserLike(user, book);
        userLikeRepository.save(userLike);

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<UserLikeDto> userLikeDtos = userLikeRepository.searchPage(pageRequest, user.getUserId());

        Assertions.assertEquals(userLikeDtos.getContent().size(), 1);
    }
}