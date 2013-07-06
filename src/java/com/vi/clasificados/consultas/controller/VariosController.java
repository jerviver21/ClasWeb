
package com.vi.clasificados.consultas.controller;

import com.vi.clasificados.caching.ConsultasCache;
import com.vi.clasificados.dominio.Clasificado;
import com.vi.clasificados.services.ClasificadosServices;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name="variosController")
@SessionScoped
public class VariosController {
    private List<Clasificado> clasificados;
    
    private int tipo = 0;
    
    private List<SelectItem> tipos;
    
    
    @EJB
    ClasificadosServices service;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    ConsultasCache consultasCache;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        consultasCache = ConsultasCache.getInstance();
        clasificados = consultasCache.getFiltro(ConsultasCache.VARIOS, getTipo());
        
        tipos = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_STTIPOSVAR));

    }
    
    public String cambiarFiltro() {
        clasificados = consultasCache.getFiltro(ConsultasCache.VARIOS, getTipo());
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
