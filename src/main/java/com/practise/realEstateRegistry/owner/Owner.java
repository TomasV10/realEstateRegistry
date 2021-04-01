package com.practise.realEstateRegistry.owner;

import com.practise.realEstateRegistry.property.Property;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @OneToMany(mappedBy = "owner", fetch = LAZY)
    private List<Property> properties = new ArrayList<>();

    public Owner() {
    }

    public Owner(Long id, String fullName, List<Property> properties) {
        this.id = id;
        this.fullName = fullName;
        this.properties = properties;
    }

    public Owner(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public OwnerDto toDto(){
        return new OwnerDto(id, fullName);
    }
}
