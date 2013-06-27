
package com.vi.clasificados.controller;

import com.vi.clasificados.dominio.Clasificado;
import com.vi.clasificados.dominio.EstadosClasificado;
import com.vi.clasificados.services.ClasificadosServices;
import com.vi.clasificados.services.TiposPublicacionService;
import com.vi.clasificados.utils.ClasificadoEstados;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author jerviver21
 */

@ManagedBean(name="clasificadoController")
@SessionScoped
public class ClasificadosController {
    
    @EJB
    ClasificadosServices clasificadosService;
    
    @EJB
    TiposPublicacionService tipoPubService;

    
    private Clasificado clasificado;
    private List<Clasificado> clasificados;
    private List<Clasificado> clasFiltro;
    
    private List<SelectItem> estados;
    private List<SelectItem> tiposPub;
    private Integer estado = 1;

    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        String usr = FacesUtil.getUsuario();
        setClasificados(clasificadosService.getClasificados(usr, ClasificadoEstados.PEDIDOXPAGAR));
        estados = FacesUtil.getSelectsItem(clasificadosService.getEstados());
        setTiposPub(FacesUtil.getSelectsItem(tipoPubService.findAllTipos(), "getNombre","getNombre"));
    }
    
    
    public void cambiarEstado(ValueChangeEvent event){
        estado = (Integer) event.getNewValue();
        setClasificados(clasificadosService.getClasificados(FacesUtil.getUsuario(), estado == -1 ? null : new EstadosClasificado(estado)));
        
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
     * @return the estados
     */
    public List<SelectItem> getEstados() {
        return estados;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the clasFiltro
     */
    public List<Clasificado> getClasFiltro() {
        return clasFiltro;
    }

    /**
     * @param clasFiltro the clasFiltro to set
     */
    public void setClasFiltro(List<Clasificado> clasFiltro) {
        this.setClasFiltro(clasFiltro);
    }

    /**
     * @return the tiposPub
     */
    public List<SelectItem> getTiposPub() {
        return tiposPub;
    }

    /**
     * @param tiposPub the tiposPub to set
     */
    public void setTiposPub(List<SelectItem> tiposPub) {
        this.tiposPub = tiposPub;
    }

}
