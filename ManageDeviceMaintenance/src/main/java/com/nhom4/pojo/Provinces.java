/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "provinces")
@NamedQueries({
    @NamedQuery(name = "Provinces.findAll", query = "SELECT p FROM Provinces p"),
    @NamedQuery(name = "Provinces.findByCode", query = "SELECT p FROM Provinces p WHERE p.code = :code"),
    @NamedQuery(name = "Provinces.findByName", query = "SELECT p FROM Provinces p WHERE p.name = :name"),
    @NamedQuery(name = "Provinces.findByNameEn", query = "SELECT p FROM Provinces p WHERE p.nameEn = :nameEn"),
    @NamedQuery(name = "Provinces.findByFullName", query = "SELECT p FROM Provinces p WHERE p.fullName = :fullName"),
    @NamedQuery(name = "Provinces.findByFullNameEn", query = "SELECT p FROM Provinces p WHERE p.fullNameEn = :fullNameEn"),
    @NamedQuery(name = "Provinces.findByCodeName", query = "SELECT p FROM Provinces p WHERE p.codeName = :codeName")})
public class Provinces implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "name_en")
    private String nameEn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "full_name")
    private String fullName;
    @Size(max = 255)
    @Column(name = "full_name_en")
    private String fullNameEn;
    @Size(max = 255)
    @Column(name = "code_name")
    private String codeName;
    @JoinColumn(name = "administrative_unit_id", referencedColumnName = "id")
    @ManyToOne
    private AdministrativeUnits administrativeUnitId;
    @OneToMany(mappedBy = "provinceCode")
    private Set<Wards> wardsSet;

    public Provinces() {
    }

    public Provinces(String code) {
        this.code = code;
    }

    public Provinces(String code, String name, String fullName) {
        this.code = code;
        this.name = name;
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
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

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public AdministrativeUnits getAdministrativeUnitId() {
        return administrativeUnitId;
    }

    public void setAdministrativeUnitId(AdministrativeUnits administrativeUnitId) {
        this.administrativeUnitId = administrativeUnitId;
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
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provinces)) {
            return false;
        }
        Provinces other = (Provinces) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.Provinces[ code=" + code + " ]";
    }
    
}
