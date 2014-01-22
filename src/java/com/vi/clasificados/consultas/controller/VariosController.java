
package com.vi.clasificados.consultas.controller;

import com.vi.clasificados.dominio.Clasificado;
import com.vi.clasificados.services.ClasificadosService;
import com.vi.clasificados.services.ConsultasService;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="variosController")
@SessionScoped
public class VariosController {
    private List<Clasificado> clasificados;
    private int tipo = 102;  
    private List<SelectItem> tipos;
    
    
    @EJB
    ClasificadosService service;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        clasificados = new ArrayList<Clasificado>();
        tipos = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.VAR_TIPO));
    }
    
    public String consultar() {
        clasificados = service.consultar(ConsultasService.VARIOS, getTipo());
        return "/consultas/varios.xhtml";
    }

    /**
     * @return the clasificados
     */
    public List<Clasificado> getClasificados() {
        return clasificados;
    }


    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the tipos
     */
    public List<SelectItem> getTipos() {
        return tipos;
    }

   
    
}
