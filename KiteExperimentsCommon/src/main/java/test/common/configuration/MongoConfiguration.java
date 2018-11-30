package test.common.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author dmste
 */
@Configuration
@ConfigurationProperties(prefix = "kite.mongo")
@Getter @Setter
@Validated
public class MongoConfiguration {

    @NotEmpty
    private String dbAddress;

    @NotNull
    private Database database;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String authenticationDb;

    @Bean
    public MongoClient mongoClient() {

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(MongoCredential.createCredential(username, authenticationDb,
                        password.toCharArray()))
                .applyConnectionString(new ConnectionString(dbAddress))
                .build();

        return MongoClients.create(settings);
    }

    @Getter @Setter
    public static class Database {
        @NotEmpty
        private String name;
    }
}
