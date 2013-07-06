/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author jerviver21
 */
@ManagedBean(name="inmobiliarioController")
@SessionScoped
public class InmobiliarioController {
    private List<Clasificado> clasificados;
    
    private int tipoOferta = 0;
    private int tipoInmueble = 0;
    private int ubicacion = 0;
    private int area = 0;
    private int rangoPrecio = 0;
   
    
    private List<SelectItem> tiposOfertas;
    private List<SelectItem> tiposInmuebles;
    private List<SelectItem> ubicaciones;
    private List<SelectItem> areas;
    private List<SelectItem> rangosPrecios;
    
    
    
    @EJB
    ClasificadosServices service;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    ConsultasCache consultasCache;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        consultasCache = ConsultasCache.getInstance();
        clasificados = consultasCache.getFiltro(ConsultasCache.INMOBILIARIO, getTipoOferta(), getTipoInmueble(), getUbicacion(), getArea(), getRangoPrecio());
        
        tiposOfertas = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_STTIPOOFERIMB));
        tiposInmuebles = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_STTIPOINM));
        ubicaciones = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_STUBICACION));
        areas = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_STAREASINM));
        rangosPrecios = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_STRANGOPRECIMB));

    }
    
    public String cambiarFiltro() {
        clasificados = consultasCache.getFiltro(ConsultasCache.INMOBILIARIO, getTipoOferta(), getTipoInmueble(), getUbicacion(), getArea(), getRangoPrecio());
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
     * @return the tipoOferta
     */
    public int getTipoOferta() {
        return tipoOferta;
    }

    /**
     * @param tipoOferta the tipoOferta to set
     */
    public void setTipoOferta(int tipoOferta) {
        this.tipoOferta = tipoOferta;
    }

    /**
     * @return the tipoInmueble
     */
    public int getTipoInmueble() {
        return tipoInmueble;
    }

    /**
     * @param tipoInmueble the tipoInmueble to set
     */
    public void setTipoInmueble(int tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    /**
     * @return the ubicacion
     */
    public int getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(int ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @return the area
     */
    public int getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(int area) {
        this.area = area;
    }

    /**
     * @return the rangoPrecio
     */
    public int getRangoPrecio() {
        return rangoPrecio;
    }

    /**
     * @param rangoPrecio the rangoPrecio to set
     */
    public void setRangoPrecio(int rangoPrecio) {
        this.rangoPrecio = rangoPrecio;
    }

    /**
     * @return the tiposOfertas
     */
    public List<SelectItem> getTiposOfertas() {
        return tiposOfertas;
    }

    /**
     * @return the tiposInmuebles
     */
    public List<SelectItem> getTiposInmuebles() {
        return tiposInmuebles;
    }

    /**
     * @return the ubicaciones
     */
    public List<SelectItem> getUbicaciones() {
        return ubicaciones;
    }

    /**
     * @return the areas
     */
    public List<SelectItem> getAreas() {
        return areas;
    }

    /**
     * @return the rangosPrecios
     */
    public List<SelectItem> getRangosPrecios() {
        return rangosPrecios;
    }
    
}
