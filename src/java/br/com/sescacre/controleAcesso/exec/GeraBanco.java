package br.com.sescacre.controleAcesso.exec;

//import br.com.uninorte.siscodis.dao.UsersDAO;

import br.com.sescacre.controleAcesso.dao.CategoriasDao;
import br.com.sescacre.controleAcesso.dao.UsuariosDao;
import br.com.sescacre.controleAcesso.entidades.Categorias;
import br.com.sescacre.controleAcesso.entidades.Usuarios;
import br.com.uninorte.siscodis.util.GeraSenha;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

//import br.com.uninorte.siscodis.entidades.Usuarios;
//import br.com.uninorte.siscodis.util.GeraSenha;

public class GeraBanco {
    
    private static UsuariosDao ud = new UsuariosDao();
    private static Usuarios u = new Usuarios();
    
    
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        SchemaExport se = new SchemaExport(configuration);
        se.create(true, true);   
        u.setAutorizacao("ROLE_GER");
        u.setLogin("admsesc");
        u.setSenha(new GeraSenha().ecripta("admin"));
        try {
            ud.salvar(u);
        } catch (Exception ex) {
            System.out.println("O administrador nao foi criado com sucesso!!!");
        }
        List<Categorias> lista = new CategoriasDao().importaCategoriasDB2();
        CategoriasDao cd = new CategoriasDao();
        for (Categorias categoria : lista) {
            try {
                cd.salvar(categoria);
            } catch (Exception ex) {
                System.out.println("Erro ao gravar: " + ex.getMessage());
            }
        }
    }
}
