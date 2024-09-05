/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "PETS")
@NamedQueries({
    @NamedQuery(name = "Pets.findAll", query = "SELECT p FROM Pets p"),
    @NamedQuery(name = "Pets.findById", query = "SELECT p FROM Pets p WHERE p.id = :id"),
    @NamedQuery(name = "Pets.findByAdoptionDate", query = "SELECT p FROM Pets p WHERE p.adoptionDate = :adoptionDate"),
    @NamedQuery(name = "Pets.findByName", query = "SELECT p FROM Pets p WHERE p.name = :name"),
    @NamedQuery(name = "Pets.findByCastrateDate", query = "SELECT p FROM Pets p WHERE p.castrateDate = :castrateDate")})
public class Pets implements Serializable {

    public static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    public Integer id;
    @Column(name = "ADOPTION_DATE")
    @Temporal(TemporalType.DATE)
    public Date adoptionDate;
    @Column(name = "NAME")
    public String name;
    @Column(name = "CASTRATE_DATE")
    @Temporal(TemporalType.DATE)
    public Date castrateDate;

    public Pets() {
    }

    public Pets(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(Date adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCastrateDate() {
        return castrateDate;
    }

    public void setCastrateDate(Date castrateDate) {
        this.castrateDate = castrateDate;
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
        if (!(object instanceof Pets)) {
            return false;
        }
        Pets other = (Pets) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pets[ id=" + id + " ]";
    }
    
}
