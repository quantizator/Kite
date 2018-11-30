package test.documents.domain;

import org.springframework.stereotype.Repository;
import test.common.domain.DocumentId;
import test.common.domain.IAggregateRepository;

@Repository
public interface IDocumentsRepository extends IAggregateRepository<Document,
        DocumentId> {
}
