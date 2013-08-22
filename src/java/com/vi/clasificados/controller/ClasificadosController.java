
package com.vi.clasificados.controller;

import com.vi.clasificados.dominio.Clasificado;
import com.vi.clasificados.dominio.EstadosClasificado;
import com.vi.clasificados.services.ClasificadosService;
import com.vi.clasificados.services.TiposPublicacionService;
import com.vi.clasificados.utils.ClasificadoEstados;
import com.vi.clasificados.utils.ClasificadosTipo;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.ArrayList;
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
    ClasificadosService clasificadosService;
    
    @EJB
    TiposPublicacionService tipoPubService;

    
    private Clasificado clasificado;
    private List<Clasificado> clasificados;
    private List<Clasificado> clasFiltro;
    
    private List<SelectItem> estadosEditables;
    private List<SelectItem> estados;
    private List<SelectItem> tiposPub;
    private Integer estado = 1;
    private Integer tipo = 0;

    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        String usr = FacesUtil.getUsuario();
        setClasificados(clasificadosService.getClasificados(usr, ClasificadoEstados.PEDIDOXPAGAR));
        estados = FacesUtil.getSelectsItem(ClasificadoEstados.getEstados());
        estadosEditables = FacesUtil.getSelectsItem(ClasificadoEstados.getEstadosEditables());
        tiposPub =FacesUtil.getSelectsItem(tipoPubService.findAllTipos());
        tiposPub.add(new SelectItem(0, "Select"));
        clasFiltro = clasificados;
    }
    
    
    public void cambiarEstado(ValueChangeEvent event){
        estado = (Integer) event.getNewValue();
        setClasificados(clasificadosService.getClasificados(FacesUtil.getUsuario(), estado == -1 ? null : new EstadosClasificado(estado)));
        clasFiltro = clasificados;
        tipo = 0;
    }
    
    public void cambiarTipo(ValueChangeEvent event){
        tipo = (Integer) event.getNewValue();
        cambiarMedio(tipo);
    }
    
    public void cambiarMedio(int tipo){
        if(tipo == 0){
           clasFiltro = clasificados; 
        }else{
           clasFiltro = new ArrayList<Clasificado>();
        }
        for(Clasificado clas:clasificados){
            if(clas.getTipoPublicacion().getId() == tipo){
                clasFiltro.add(clas);
            }
        }
    }

    public String editar(Clasificado clasificado){
        this.setClasificado(clasificado);
        return "/publicacion/clasificado.xhtml";
    }
    
    public String agregarPedido(Clasificado clasificado){
        PublicacionController pc = (PublicacionController) FacesUtil.getManagedBean("#{publicacionController}");
        pc.agregarClasificadoVencidoAPedido(clasificado);
        return "/publicacion/publicacion_pedido.xhtml";
    }
    
    public String guardarClasificado(){
        clasificadosService.edit(clasificado);
        setClasificados(clasificadosService.getClasificados(FacesUtil.getUsuario(), estado == -1 ? null : new EstadosClasificado(estado)));
        clasFiltro = clasificados;
        cambiarMedio(tipo);
        return "/publicacion/mis_clasificados.xhtml";
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
     * @return the tipo
     */
    public Integer getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the clasificado
     */
    public Clasificado getClasificado() {
        return clasificado;
    }

    /**
     * @param clasificado the clasificado to set
     */
    public void setClasificado(Clasificado clasificado) {
        this.clasificado = clasificado;
    }

    /**
     * @return the estadosEditables
     */
    public List<SelectItem> getEstadosEditables() {
        return estadosEditables;
    }

    /**
     * @param estadosEditables the estadosEditables to set
     */
    public void setEstadosEditables(List<SelectItem> estadosEditables) {
        this.estadosEditables = estadosEditables;
    }


}
