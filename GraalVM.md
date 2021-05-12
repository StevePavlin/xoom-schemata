# xoom-schemata - GraalVM Support

The VLINGO XOOM Schema Registry.

## Getting started

Prerequisites:
* Java JDK 8 or greater
* Maven
* [GraalVM 21.1.0 Java 8/11](https://www.graalvm.org/docs/getting-started/)

## Maven build
```bash
mvn clean package -Pfrontend -Pnative-image
```
- Generate native image resources Configs
```bash
java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar target/xoom-schemata-1.7.8-SNAPSHOT-jar-with-dependencies.jar
```

## Native Image Maven Plugin & GraalVM SDK
```
<properties>
    ...
    <exec.mainClass>io.vlingo.xoom.schemata.XoomInitializer</exec.mainClass>
    <graalvm.version>21.1.0</graalvm.version>
    ...
</properties>
<dependencies>
    ...
    <dependency>
      <groupId>org.graalvm.sdk</groupId>
      <artifactId>graal-sdk</artifactId>
      <version>${graalvm.version}</version>
      <scope>provided</scope>
    </dependency>
    ...
</dependencies>

```
```
<profiles>
    ...
    <profile>
      <id>native-image</id>
      <build>
        <plugins>
          <plugin>
            <groupId>org.graalvm.nativeimage</groupId>
            <artifactId>native-image-maven-plugin</artifactId>
            <version>${graalvm.version}</version>
            <executions>
              <execution>
                <goals>
                  <goal>native-image</goal>
                </goals>
                <phase>package</phase>
              </execution>
            </executions>
            <configuration>
              <imageName>${project.name}</imageName>
              <mainClass>${exec.mainClass}</mainClass>
              <buildArgs>
                --no-fallback --no-server --enable-url-protocols=http -H:+AllowIncompleteClasspath
                -H:ReflectionConfigurationFiles=classes/META-INF/native-image/reflect-config.json
                -H:ResourceConfigurationFiles=classes/META-INF/native-image/resource-config.json
                -H:SerializationConfigurationFiles=classes/META-INF/native-image/serialization-config.json
                --initialize-at-run-time=io.netty
                --initialize-at-run-time=io.vlingo.xoom.common.identity.IdentityGeneratorType
                --report-unsupported-elements-at-runtime
                --allow-incomplete-classpath
              </buildArgs>
            </configuration>
          </plugin>
        </plugins>
      </build>
    </profile>
    ...
</profiles>
```
- To build the native image run:
```bash
mvn clean package -Pfrontend -Pnative-image
```
```bash
./target/xoom-schemata
```
- On native image runtime, an exception is always thrown, issue described here: [ISSUE](https://github.com/RuedigerMoeller/fast-serialization/issues/313)
- WIP running frontend...