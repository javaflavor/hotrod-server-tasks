# hotrod-server-tasks

JDG7 has a new feature called remote task execution from Hot Rod clients. This module contains two deployable ServerTask implementations.

* manage-cache-task
* list-cache-task
* mass-indexer-start-task

Using the manage-cache-task, Hot Rod client can create or remove cache definitions dynamically.

The list-cache-task provides the same functionality of  EmbeddedCacheManager.getCacheNames() for Hot Rod clients.

The mass-indexer-start-task calls MassIndexer.startAsync().

## Prerequisite

* Red Hat JBoss Data Grid 7.0.0 Server

After starting JDG 7 servers, you must build the server task modules and deploy these on all JDG server of the target cluster.

~~~
$ maven clean package
$ ${JDG_HOME}/bin/ispn-cli.sh -c --controller=localhost:9990
$ cp manage-cache-task/target/manage-cache-task.jar ${JDG_HOME}/deployments/
$ cp list-cache-task/target/list-cache-task.jar ${JDG_HOME}/deployments/
$ cp mass-indexer-start-task/target/mass-indexer-start-task.jar ${JDG_HOME}/deployments/
~~~

## Usage

In order to create a cache, execute ServerTask "manage-cache" with parameter command "create" and cacheName:

~~~
cache.execute("manage-cache", new HashMap<String,String>() {{
	put("command", "create"); put("cacheName", "new-cache-1");
}});
~~~

To remove a cache, execute ServerTask "manage-cache" with parameter command "remove" and cacheName:

~~~
cache.execute("manage-cache", new HashMap<String,String>() {{
	put("command", "remove"); put("cacheName", "new-cache-1");
}});
~~~

To obtain the current cache name list, execute ServerTask "list-cache" without any parameter:

~~~
Set<String> cacheNames = cache.execute("list-cache", null);
~~~

To call MassIndexer.startAsync(), execute ServerTask "mass-indexer-start" without any parameter. The target cache must be configured for indexing.

~~~
cache.execute("mass-indexer-start", null);
~~~

