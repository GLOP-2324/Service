package com.shoploc.shoploc.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VfpJobListener implements JobExecutionListener {

    private final VfpReader vfpReader;

    @Autowired
    public VfpJobListener(VfpReader vfpReader) {
        this.vfpReader = vfpReader;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        vfpReader.init();
    }
}
