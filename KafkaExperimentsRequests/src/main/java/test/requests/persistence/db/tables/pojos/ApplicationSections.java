/*
 * This file is generated by jOOQ.
 */
package test.requests.persistence.db.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * Секции заявлений
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ApplicationSections implements Serializable {

    private static final long serialVersionUID = -991308470;

    private Long   id;
    private Long   applicationId;
    private String name;
    private String description;
    private String documentCode;
    private String documentDesignation;
    private Long   documentId;

    public ApplicationSections() {}

    public ApplicationSections(ApplicationSections value) {
        this.id = value.id;
        this.applicationId = value.applicationId;
        this.name = value.name;
        this.description = value.description;
        this.documentCode = value.documentCode;
        this.documentDesignation = value.documentDesignation;
        this.documentId = value.documentId;
    }

    public ApplicationSections(
        Long   id,
        Long   applicationId,
        String name,
        String description,
        String documentCode,
        String documentDesignation,
        Long   documentId
    ) {
        this.id = id;
        this.applicationId = applicationId;
        this.name = name;
        this.description = description;
        this.documentCode = documentCode;
        this.documentDesignation = documentDesignation;
        this.documentId = documentId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentCode() {
        return this.documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentDesignation() {
        return this.documentDesignation;
    }

    public void setDocumentDesignation(String documentDesignation) {
        this.documentDesignation = documentDesignation;
    }

    public Long getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ApplicationSections (");

        sb.append(id);
        sb.append(", ").append(applicationId);
        sb.append(", ").append(name);
        sb.append(", ").append(description);
        sb.append(", ").append(documentCode);
        sb.append(", ").append(documentDesignation);
        sb.append(", ").append(documentId);

        sb.append(")");
        return sb.toString();
    }
}
