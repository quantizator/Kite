package test.requests.domain.published;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dmste
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
//@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonClassDescription("Заявление на получение услуги заявителем " +
        "(представление для просмотра истории)")
public class PublishedApplicationHistory extends PublishedApplication {


    @JsonPropertyDescription("История работы с заявлением")
    @JsonProperty(required = true)
    private List<PublishedApplicationHistoryEntry> historyEntries;

    public PublishedApplicationHistory(String number, PublishedApplicationStatus status, String applicationType, String applicantId, String creatorId, String updaterId) {
        super(number, status, applicationType, applicantId, creatorId, updaterId);
    }
}
