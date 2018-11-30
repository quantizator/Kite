package test.requests.domain;

import reactor.core.publisher.Flux;
import test.requests.domain.published.HistorySearchFilter;
import test.requests.domain.published.PublishedApplicationHistoryEntry;

/**
 * @author dmste
 */
public interface IPublishedApplicationHistoryRepository {
    Flux<PublishedApplicationHistoryEntry> getApplicationHistory(String applicationNumber);

    Flux<PublishedApplicationHistoryEntry> search(HistorySearchFilter searchFilter);
}
