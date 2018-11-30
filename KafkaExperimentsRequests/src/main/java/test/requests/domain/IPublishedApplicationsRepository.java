package test.requests.domain;

import reactor.core.publisher.Flux;
import test.requests.domain.published.PublishedApplication;

/**
 * @author dmste
 */
public interface IPublishedApplicationsRepository {

    Flux<PublishedApplication> searchByApplicant(String applicantId);

    Flux<PublishedApplication> searchByOperator(String operatorId);
}
