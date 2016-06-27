package com.redhat.example;

import java.util.Map;
import java.util.Set;

import org.infinispan.Cache;
import org.infinispan.commons.CacheException;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;
import org.infinispan.tasks.TaskExecutionMode;
import org.jboss.logging.Logger;

public class ListCacheTask implements ServerTask<Set<String>> {
	static Logger log = Logger.getLogger(ListCacheTask.class);
	
	TaskContext context;
	
	public Set<String> call() throws Exception {
		Cache<?,?> defaultCache = context.getCache().get();
		EmbeddedCacheManager manager = defaultCache.getCacheManager();
		return manager.getCacheNames();
	}

	@Override
	public String getName() {
		return "list-cache";
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
