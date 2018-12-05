package test.requests.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import test.requests.domain.published.HistorySearchFilter;
import test.requests.domain.published.PublishedApplicationHistory;
import test.requests.domain.published.PublishedApplicationHistoryEntry;

/**
 * @author dmste
 */
public interface IPublishedApplicationHistoryRepository {

    Mono<PublishedApplicationHistory> getApplicationHistory(String applicationNumber);

    Flux<PublishedApplicationHistoryEntry> searchEntries(HistorySearchFilter searchFilter);
}
