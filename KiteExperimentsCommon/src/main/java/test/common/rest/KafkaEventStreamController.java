package test.common.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import test.common.service.AggregateEventsHolder;
import test.common.service.EventStoreConstants;
import test.common.service.EventsHolderQueryServiceKafka;

@RestController
@RequestMapping(EventStoreConstants.KAFKA_REMOTE_STATE_STORE_PATH)
public class KafkaEventStreamController {

    @Autowired
    private EventsHolderQueryServiceKafka queryServiceKafka;

    @GetMapping("/{storeName}/{aggregateId}")
    public Mono<AggregateEventsHolder> getAggregateEventsStream(String storeName, String aggregateId) {
        return queryServiceKafka.getEventsHolderForAggregate(aggregateId, storeName);
    }
}
