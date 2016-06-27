# hotrod-server-tasks

JDG7 has a remote task execution from Hot Rod clients. This module contains two deployable ServerTask implementations.

* manage-cache-task
* list-cache-task

Using the manage-cache-task, Hot Rod client can create or remove cache definitions dynamically. The list-cache-task provides the same functionality of  EmbeddedCacheManager.getCacheNames() for Hot Rod clients.

## Prerequisite

* Red Hat JBoss Data Grid 7.0.0 Beta Server

After starting JDG 7 servers, you must build the server task modules and deploy these on all JDG server of the target cluster.

~~~
$ maven clean package
$ ${JDG_HOME}/bin/ispn-cli.sh -c --controller=localhost:9990
[] deploy manage-cache-task/target/manage-cache-task.jar
[] deploy list-cache-task/target/list-cache-task.jar
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
