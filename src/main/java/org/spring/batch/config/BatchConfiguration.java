package org.spring.batch.config;


import org.spring.batch.listener.HwJobExecutionListener;
import org.spring.batch.listener.HwStepExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {
	@Autowired private JobBuilderFactory jobs;
	@Autowired private StepBuilderFactory steps;
	@Autowired private HwJobExecutionListener hwJobExecutionListener;
	@Autowired private HwStepExecutionListener hwStepExecutionListener;

	@Bean
	public Step step1() {
		return steps.get("step1")
				.listener(hwStepExecutionListener)
				.tasklet(helloworldTasklet())
				.build();
	}

	private Tasklet helloworldTasklet() {
		return (new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("hello world");
				return RepeatStatus.FINISHED;
			}
		});
	}
	
	@Bean
	public Job helloworldJob() {
		return jobs.get("helloworldJob")
				.listener(hwJobExecutionListener)
				.start(step1())
				.build();
	}


}
