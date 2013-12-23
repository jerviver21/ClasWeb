
package com.vi.clasificados.consultas.controller;

import com.vi.clasificados.dominio.Clasificado;
import com.vi.clasificados.services.ClasificadosService;
import com.vi.locator.ComboLocator;
import com.vi.usuarios.dominio.Users;
import com.vi.usuarios.services.UsuariosServicesLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="detalleController")
@SessionScoped
public class DetalleController {
    private Clasificado clas1;
    private Users usuario;
    
    @EJB
    UsuariosServicesLocal usrService;
    
    @EJB
    ClasificadosService clasService;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    //Imagenes
    private int pagelinks = 0;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
    }
    
    
    public String mostrarClasificado(Clasificado clasificado){
        init();
        this.clas1 = clasService.findWithImgs(clasificado.getId());
        pagelinks = this.clas1.getImgs().size();
        this.usuario = usrService.findByUser(clasificado.getPedido().getUsuario());
        return "/consultas/detalle.xhtml";
    }
    

    /**
     * @return the clasificado
     */
    public Clasificado getClas1() {
        return clas1;
    }

    /**
     * @return the usuario
     */
    public Users getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Users usuario) {
        this.usuario = usuario;
    }


    /**
     * @return the pagelinks
     */
    public int getPagelinks() {
        return pagelinks;
    }


}
