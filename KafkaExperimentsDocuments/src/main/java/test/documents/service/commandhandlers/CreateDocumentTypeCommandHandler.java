package test.documents.service.commandhandlers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import test.common.domain.AggregateNames;
import test.common.domain.DocumentTypeId;
import test.common.domain.EventPublisher;
import test.common.service.AggregateIdentifierGenerator;
import test.common.service.NonReturningCommandHandler;
import test.documents.domain.*;
import test.documents.domain.published.CreateDocumentTypeCommand;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author dmste
 */
@Component
public class CreateDocumentTypeCommandHandler implements NonReturningCommandHandler<CreateDocumentTypeCommand> {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private IDocumentTypesRepository documentTypesRepository;

    @Autowired
    private AggregateIdentifierGenerator identifierGenerator;

    @Override
    public Mono<Void> handle(CreateDocumentTypeCommand command) {
        return checkCommandArguments(command)
                .then(Mono.create(sink -> {
                    // Шаг 1: Генерируем идентификатор
                    DocumentTypeId documentTypeId =
                            identifierGenerator.generate(AggregateNames.DOCUMENT_TYPES);
                    sink.success(documentTypeId);
                }))
                .<DocumentType>handle((id, sink) -> {
                    // Шаг 2: Создаем агрегат
                    sink.next(createDocumentTypeWithId((DocumentTypeId) id, command));
                    sink.complete();
                    // Шаг 3: Сохраняем агрегат в репозиторий
                }).flatMap(documentType -> documentTypesRepository.save(documentType).doOnNext(success -> {
                    if (success) {
                        new RuntimeException(MessageFormat.format(
                                "Storing of domain " +
                                        "aggregate [{0}] with ID [{1}] did not " +
                                        "succeed!", AggregateNames.DOCUMENT_TYPES,
                                documentType.identifier().id()));
                    }
                }))
                .log()
                .then();
    }

    private DocumentType createDocumentTypeWithId(DocumentTypeId documentTypeId, CreateDocumentTypeCommand command) {
        DocumentTypeCode typeCode =
                new DocumentTypeCode(command.getCode(),
                        command.getClassifierCode());
        DocumentArtifactMetadata metadata =
                new DocumentArtifactMetadata(command.getName(),
                        command.getDescription());
        AttachmentPolicy attachmentPolicy =
                AttachmentPolicy.valueOf(command.getAttachmentPolicy());

        return new DocumentType(eventPublisher,
                documentTypeId,
                typeCode, metadata,
                attachmentPolicy);
    }

    @Override
    public Class<CreateDocumentTypeCommand> getCommandClass() {
        return CreateDocumentTypeCommand.class;
    }

    private Mono<Void> checkCommandArguments(CreateDocumentTypeCommand command) {
        if (StringUtils.isBlank(command.getCode())
                || StringUtils.isBlank(command.getName())
                || Objects.isNull(command.getAttachmentPolicy())) {
            return Mono.error(new RuntimeException(String.format("Not all " +
                            "required " +
                            "arguments has " +
                            "been filled for command [%s]. Check out and try again",
                    command.toString())));
        }
        AttachmentPolicy.valueOf(command.getAttachmentPolicy());
        return Mono.empty();
    }
}
