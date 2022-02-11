# Spring Boot - native
[Docs](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#getting-started)
```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <configuration>
    <image>
      <builder>paketobuildpacks/builder:tiny</builder>
      <env>
        <BP_NATIVE_IMAGE>true</BP_NATIVE_IMAGE>
      </env>
    </image>
  </configuration>
</plugin>
```

```shell
# build native image
mvn spring-boot:build-image
```

```shell
# image start
docker run --rm -p 8080:8080 spring-native:0.0.1-SNAPSHOT
```

## Errors
### Fallback
If you see the error:
```text
Error: No bin/java and no environment variable JAVA_HOME
```
that means you native image could not been build and the builder builds the image with fallback to JVM, but because our image don't have java,
we will get the error. You can add `<BP_NATIVE_IMAGE_BUILD_ARGUMENTS>--no-fallback</BP_NATIVE_IMAGE_BUILD_ARGUMENTS>` to the
plugin to enforce a build error:

```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <configuration>
    <image>
      <builder>paketobuildpacks/builder:tiny</builder>
      <env>
        <BP_NATIVE_IMAGE>true</BP_NATIVE_IMAGE>
        <BP_NATIVE_IMAGE_BUILD_ARGUMENTS>--no-fallback</BP_NATIVE_IMAGE_BUILD_ARGUMENTS>
      </env>
    </image>
  </configuration>
</plugin>
```

Then you will see the full stacktrace

