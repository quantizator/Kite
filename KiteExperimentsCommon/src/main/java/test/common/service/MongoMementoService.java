package test.common.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import test.common.configuration.MongoConfiguration;
import test.common.domain.Memento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dmste
 */
@Component
@EnableConfigurationProperties(MongoConfiguration.class)
@Slf4j
public class MongoMementoService implements IAggregateMementoService {

    private static final String ID_FIELD_NAME = "_id";

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoConfiguration configuration;

    @Override
    public Flux<Memento> getMemento(String aggregateName,
                                    Map<FilterAttribute, Object> filterAttributes) {
        Bson filterBson = buildFilter(filterAttributes);
        Iterable<Document> documents =
                getCollection(aggregateName).find(filterBson);

        return Flux.fromIterable(documents).map(Memento::new);
    }

    /**
     * @param aggregateName
     * @param values
     */
    @Override
    public Mono<Boolean> storeMemento(String aggregateName,
                                      Map<FilterAttribute, Object> filterAttributes, Memento values) {

        Document document =
                new Document(values.getState());
//        document.put(ID_FIELD_NAME, ObjectId.get());
        MongoCollection<Document> documents = getOrCreateCollection(aggregateName,
                filterAttributes);
//        long matchCount = documents.countDocuments(filter);
//        if (matchCount > 0) {
//            UpdateResult result = documents.replaceOne(filter, document);
//            log.info("Modified {} documents", result.getModifiedCount());
//        } else {
//            documents.insertOne(document);
//            log.info("Inserted new document");
//        }
//        return Mono.just(true);
        Bson filter = buildFilter(filterAttributes);

        ReplaceOptions options = new ReplaceOptions();
        options.upsert(true);
        log.debug("Mongo update started");
        UpdateResult result = documents.replaceOne(filter, document, options);
        log.debug("Mongo update finished");
        boolean upserted = result.getUpsertedId() != null;
        boolean modified = result.getModifiedCount() > 0;

        if (upserted) {
            log.info("Inserted document with ID {}", result.getUpsertedId());
        } else if (modified) {
            log.info("Modified {} documents", result.getModifiedCount());
        }
        return Mono.just(upserted || modified);

    }

    @Override
    public Mono<Void> deleteMemento(String aggregateName, Map<FilterAttribute, Object> filterAttributes) {
        MongoCollection<Document> collection = getCollection(aggregateName);
        if (collection == null || collection.countDocuments() == 0) {
            return Mono.empty();
        }

        Bson filter = buildFilter(filterAttributes);

        return Mono.create(sink -> {
            collection.deleteMany(filter);
            sink.success();
        });
    }

    private MongoCollection<Document> getCollection(String aggregateName) {
        return getOrCreateCollection(aggregateName, null);
    }

    private MongoCollection<Document> getOrCreateCollection(String aggregateName, Map<FilterAttribute, Object> attributes) {
        MongoDatabase database =
                mongoClient.getDatabase(configuration.getDatabase().getName());
        MongoCollection<Document> collection =
                database.getCollection(aggregateName);

        if (collection == null && !MapUtils.isEmpty(attributes)) {
            database.createCollection(aggregateName);
            final MongoCollection newCollection = database.getCollection(aggregateName);

            attributes.keySet().stream().filter(FilterAttribute::isUnique)
                    .forEach(a -> newCollection.createIndex(Indexes.text(a.getAttributeName()),
                            new IndexOptions().unique(true)));

            collection = newCollection;
        }
        return collection;
    }

    private Bson buildFilter(Map<FilterAttribute, Object> attributes) {
        List<Bson> subfilters = new ArrayList<>();
        attributes.forEach((attribute, value) -> {
            String attributeName = attribute.getAttributeName();
            Object filterValue = attribute.equals(ID_FIELD_NAME) ?
                    new ObjectId((String) value) : value;
            subfilters.add(Filters.eq(attributeName, filterValue));
        });
        Bson result = null;
        for (Bson filter : subfilters) {
            if (result == null) {
                result = filter;
            } else {
                result = Filters.and(result, filter);
            }
        }
        return result;
    }
}
