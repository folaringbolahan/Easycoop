package com.sift.gl.service;

//import com.sift.loan.utility.*;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class EODJob extends QuartzJobBean{
	private RuneodScheduler runEodTask;
	 
	public void setRunEodTask(RuneodScheduler runEodTask){
		this.runEodTask = runEodTask;
	}
 
        @Override
	protected void executeInternal(JobExecutionContext context)
		throws JobExecutionException{
           //System.out.println("ccheck 3323");
		runEodTask.run(); 
           

	}
}