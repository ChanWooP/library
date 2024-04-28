package com.cwpark.library.config.batch;

import com.cwpark.library.data.dto.book.BookLoanDto;
import com.cwpark.library.service.SettingService;
import com.cwpark.library.service.book.BookLoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Slf4j
public class LoanBatchConfig {
    private final BookLoanService bookLoanService;
    private final SettingService settingService;

    @Bean
    public Tasklet loanTasklet() {
        return (contribution, chunkContext) -> {
            log.info("=====반납 배치 시작=====");

            int loanDate = (int) settingService.findById("loanDate").getTypeConversionValue();
            List<BookLoanDto> list = bookLoanService.findByLoanDateLessThanEqualAndLoanReturnYn(LocalDateTime.now().minusDays(loanDate));
            list.forEach((l) -> {
                bookLoanService.loanReturnBatch(l.getLoanId(), l.getBook().getBookIsbn());
                log.info("[반납] 이용자 : " + l.getUser().getUserId() + " 책 : " +l.getBook().getBookIsbn());
            });

            log.info("=====반납 배치 종료=====");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step step(JobRepository jobRepository, Tasklet loanTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository).
                tasklet(loanTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("loanReturn", jobRepository)
                .start(step)
                .build();
    }
}
