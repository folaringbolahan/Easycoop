package com.sift.votes.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class VotingreminderJob extends QuartzJobBean{
	private Votersremindernotif runvotTask;
	 
	public void setRunvotTask(Votersremindernotif runvotTask){
		this.runvotTask = runvotTask;
	}
 
        @Override
	protected void executeInternal(JobExecutionContext context)
		throws JobExecutionException{
               System.out.println("starting voting job reminder...");
               runvotTask.run(); 
       }
}