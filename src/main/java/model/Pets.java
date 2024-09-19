/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "pets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pets.findAll", query = "SELECT p FROM Pets p"),
    @NamedQuery(name = "Pets.findByDataCastracao", query = "SELECT p FROM Pets p WHERE p.dataCastracao = :dataCastracao"),
    @NamedQuery(name = "Pets.findByDataAdocao", query = "SELECT p FROM Pets p WHERE p.dataAdocao = :dataAdocao"),
    @NamedQuery(name = "Pets.findByCodigoMicrochip", query = "SELECT p FROM Pets p WHERE p.codigoMicrochip = :codigoMicrochip"),
    @NamedQuery(name = "Pets.findByPeso", query = "SELECT p FROM Pets p WHERE p.peso = :peso")})
public class Pets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "data_castracao")
    @Temporal(TemporalType.DATE)
    public Date dataCastracao;
    @Column(name = "data_adocao")
    @Temporal(TemporalType.DATE)
    public Date dataAdocao;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo_microchip")
    public Integer codigoMicrochip;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "peso")
    public BigDecimal peso;
    @JoinColumn(name = "adotante_cpf", referencedColumnName = "cpf")
    @ManyToOne
    public Adotante adotanteCpf;

    public Pets() {
    }

    public Pets(Integer codigoMicrochip) {
        this.codigoMicrochip = codigoMicrochip;
    }

    public Pets(Integer codigoMicrochip, Date dataCastracao, BigDecimal peso) {
        this.codigoMicrochip = codigoMicrochip;
        this.dataCastracao = dataCastracao;
        this.peso = peso;
    }

    public Date getDataCastracao() {
        return dataCastracao;
    }

    public void setDataCastracao(Date dataCastracao) {
        this.dataCastracao = dataCastracao;
    }

    public Date getDataAdocao() {
        return dataAdocao;
    }

    public void setDataAdocao(Date dataAdocao) {
        this.dataAdocao = dataAdocao;
    }

    public Integer getCodigoMicrochip() {
        return codigoMicrochip;
    }

    public void setCodigoMicrochip(Integer codigoMicrochip) {
        this.codigoMicrochip = codigoMicrochip;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Adotante getAdotanteCpf() {
        return adotanteCpf;
    }

    public String getAdotanteCpfDiferente() {
        return adotanteCpf != null ? adotanteCpf.getCpf() : null;
    }

    public void setAdotanteCpf(String cpf) {
        this.adotanteCpf = new Adotante(cpf);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoMicrochip != null ? codigoMicrochip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pets)) {
            return false;
        }
        Pets other = (Pets) object;
        if ((this.codigoMicrochip == null && other.codigoMicrochip != null) || (this.codigoMicrochip != null && !this.codigoMicrochip.equals(other.codigoMicrochip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pets[ codigoMicrochip=" + codigoMicrochip + " ]";
    }
    
}
