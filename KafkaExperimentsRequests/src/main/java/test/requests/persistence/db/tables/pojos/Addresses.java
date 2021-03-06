/*
 * This file is generated by jOOQ.
 */
package test.requests.persistence.db.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * Адреса заявителей
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Addresses implements Serializable {

    private static final long serialVersionUID = -368938380;

    private Long   id;
    private String region;
    private String city;
    private String street;
    private String building;
    private String apartment;

    public Addresses() {}

    public Addresses(Addresses value) {
        this.id = value.id;
        this.region = value.region;
        this.city = value.city;
        this.street = value.street;
        this.building = value.building;
        this.apartment = value.apartment;
    }

    public Addresses(
        Long   id,
        String region,
        String city,
        String street,
        String building,
        String apartment
    ) {
        this.id = id;
        this.region = region;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return this.building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartment() {
        return this.apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Addresses (");

        sb.append(id);
        sb.append(", ").append(region);
        sb.append(", ").append(city);
        sb.append(", ").append(street);
        sb.append(", ").append(building);
        sb.append(", ").append(apartment);

        sb.append(")");
        return sb.toString();
    }
}
