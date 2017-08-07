package com.oracle.cloud.jsr.jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "JAVAEE_JSR")
@XmlRootElement

public class JavaEESpecification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "JSR_ID")
    private Integer jsrId;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "NAME")
    private String name;
    @Column(name = "VERSION")
    private String version;

    public JavaEESpecification() {
    }

    public JavaEESpecification(Integer jsrId, String description, String name, String version) {
        this.jsrId = jsrId;
        this.description = description;
        this.name = name;
        this.version = version;
    }

    public JavaEESpecification(Integer jsrId, String name, String version) {
        this.jsrId = jsrId;
        this.name = name;
        this.version = version;
    }

    public Integer getJsrId() {
        return jsrId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "JavaEESpecification{" + "jsrId=" + jsrId + ", description=" + description + ", name=" + name + ", version=" + version + '}';
    }

}
