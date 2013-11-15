
package com.vi.clasificados.consultas.controller;

import com.vi.clasificados.locator.ClasificadosCachingLocator;
import com.vi.clasificados.dominio.Clasificado;
import com.vi.clasificados.services.ClasificadosService;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="variosController")
@RequestScoped
public class VariosController {
    private List<Clasificado> clasificados;
    private int tipo = 0;  
    private List<SelectItem> tipos;
    
    
    @EJB
    ClasificadosService service;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        clasificados = service.getFiltro(ClasificadosCachingLocator.VARIOS, getTipo());
        tipos = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.VAR_TIPO));
    }
    
    public String cambiarFiltro() {
        clasificados = service.getFiltro(ClasificadosCachingLocator.VARIOS, getTipo());
        return null;
    }

    /**
     * @return the clasificados
     */
    public List<Clasificado> getClasificados() {
        return clasificados;
    }

    /**
     * @param clasificados the clasificados to set
     */
    public void setClasificados(List<Clasificado> clasificados) {
        this.clasificados = clasificados;
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
