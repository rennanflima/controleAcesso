/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.sescacre.controleAcesso.dao;

import br.com.sescacre.controleAcesso.entidades.Registros;
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
public class RegistroDao {
    
    public void salvar(Registros r) throws Exception {
        Session s = HibernateUtil.getSession();
        Transaction t = null; 
        try {
            t = s.beginTransaction();
            s.save(r);
            t.commit();
        } catch (Exception ex) {
            t.rollback();
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void alterar(Registros r) throws Exception {
        Session s = HibernateUtil.getSession();
        Transaction t = null;
        try {
            t = s.beginTransaction();
            s.update(r);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Registros r) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(r);
        t.commit();
        s.close();
    }

    public List<Registros> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Registros");
        List<Registros> lista = q.list();
        s.close();
        return lista;
    }

    public Registros pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        Registros r = (Registros) s.load(Registros.class, id);
        s.close();
        return r;
    }
}
