package com.cwpark.library.repository.book.hope;

import com.cwpark.library.config.TestRepositoryConfig;
import com.cwpark.library.data.dto.book.BookHopeDto;
import com.cwpark.library.data.dto.user.UserSelectDto;
import com.cwpark.library.data.entity.User;
import com.cwpark.library.data.entity.book.BookHope;
import com.cwpark.library.data.enums.BookHopeStatus;
import com.cwpark.library.data.enums.UserAuthority;
import com.cwpark.library.data.enums.UserOauthType;
import com.cwpark.library.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestRepositoryConfig.class)
@Transactional
class BookHopeRepositoryTest {
    @Autowired
    BookHopeRepository bookHopeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("관리자 조회")
    void search() {
        User user = new User(
                "user", "password", "name", "M", "941212", UserAuthority.USER, 0, UserOauthType.EMALE, "N");
        userRepository.save(user);

        BookHope bookHope1 = new BookHope(null, user, "isbn", "title", "author", "publisher", "20201212", BookHopeStatus.REQUEST);
        BookHope bookHope2 = new BookHope(null, user, "isbn", "title1", "author", "publisher", "20201212", BookHopeStatus.REQUEST);
        bookHopeRepository.save(bookHope1);
        bookHopeRepository.save(bookHope2);

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<BookHopeDto> bookHopes = bookHopeRepository.searchPage(BookHopeStatus.REQUEST, "20201111", "20201213", "1", pageRequest);

        Assertions.assertEquals(bookHopes.getContent().size(), 1);
    }
}