/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "administrative_units")
@NamedQueries({
    @NamedQuery(name = "AdministrativeUnits.findAll", query = "SELECT a FROM AdministrativeUnits a"),
    @NamedQuery(name = "AdministrativeUnits.findById", query = "SELECT a FROM AdministrativeUnits a WHERE a.id = :id"),
    @NamedQuery(name = "AdministrativeUnits.findByFullName", query = "SELECT a FROM AdministrativeUnits a WHERE a.fullName = :fullName"),
    @NamedQuery(name = "AdministrativeUnits.findByFullNameEn", query = "SELECT a FROM AdministrativeUnits a WHERE a.fullNameEn = :fullNameEn"),
    @NamedQuery(name = "AdministrativeUnits.findByShortName", query = "SELECT a FROM AdministrativeUnits a WHERE a.shortName = :shortName"),
    @NamedQuery(name = "AdministrativeUnits.findByShortNameEn", query = "SELECT a FROM AdministrativeUnits a WHERE a.shortNameEn = :shortNameEn"),
    @NamedQuery(name = "AdministrativeUnits.findByCodeName", query = "SELECT a FROM AdministrativeUnits a WHERE a.codeName = :codeName"),
    @NamedQuery(name = "AdministrativeUnits.findByCodeNameEn", query = "SELECT a FROM AdministrativeUnits a WHERE a.codeNameEn = :codeNameEn")})
public class AdministrativeUnits implements Serializable {

    @Size(max = 255)
    @Column(name = "full_name")
    private String fullName;
    @Size(max = 255)
    @Column(name = "full_name_en")
    private String fullNameEn;
    @Size(max = 255)
    @Column(name = "short_name")
    private String shortName;
    @Size(max = 255)
    @Column(name = "short_name_en")
    private String shortNameEn;
    @Size(max = 255)
    @Column(name = "code_name")
    private String codeName;
    @Size(max = 255)
    @Column(name = "code_name_en")
    private String codeNameEn;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JsonIgnore
    @OneToMany(mappedBy = "administrativeUnitId")
    private Set<Provinces> provincesSet;
    @JsonIgnore
    @OneToMany(mappedBy = "administrativeUnitId")
    private Set<Wards> wardsSet;

    public AdministrativeUnits() {
    }

    public AdministrativeUnits(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameEn() {
        return fullNameEn;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortNameEn() {
        return shortNameEn;
    }

    public void setShortNameEn(String shortNameEn) {
        this.shortNameEn = shortNameEn;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeNameEn() {
        return codeNameEn;
    }

    public void setCodeNameEn(String codeNameEn) {
        this.codeNameEn = codeNameEn;
    }

    public Set<Provinces> getProvincesSet() {
        return provincesSet;
    }

    public void setProvincesSet(Set<Provinces> provincesSet) {
        this.provincesSet = provincesSet;
    }

    public Set<Wards> getWardsSet() {
        return wardsSet;
    }

    public void setWardsSet(Set<Wards> wardsSet) {
        this.wardsSet = wardsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdministrativeUnits)) {
            return false;
        }
        AdministrativeUnits other = (AdministrativeUnits) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.AdministrativeUnits[ id=" + id + " ]";
    }

   
    

    
    
    
}
