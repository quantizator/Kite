package test.requests.domain.published;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;

/**
 * @author dmste
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonClassDescription("Секция заявления")
public class PublishedApplicationSection {

    @JsonPropertyDescription("Метаданные секции")
    @JsonProperty(required = true)
    private PublishedApplicationMetadata sectionMetadata;

    @JsonPropertyDescription("Код документа, к которому относится секция")
    private String documentCode;

    @JsonPropertyDescription("Список полей документа")
    private List<PublishedApplicationField> documentFields;
}
