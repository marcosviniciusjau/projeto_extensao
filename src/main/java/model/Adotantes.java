/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "adotantes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adotantes.findAll", query = "SELECT a FROM Adotantes a"),
    @NamedQuery(name = "Adotantes.findByNome", query = "SELECT a FROM Adotantes a WHERE a.nome = :nome"),
    @NamedQuery(name = "Adotantes.findByDataNascimento", query = "SELECT a FROM Adotantes a WHERE a.dataNascimento = :dataNascimento"),
    @NamedQuery(name = "Adotantes.findByCpf", query = "SELECT a FROM Adotantes a WHERE a.cpf = :cpf"),
    @NamedQuery(name = "Adotantes.findByTelefone", query = "SELECT a FROM Adotantes a WHERE a.telefone = :telefone"),
    @NamedQuery(name = "Adotantes.findByEndereco", query = "SELECT a FROM Adotantes a WHERE a.endereco = :endereco"),
    @NamedQuery(name = "Adotantes.findByCep", query = "SELECT a FROM Adotantes a WHERE a.cep = :cep")})
public class Adotantes implements Serializable {

    public static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "nome")
    public String nome;
    @Basic(optional = false)
    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    public Date dataNascimento;
    @Id
    @Basic(optional = false)
    @Column(name = "cpf")
    public String cpf;
    @Basic(optional = false)
    @Column(name = "telefone")
    public String telefone;
    @Basic(optional = false)
    @Column(name = "endereco")
    public String endereco;
    @Basic(optional = false)
    @Column(name = "cep")
    public String cep;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "adotanteCpf")
    public List<Pets> petsList;

    public Adotantes() {
    }

    public Adotantes(String cpf) {
        this.cpf = cpf;
    }

    public Adotantes(String cpf, String nome, Date dataNascimento, String telefone, String endereco, String cep) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @XmlTransient
    public List<Pets> getPetsList() {
        return petsList;
    }

    public void setPetsList(List<Pets> petsList) {
        this.petsList = petsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpf != null ? cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adotantes)) {
            return false;
        }
        Adotantes other = (Adotantes) object;
        if ((this.cpf == null && other.cpf != null) || (this.cpf != null && !this.cpf.equals(other.cpf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Adotantes[ cpf=" + cpf + " ]";
    }
    
}
