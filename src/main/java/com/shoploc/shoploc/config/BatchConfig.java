package com.shoploc.shoploc.config;

import com.shoploc.shoploc.batch.VfpProcessor;
import com.shoploc.shoploc.batch.VfpReader;
import com.shoploc.shoploc.batch.VfpWriter;
import com.shoploc.shoploc.domain.client.ClientEntity;
import com.shoploc.shoploc.domain.historique.HistoriqueAchat;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final VfpReader vfpReader;

    private final VfpProcessor vfpProcessor;

    private final VfpWriter vfpWriter;

    @Autowired
    public BatchConfig(VfpReader vfpReader, VfpProcessor vfpProcessor, VfpWriter vfpWriter) {
        this.vfpReader = vfpReader;
        this.vfpProcessor = vfpProcessor;
        this.vfpWriter = vfpWriter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public Job processVFPStatusJob (JobRepository jobRepository) {
        return new JobBuilder("VFP Status JOB", jobRepository)
                .flow(processVFPStatusStep(jobRepository)).end().build();
    }

    @Bean
    public Step processVFPStatusStep (JobRepository jobRepository) {
        this.vfpReader.init();
        return new StepBuilder("VFP Status STEP", jobRepository)
                .<Pair<List<HistoriqueAchat>, ClientEntity>, ClientEntity> chunk(10, transactionManager())
                .allowStartIfComplete(true)
                .reader(this.vfpReader)
                .processor(this.vfpProcessor)
                .writer(this.vfpWriter)
                .build();
    }
}
