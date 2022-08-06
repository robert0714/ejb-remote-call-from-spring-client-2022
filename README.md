# Demonstrates remote EJB calls across Spring-boot between Wildfly 26 

## Reference Documentation
For further reference, please consider the following sections:

* [Demonstrates remote EJB calls and transaction propagation](https://github.com/wildfly/quickstart/tree/26.1.1.Final/ejb-txn-remote-call)  
     * [Migrate Legacy Security to Elytron Security](https://docs.wildfly.org/26/WildFly_Elytron_Security.html#Migrate_Legacy_Security_to_Elytron_Security)
     * [Invoking EJBs over HTTP](https://wildfly-security.github.io/wildfly-elytron/blog/ejb-over-http/)
     * [Advanced EJB: Securing EJBs using a FileSystem realm and invoking them from a remote client using a credential store](https://wildfly-security.github.io/wildfly-elytron/blog/advanced-ejb-security/)
     * [Getting started with EJBs and Elytron Part 1: Securing EJBs and invoking them from remote clients](https://developer.jboss.org/people/fjuma/blog/2017/09/08/getting-started-with-ejbs-and-elytron-part-1)
     * [Getting started with EJBs and Elytron Part 2: EJB invocations from remote servers](https://developer.jboss.org/people/fjuma/blog/2017/09/08/getting-started-with-ejbs-and-elytron-part-2)
     * [WF 26 + Elytron + OIDC + remote EJB but no WAR in EAR](https://groups.google.com/g/wildfly/c/7z_Xv2mbgRo)
     * [Wildfly client Doc](https://docs.wildfly.org/26/Client_Guide.html)

## Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Serverside (Wildfly 26) Settings
[source codes url](https://github.com/wildfly/quickstart/tree/26.1.1.Final/ejb-txn-remote-call/server)
#### Add the Authorized Application User
This quickstart uses secured application interfaces and requires that you create the following application user to access the running application.

| UserName        | Realm            | Password          | Roles  |
|-----------------|------------------|-------------------|--------|
| quickstartUser  | ApplicationRealm | ApplicationRealm  |        |

To add the application user, open a terminal and type the following command:

```shell
$ {jbossHomeName}/bin/add-user.sh -a -u 'quickstartUser' -p 'quickstartPwd1!' 
```

**⚠ NOTICE:** For Windows, use the ``{jbossHomeName}\bin\add-user.bat`` script.

* quickstart/ejb-txn-remote-call/server/configuration
   * application-roles.properties 
    
	```properties
	# For OpenShift: this properties file that will be copied by WildFly s2i scripts under $JBOSS_HOME/configuration directory
    quickstartUser=quickstartUser
	```
	
    * application-users.properties
   
  	```properties
	 # For OpenShift: this properties file that will be copied by WildFly s2i scripts under $JBOSS_HOME/configuration directory
     quickstartUser=c2d60ae3c894489fa59196c192e351ca
	```
 * If you use the tradtional Wildfly 26 standalone version.  Copy to ``{jbossHomeName}/standalone/configuration/`` .
 * If you use wildfy bootable jar , refer [the document: Configuring the server during packaging](https://docs.wildfly.org/bootablejar/#wildfly_jar_configuring_build).

#### Examining the Quickstart
  ```
  java -Dremote.server.host=192.168.18.30   -Dremote.server.port=8080  -Dremote.server.username=quickstartUser   -Dremote.server.password=quickstartPwd1!  -Dclient.mappings.socket.binding=server  -jar ejb-remote-call-from-spring-client-0.0.1-SNAPSHOT.jar
  ```
  
**⚠ NOTICE:** The endpoints return data in JSON format. You can use ``curl`` for invocation and ``jq`` command to format the results. For example: ``curl -s http://localhost:8080/client/direct-stateless-http | jq . ``

You can invoke the endpoint ``server/commits`` at  wildfly 26 server (i.e. http://localhost:8180/server/commits ). 

The HTTP invocations return the hostnames of the contacted servers.

# Reference CLI
* official sample cli : https://github.com/wildfly-extras/wildfly-jar-maven-plugin/tree/7.0.1.Final/examples/scripts
* sample like add user , socket 
  ```
  -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0  -Dhttp=true
  
  # Create a user store
  /subsystem=elytron/filesystem-realm=RemotePasswordRealm:add(path=fs-realm-users, relative-to=jboss.server.config.dir)

  # Add user quickstartUser identity and password quickstartPwd1!
  /subsystem=elytron/filesystem-realm=RemotePasswordRealm:add-identity(identity=quickstartUser)
  /subsystem=elytron/filesystem-realm=RemotePasswordRealm:set-password(clear={password=quickstartPwd1!},identity=quickstartUser)
  /subsystem=elytron/filesystem-realm=RemotePasswordRealm:add-identity-attribute(identity=quickstartUser, name=Roles, value=[guest])
  /subsystem=elytron/simple-role-decoder=from-roles-attribute:add(attribute=Roles)

  /socket-binding-group=standard-sockets/socket-binding=http:list-add(name=client-mappings, value={destination-address="192.168.18.13"})
  /socket-binding-group=standard-sockets/socket-binding=http:list-add(name=client-mappings, value={destination-address="192.168.50.92"})
  ```
