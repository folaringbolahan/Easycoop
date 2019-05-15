package com.sift.loan.utility;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class FileUploadJob extends QuartzJobBean{
	private EazyCoopUtility runMeTask;
	 
	public void setRunMeTask(EazyCoopUtility runMeTask){
		this.runMeTask = runMeTask;
	}
 
	protected void executeInternal(JobExecutionContext context)
		throws JobExecutionException{	 
		runMeTask.doBulkRepayment(); 
	}
}