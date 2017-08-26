package io.github.mockenize.config;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.StreamSupport;

@Slf4j
@Configuration
public class EmbeddedMongoDbConfig {

    private static final String BIND_IP = "localhost";

    private static final int PORT = 27017;

    private static final String DB_NAME = "mockenize";

    private MongodStarter mongodStarter = MongodStarter.getDefaultInstance();

    private MongodProcess mongodProcess;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void setup() throws IOException {
        log.info("Starting mongodb on {}:{}", BIND_IP, PORT);

        IMongodConfig mongodConfig = null;
        MongodConfigBuilder builder = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(BIND_IP, PORT, Network.localhostIsIPv6()));

        if (environment.acceptsProfiles("test")) {
            mongodConfig = builder.build();
        } else {
            Storage replication = new Storage("./data", null, 0);
            mongodConfig = builder.replication(replication).build();
        }

        this.mongodProcess = mongodStarter.prepare(mongodConfig).start();
    }

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(BIND_IP, PORT);
    }

    @Bean
    @Autowired
    public MongoDbFactory mongoDbFactory(MongoClient mongoClient) {
        return new SimpleMongoDbFactory(mongoClient, DB_NAME);
    }

    @Bean
    @Autowired
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @PreDestroy
    public void shutdownMongoDb() {
        if (mongodProcess != null) {
            mongodProcess.stop();
        }
    }
}
