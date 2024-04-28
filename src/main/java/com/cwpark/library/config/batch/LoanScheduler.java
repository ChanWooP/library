package com.cwpark.library.config.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class LoanScheduler {

    private final JobLauncher jobLauncher;
    private final LoanBatchConfig batchConfig;
    private final JobRepository jobRepository;
    private final Step step;

    @Scheduled(cron = "0 0/1 * * * *", zone = "Asia/Seoul")
    public void runJob() {
        String time = LocalDateTime.now().toString();
        JobParametersBuilder jobParam = new JobParametersBuilder().addString("time", time);

        try {
            jobLauncher.run(batchConfig.job(jobRepository, step), jobParam.toJobParameters());
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

            log.error(e.getMessage());
        }
    }
}
