package com.shoploc.shoploc.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BatchScheduler {

    private final JobLauncher jobLauncher;

    private final Job vfpJob;

    public BatchScheduler(JobLauncher jobLauncher, Job vfpJob) {
        this.jobLauncher = jobLauncher;
        this.vfpJob = vfpJob;
    }

    @Scheduled(cron = "${batch.scheduled.cron.expression}")
    public void runBatchJob() throws Exception {
        jobLauncher.run(vfpJob, new JobParameters());
    }
}
