/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sescacre.controleAcesso.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Rennan Francisco
 */
@Entity
public class Registros implements Serializable {
    
    @Id
    @GeneratedValue
    private Long idRegistro;
    @Column(nullable = false, length = 13)
    private String cateira;
    @Column(length = 60)
    private String nome;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Categorias categoria = new Categorias();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Unidades unidade = new Unidades();
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHora;

    public Long getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Long idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getCateira() {
        return cateira;
    }

    public void setCateira(String cateira) {
        this.cateira = cateira;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public Unidades getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidades unidade) {
        this.unidade = unidade;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idRegistro);
        hash = 79 * hash + Objects.hashCode(this.cateira);
        hash = 79 * hash + Objects.hashCode(this.nome);
        hash = 79 * hash + Objects.hashCode(this.categoria);
        hash = 79 * hash + Objects.hashCode(this.unidade);
        hash = 79 * hash + Objects.hashCode(this.dataHora);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Registros other = (Registros) obj;
        if (!Objects.equals(this.idRegistro, other.idRegistro)) {
            return false;
        }
        if (!Objects.equals(this.cateira, other.cateira)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.unidade, other.unidade)) {
            return false;
        }
        if (!Objects.equals(this.dataHora, other.dataHora)) {
            return false;
        }
        return true;
    }

}
