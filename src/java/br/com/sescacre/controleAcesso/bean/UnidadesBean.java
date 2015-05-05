/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sescacre.controleAcesso.bean;

import br.com.sescacre.controleAcesso.dao.UnidadesDAO;
import br.com.sescacre.controleAcesso.entidades.Unidades;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class UnidadesBean implements Serializable {

    private Unidades unidade = new Unidades();
    private List<Unidades> unidades = new ArrayList<Unidades>();

    @PostConstruct
    public void construct() {
        unidades = new UnidadesDAO().ListaTodos();
    }

    public Unidades getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidades unidade) {
        this.unidade = unidade;
    }

    public List<Unidades> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidades> unidades) {
        this.unidades = unidades;
    }

    public String salvar() {
        UnidadesDAO unidadeD = new UnidadesDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            unidade.setDeletado(false);
            if (unidade.getIdUnidade() == null) {
                unidadeD.salvar(unidade);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "A setor '" + unidade.getNome() + "' foi inserida com sucesso.", null));
            } else {
                unidadeD.alterar(unidade);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "A unidade '" + unidade.getNome() + "' foi alterada com sucesso.", null));
                RequestContext.getCurrentInstance().execute("inserir.hide()");
            }
            limpar();
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Já existe uma Unidade cadastrada com esse nome!", null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Ocorreu um erro ao inserir a unidade '" + unidade.getNome() + "'", null));
        }
        construct();
        return null;
    }

    public String excluir() {
        UnidadesDAO unidadeD = new UnidadesDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            unidade.setDeletado(true);
            //unidadeD.excluir(unidade);
            unidadeD.alterar(unidade);
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "A unidade '" + unidade.getNome() + "' foi excluída com sucesso.", null));
            limpar();
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Ocorreu um erro ao excluir a unidade '" + unidade.getNome() + "'", null));
        }
        construct();
        return null;
    }

    public String limpar() {
        unidade = new Unidades();
        return null;
    }
}
