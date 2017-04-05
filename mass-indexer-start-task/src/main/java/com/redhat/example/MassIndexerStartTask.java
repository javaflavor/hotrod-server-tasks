package com.redhat.example;

import org.infinispan.Cache;
import org.infinispan.query.Search;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;
import org.infinispan.tasks.TaskExecutionMode;
import org.jboss.logging.Logger;

public class MassIndexerStartTask implements ServerTask<Object> {
	static Logger log = Logger.getLogger(MassIndexerStartTask.class);
	
	TaskContext context;
	
	public Object call() throws Exception {
		Cache<?,?> cache = context.getCache().get();
		Search.getSearchManager(cache).getMassIndexer().startAsync();
		return null;
	}

	@Override
	public String getName() {
		return "mass-indexer-start";
	}

	@Override
	public TaskExecutionMode getExecutionMode() {
		return TaskExecutionMode.ONE_NODE;
	}

	@Override
	public void setTaskContext(TaskContext context) {
		this.context = context;
	}
}
