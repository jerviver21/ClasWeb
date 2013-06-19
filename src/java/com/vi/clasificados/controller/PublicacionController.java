package com.vi.clasificados.controller;

import com.vi.clasificados.dominio.Clasificado; 
import com.vi.clasificados.dominio.TipoClasificado;
import com.vi.clasificados.dominio.TipoPublicacion;
import com.vi.clasificados.services.ClasificadosServices;
import com.vi.clasificados.services.TipoClasificadoService;
import com.vi.clasificados.services.TiposPublicacionService;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * @author Jerson Viveros
 */
@ManagedBean(name="publicacionController")
@SessionScoped
public class PublicacionController {
    //Objetos para procesar la información del clasificado
    private Clasificado clasificado;
    private TipoClasificado tipoClasificado;
    private List<Clasificado> pedido;
    
    //Objetos para guardar información de listas desplegables
    private List<SelectItem> tipos;
    private List<SelectItem> subtipos1;
    private List<SelectItem> subtipos2;
    private List<SelectItem> subtipos3;
    private List<SelectItem> subtipos4;
    private List<SelectItem> subtipos5;
    private List<String> tiposPublicacion;
    
    //Objetos para los titulos de los subtipos de cada tipo
    private String nsubtipo1;
    private String nsubtipo2;
    private String nsubtipo3;
    private String nsubtipo4;
    private String nsubtipo5;
    
    //Variables de control permiten hacer un despliegue dinámico y precargar en memoria todos los subtipos y para que sean rapidamente accedidos
    //<TIPO,<SUBTIPO,List<Subtipos>>>
    Map<Integer, Map<String, List<TipoClasificado>>> mapaSubtipos;
    Map<Integer, TipoClasificado> mapaTipos;
    boolean modoEdicion = false;
    
    //Servicios
    @EJB
    TipoClasificadoService tipoService;
    @EJB
    TiposPublicacionService tipoPubService;
    @EJB
    ClasificadosServices clasificadosService;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        clasificado = new Clasificado();
        mapaTipos = tipoService.getTiposBase();
        mapaSubtipos = tipoService.getEstructuraConsulta();
        seleccionarSubtipos(1);// 1 - Es el tipo: FINCA RAIZ 
        tipos = FacesUtil.getSelectsItem(mapaTipos);
        tiposPublicacion = tipoPubService.findAllNombresTipos();
        pedido = new ArrayList<Clasificado>();
    }
    
    public void cambiarTipo(ValueChangeEvent event) {
        Integer idTipo = (Integer) event.getNewValue();
        seleccionarSubtipos(idTipo);
    }
    
    public void seleccionarSubtipos(int idTipo) {
        tipoClasificado = mapaTipos.get(idTipo);
        Map<String, List<TipoClasificado>> subtipos = mapaSubtipos.get(idTipo); 
        Set<String> nSubs = subtipos.keySet();
        
        int indiceSubtipo = 1;
        for(String nombre : nSubs){
            switch(indiceSubtipo){
                case 1:
                    setNsubtipo1(nombre);
                    subtipos1 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
                case 2:
                    nsubtipo2 = nombre;
                    subtipos2 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
                case 3:
                    nsubtipo3 = nombre;
                    subtipos3 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
                case 4:
                    nsubtipo4 = nombre;
                    subtipos4 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
                case 5:
                    nsubtipo5 = nombre;
                    subtipos5 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
            }
            indiceSubtipo++;
        }
        
        switch(indiceSubtipo){
            case 1:
                nsubtipo1 = null;
            case 2:
                nsubtipo2 = null;
            case 3:
                nsubtipo3 = null;
            case 4:
                nsubtipo4 = null;
            case 5:
                nsubtipo5 = null;
        }
    }
    
    
    public String procesarClasificado(){
        System.out.println(clasificado.getClasificado());
        List<Clasificado> clasificados = clasificadosService.procesarClasificado(clasificado);
        pedido.addAll(clasificados);
        return "/publicacion/publicacion_pedido.xhtml";
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
     * @return the tipos
     */
    public List<SelectItem> getTipos() {
        return tipos;
    }

    /**
     * @param tipos the tipos to set
     */
    public void setTipos(List<SelectItem> tipos) {
        this.tipos = tipos;
    }

    /**
     * @return the subtipos1
     */
    public List<SelectItem> getSubtipos1() {
        return subtipos1;
    }

    /**
     * @return the subtipos2
     */
    public List<SelectItem> getSubtipos2() {
        return subtipos2;
    }

    /**
     * @return the subtipos3
     */
    public List<SelectItem> getSubtipos3() {
        return subtipos3;
    }


    /**
     * @return the subtipos4
     */
    public List<SelectItem> getSubtipos4() {
        return subtipos4;
    }

    /**
     * @return the subtipos5
     */
    public List<SelectItem> getSubtipos5() {
        return subtipos5;
    }


    /**
     * @return the tipoClasificado
     */
    public TipoClasificado getTipoClasificado() {
        return tipoClasificado;
    }


    /**
     * @return the nsubtipo1
     */
    public String getNsubtipo1() {
        return nsubtipo1;
    }

    /**
     * @param nsubtipo1 the nsubtipo1 to set
     */
    public void setNsubtipo1(String nsubtipo1) {
        this.nsubtipo1 = nsubtipo1;
    }

    /**
     * @return the nsubtipo2
     */
    public String getNsubtipo2() {
        return nsubtipo2;
    }

    /**
     * @return the nsubtipo3
     */
    public String getNsubtipo3() {
        return nsubtipo3;
    }

    /**
     * @return the nsubtipo4
     */
    public String getNsubtipo4() {
        return nsubtipo4;
    }

    /**
     * @return the nsubtipo5
     */
    public String getNsubtipo5() {
        return nsubtipo5;
    }


    /**
     * @return the tiposPublicacion
     */
    public List<String> getTiposPublicacion() {
        return tiposPublicacion;
    }

    /**
     * @param tiposPublicacion the tiposPublicacion to set
     */
    public void setTiposPublicacion(List<String> tiposPublicacion) {
        this.tiposPublicacion = tiposPublicacion;
    }

    /**
     * @return the clasificadosPedido
     */
    public List<Clasificado> getPedido() {
        return pedido;
    }

    /**
     * @param clasificadosPedido the clasificadosPedido to set
     */
    public void setPedido(List<Clasificado> clasificadosPedido) {
        this.pedido = clasificadosPedido;
    }
    
}
