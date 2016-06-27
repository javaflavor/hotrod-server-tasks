package com.redhat.example;

import java.util.Map;

import org.infinispan.Cache;
import org.infinispan.commons.CacheException;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;
import org.infinispan.tasks.TaskExecutionMode;

public class ManageCacheTask implements ServerTask<Object> {
	TaskContext context;
	
	public Object call() throws Exception {
		Cache<?,?> defaultCache = context.getCache().get();
		EmbeddedCacheManager manager = defaultCache.getCacheManager();
		Map<String,?> params = context.getParameters().get();
		String command = (String)params.get("command");
		String cacheName = (String)params.get("cacheName");
		switch (command) {
		case "create":
			manager.defineConfiguration(cacheName, defaultCache.getCacheConfiguration());
			manager.getCache(cacheName);
			break;
		case "remove":
			Cache<?,?> cache = manager.getCache(cacheName);
			cache.clear();
			manager.removeCache(cacheName);
			break;
		default:
			throw new CacheException("Unkown command: "+command);
		}
		return null;
	}

	@Override
	public String getName() {
		return "manage-cache";
	}

	@Override
	public TaskExecutionMode getExecutionMode() {
		return TaskExecutionMode.ALL_NODES;
	}

	@Override
	public void setTaskContext(TaskContext context) {
		this.context = context;
	}
}
