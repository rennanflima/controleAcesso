/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sescacre.controleAcesso.dao;

import br.com.sescacre.controleAcesso.entidades.Categorias;
import br.com.sescacre.controleAcesso.util.Conexao;
import br.com.sescacre.controleAcesso.util.HibernateUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rennan Francisco
 */
public class CategoriasDao {
    
    public void salvar(Categorias cat) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(cat);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void alterar(Categorias cat) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(cat);
            t.commit();
        } catch (Exception e) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Categorias cat) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(cat);
        t.commit();
        s.close();
    }

    public List<Categorias> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Categorias");
        List<Categorias> lista = q.list();
        s.close();
        return lista;
    }

    public Categorias pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        Categorias cat = (Categorias) s.load(Categorias.class, id);
        s.close();
        return cat;
    }    
    
    public List<Categorias> importaCategoriasDB2(){
        Conexao con = new Conexao();
        List<Categorias> lista = new ArrayList<Categorias>();
        try {
            Connection conn = con.abreConexao();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT CDCATEGORI, DSCATEGORI, CDIMPRESS FROM CATEGORIA");
            while(rs.next()){
                Categorias categoria = new Categorias();
                categoria.setIdCategoria(rs.getInt("CDCATEGORI"));
                categoria.setDescricao(rs.getString("DSCATEGORI"));
                categoria.setSigla(rs.getString("CDIMPRESS"));
                lista.add(categoria);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar: "+e.getMessage());
        }finally{
            con.fechaConexao();
        }
        return lista;
    }
}
