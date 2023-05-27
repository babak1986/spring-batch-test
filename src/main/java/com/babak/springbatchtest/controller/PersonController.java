package com.babak.springbatchtest.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.UUID;

@RestController
@RequestMapping("person")
public class PersonController {

    final private JobLauncher jobLauncher;
    final private Job job;

    public PersonController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public String fileFormat(String fileName) {
        String[] p = fileName.split("[.]");
        return p[p.length - 1];
    }

    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) throws Exception {
        String tmpFileName = UUID.randomUUID() + "." +  fileFormat(file.getOriginalFilename());
        IOUtils.copy(file.getInputStream(), new FileOutputStream(tmpFileName));
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("filePath", tmpFileName)
                .toJobParameters();
        JobExecution execution = jobLauncher.run(job, jobParameters);
        while (execution.isRunning()) {
            System.out.println("...");
        }
        return new ResponseEntity(execution.getStatus(), HttpStatus.OK);
    }
}
