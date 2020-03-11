# log4j2-prop-lookup

A log4j2 plugin to enable properties lookup from a configurable list of property files.

The file can be either `.yaml` or .`.properties` file. If a file has a .yaml extension the properties will be loaded using the yaml property loader.
Any other extensions will use the property file properties loader.

## Installation

To enable the plugin, include the following dependency in the `pom.xml`

```xml
<dependency>
    <groupId>com.mariocairone</groupId>
    <artifactId>log4j2-prop-lookup</artifactId>
    <version>1.0.0</version>
</dependency> 
```

## Configuration

The plugin is configured using the following properties:

| name       | values | description                                      | required |
| ---------- | ------ | ------------------------------------------------ | -------- |
| prop.files | String | A comma separated list of file paths             | true     |
| prop.dir   | String | The base directory for all the files in the list | false    |


---
**NOTE**

the `filePaths` must be relative to the classpath root. 

If the optional `prop.dir` properties is present its value will be used as prefix for each `filePath`.

The `com.mariocairone.log4j2` must be declared in the log4j2.xml configuration file. 

---

### Example

A common scenario is to customize the log4j2 configuration with different properties for different environments.
We can have an Http appender sending the logs to a sandbox logstash instance for non production deployments and to a different instance for the production deployment.
Or we want to customize our layout with properties that are specific for an environment.

to do that I have to create a properties file for each environment:

#### sandbox.yaml
```yaml
environment:
  name: "Sandbox"
logstash:
  url: "http://mysandbox.host:8080"
```
#### production.yaml
```yaml
environment:
  name: "Production"
logstash:
  url: "http://myproduction.host:8080"
```

#### log4j.xml

I can then use a system properties to select the properties file based on the environment.

```xml
<Configuration packages="com.mariocairone.log4j2">
    <Properties>
     <Property name="prop.files">${sys:mule.env}.yaml</Property>
    </Properties>
  ...
    <Http name="http" url="${prop:logstash.url}" >
        <Property name="Content-Type" value="application/json" />
        <JsonLayout complete="true" properties="true" >
          <KeyValuePair key="environment" value="${prop:environment.name}"/>
        </JsonLayout>
    </Http> 
```
