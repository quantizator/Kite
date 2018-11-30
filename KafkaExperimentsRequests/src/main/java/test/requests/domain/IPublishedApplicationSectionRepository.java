package test.requests.domain;

import reactor.core.publisher.Flux;
import test.requests.domain.published.PublishedApplicationSection;

/**
 * @author dmste
 */
public interface IPublishedApplicationSectionRepository {
    Flux<PublishedApplicationSection> getApplicationSections(String applicationNumber);
}
