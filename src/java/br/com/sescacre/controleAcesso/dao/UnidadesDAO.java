/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sescacre.controleAcesso.dao;

import br.com.sescacre.controleAcesso.entidades.Unidades;
import br.com.sescacre.controleAcesso.util.HibernateUtil;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rennan Francisco
 */
public class UnidadesDAO {

    public void salvar(Unidades unidade) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(unidade);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void alterar(Unidades unidade) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(unidade);
            t.commit();
        } catch (Exception e) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Unidades unidade) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(unidade);
        t.commit();
        s.close();
    }

    public List<Unidades> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Unidades u order by u.nome");
        List<Unidades> lista = q.list();
        s.close();
        return lista;
    }

    public Unidades pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        Unidades unidade = (Unidades) s.load(Unidades.class, id);
        s.close();
        return unidade;
    }
}
