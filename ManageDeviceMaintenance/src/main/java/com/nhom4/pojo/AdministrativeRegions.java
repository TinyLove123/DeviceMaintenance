/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom4.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "administrative_regions")
@NamedQueries({
    @NamedQuery(name = "AdministrativeRegions.findAll", query = "SELECT a FROM AdministrativeRegions a"),
    @NamedQuery(name = "AdministrativeRegions.findById", query = "SELECT a FROM AdministrativeRegions a WHERE a.id = :id"),
    @NamedQuery(name = "AdministrativeRegions.findByName", query = "SELECT a FROM AdministrativeRegions a WHERE a.name = :name"),
    @NamedQuery(name = "AdministrativeRegions.findByNameEn", query = "SELECT a FROM AdministrativeRegions a WHERE a.nameEn = :nameEn"),
    @NamedQuery(name = "AdministrativeRegions.findByCodeName", query = "SELECT a FROM AdministrativeRegions a WHERE a.codeName = :codeName"),
    @NamedQuery(name = "AdministrativeRegions.findByCodeNameEn", query = "SELECT a FROM AdministrativeRegions a WHERE a.codeNameEn = :codeNameEn")})
public class AdministrativeRegions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name_en")
    private String nameEn;
    @Size(max = 255)
    @Column(name = "code_name")
    private String codeName;
    @Size(max = 255)
    @Column(name = "code_name_en")
    private String codeNameEn;

    public AdministrativeRegions() {
    }

    public AdministrativeRegions(Integer id) {
        this.id = id;
    }

    public AdministrativeRegions(Integer id, String name, String nameEn) {
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdministrativeRegions)) {
            return false;
        }
        AdministrativeRegions other = (AdministrativeRegions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhom4.pojo.AdministrativeRegions[ id=" + id + " ]";
    }
    
}
