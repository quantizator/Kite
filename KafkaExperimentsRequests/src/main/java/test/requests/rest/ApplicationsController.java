package test.requests.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import test.requests.domain.IPublishedApplicationsRepository;
import test.requests.domain.published.PublishedApplication;

/**
 * @author dmste
 */
@RestController
@RequestMapping("/applications")
public class ApplicationsController {

    @Autowired
    private IPublishedApplicationsRepository applicationRepository;

    /**
     *
     */
    @GetMapping("/byApplicant/{applicantId}")
    public Flux<PublishedApplication> searchByApplicant(@PathVariable String applicantId) {
        return applicationRepository.searchByApplicant(applicantId);

    }

    @GetMapping("/byOperator/{operatorId}")
    public Flux<PublishedApplication> searchByOperator(@PathVariable String operatorId) {
        return applicationRepository.searchByOperator(operatorId);
    }




}
