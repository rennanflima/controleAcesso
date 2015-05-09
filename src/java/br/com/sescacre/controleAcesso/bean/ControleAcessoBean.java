/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sescacre.controleAcesso.bean;

import br.com.sescacre.controleAcesso.dao.CategoriasDao;
import br.com.sescacre.controleAcesso.dao.ClientesDao;
import br.com.sescacre.controleAcesso.dao.RegistroDao;
import br.com.sescacre.controleAcesso.dao.UnidadesDAO;
import br.com.sescacre.controleAcesso.entidades.Categorias;
import br.com.sescacre.controleAcesso.entidades.Clientes;
import br.com.sescacre.controleAcesso.entidades.Registros;
import br.com.sescacre.controleAcesso.entidades.Unidades;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class ControleAcessoBean implements Serializable {

    private Clientes cliente = new Clientes();
    private String carteira;
    private String acesso = "";

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }

    public void pesquisaCliente() {
        RegistroDao registroD = new RegistroDao();
        Registros registro = new Registros();
        ClientesDao clienteDao = new ClientesDao();
        FacesContext msg = FacesContext.getCurrentInstance();
        String nrviacart = "";
        String cduop = "";
        String sqmatric = "";
        String nudv = "";
        if (carteira.length() == 12) {
            nrviacart = carteira.substring(0, 1);
            cduop = carteira.substring(1, 5);
            sqmatric = carteira.substring(5, 11);
            nudv = carteira.substring(11);
        } else {
            cduop = carteira.substring(0, 4);
            sqmatric = carteira.substring(4, 10);
            nudv = carteira.substring(10);
        }
        cliente = clienteDao.pesquisaCliente(cduop, sqmatric, nudv);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext context = (ServletContext) externalContext.getContext();
        String nomeArquivo = cliente.getMat() + ".jpg";
        String arquivo = context.getRealPath("/resources/imgs/tmp/" + nomeArquivo);
        criaArquivo(cliente.getFoto(), arquivo);
        if (cliente.getDataVencimento().before(new Date())) {
            acesso = "Bloqueado";
        } else {
            try {
                Categorias categoria = new CategoriasDao().pesquisaPorId(cliente.getCategoria());
                Unidades unidade = new UnidadesDAO().pesquisaPorId(9);
                acesso = "Liberado";
                registro.setCateira(cliente.getCarteira());
                registro.setCategoria(categoria);
                registro.setUnidade(unidade);
                registro.setNome(cliente.getNome());
                registro.setDataHora(new Date());
                registroD.salvar(registro);
            } catch (Exception ex) {
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Ocorreu um erro ao inserir o pilar '" + registro.getNome() + "'", null));
            }
        }
        carteira = null;
    }

    public void criaArquivo(byte[] bytes, String arquivo) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(arquivo);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControleAcessoBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControleAcessoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpa() {
        carteira = null;
        cliente = new Clientes();
        acesso = "";
    }
}
