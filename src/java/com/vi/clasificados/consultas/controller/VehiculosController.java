
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

@ManagedBean(name="vehiculosController")
@SessionScoped
public class VehiculosController {
    private List<Clasificado> clasificados;
    
    private int tipo = 0;
    private int marca = 0;
    private int precio = 0;
    
    private List<SelectItem> tipos;
    private List<SelectItem> marcas;
    private List<SelectItem> precios;
    
    
    @EJB
    ClasificadosServices service;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    ConsultasCache consultasCache;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        consultasCache = ConsultasCache.getInstance();
        clasificados = consultasCache.getFiltro(ConsultasCache.VEHICULOS, getTipo(), getMarca(), getPrecio());
        
        tipos = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_STTIPOSVEH));
        marcas = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_STMARCASVEH));
        precios = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_STPRECIOSVEH));
    }
    
    public String cambiarFiltro() {
        clasificados = consultasCache.getFiltro(ConsultasCache.VEHICULOS, getTipo(), getMarca(), getPrecio());
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
     * @return the marca
     */
    public int getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(int marca) {
        this.marca = marca;
    }

    /**
     * @return the precio
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * @return the tipos
     */
    public List<SelectItem> getTipos() {
        return tipos;
    }

    /**
     * @return the marcas
     */
    public List<SelectItem> getMarcas() {
        return marcas;
    }

    /**
     * @return the precios
     */
    public List<SelectItem> getPrecios() {
        return precios;
    }

  
    
}
