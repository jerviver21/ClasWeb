package com.vi.clasificados.controller;

import com.vi.clasificados.dominio.Clasificado; 
import com.vi.clasificados.dominio.TipoClasificado;
import com.vi.clasificados.services.TipoClasificadoService;
import com.vi.util.FacesUtil;
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
    private Clasificado clasificado;
    private List<SelectItem> tipos;
    private List<SelectItem> subtipos1;
    private List<SelectItem> subtipos2;
    private List<SelectItem> subtipos3;
    private List<SelectItem> subtipos4;
    private List<SelectItem> subtipos5;
    
    private Integer tipo;
    private Integer subtipo1;
    private Integer subtipo2;
    private Integer subtipo3;
    private Integer subtipo4;
    private Integer subtipo5;
    
    String nSubtipo1;
    String nSubtipo2;
    String nSubtipo3;
    String nSubtipo4;
    String nSubtipo5;
    
    //Permite precargar en memoria todos los subtipos para que sean rapidamente accedidos
    //<TIPO,<SUBTIPO,List<Subtipos>>>
    Map<Integer, Map<String, List<TipoClasificado>>> mapaTipos;
    
    @EJB
    TipoClasificadoService tipoService;
    
    @PostConstruct
    public void init(){
        setTipos(FacesUtil.getSelectsItem(tipoService.getTiposBase()));
        mapaTipos = tipoService.getEstructuraConsulta();
    }
    
    public void seleccionarSubtipos(ValueChangeEvent event) {
        Map<String, List<TipoClasificado>> subtipos = mapaTipos.get((Integer) event.getNewValue()); 
        Set<String> nSubs = subtipos.keySet();
        
        int indiceSubtipo = 1;
        for(String nombre : nSubs){
            switch(indiceSubtipo){
                case 1:
                    nSubtipo1 = nombre;
                    subtipos1 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
                case 2:
                    nSubtipo2 = nombre;
                    subtipos2 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
                case 3:
                    nSubtipo3 = nombre;
                    subtipos3 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
                case 4:
                    nSubtipo4 = nombre;
                    subtipos4 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
                case 5:
                    nSubtipo5 = nombre;
                    subtipos5 = FacesUtil.getSelectsItem(subtipos.get(nombre));
                    break;
            }
            indiceSubtipo++;
        }
        
        switch(indiceSubtipo){
            case 1:
                nSubtipo1 = null;
            case 2:
                nSubtipo2 = null;
            case 3:
                nSubtipo3 = null;
            case 4:
                nSubtipo4 = null;
            case 5:
                nSubtipo5 = null;
        }
        System.out.println(nSubtipo1+" - "+nSubtipo2+" - "+nSubtipo3);
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
     * @param subtipos1 the subtipos1 to set
     */
    public void setSubtipos1(List<SelectItem> subtipos1) {
        this.subtipos1 = subtipos1;
    }

    /**
     * @return the subtipos2
     */
    public List<SelectItem> getSubtipos2() {
        return subtipos2;
    }

    /**
     * @param subtipos2 the subtipos2 to set
     */
    public void setSubtipos2(List<SelectItem> subtipos2) {
        this.subtipos2 = subtipos2;
    }

    /**
     * @return the subtipos3
     */
    public List<SelectItem> getSubtipos3() {
        return subtipos3;
    }

    /**
     * @param subtipos3 the subtipos3 to set
     */
    public void setSubtipos3(List<SelectItem> subtipos3) {
        this.subtipos3 = subtipos3;
    }

    /**
     * @return the subtipos4
     */
    public List<SelectItem> getSubtipos4() {
        return subtipos4;
    }

    /**
     * @param subtipos4 the subtipos4 to set
     */
    public void setSubtipos4(List<SelectItem> subtipos4) {
        this.subtipos4 = subtipos4;
    }

    /**
     * @return the subtipos5
     */
    public List<SelectItem> getSubtipos5() {
        return subtipos5;
    }

    /**
     * @param subtipos5 the subtipos5 to set
     */
    public void setSubtipos5(List<SelectItem> subtipos5) {
        this.subtipos5 = subtipos5;
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
     * @return the subtipo1
     */
    public Integer getSubtipo1() {
        return subtipo1;
    }

    /**
     * @param subtipo1 the subtipo1 to set
     */
    public void setSubtipo1(Integer subtipo1) {
        this.subtipo1 = subtipo1;
    }

    /**
     * @return the subtipo2
     */
    public Integer getSubtipo2() {
        return subtipo2;
    }

    /**
     * @param subtipo2 the subtipo2 to set
     */
    public void setSubtipo2(Integer subtipo2) {
        this.subtipo2 = subtipo2;
    }

    /**
     * @return the subtipo3
     */
    public Integer getSubtipo3() {
        return subtipo3;
    }

    /**
     * @param subtipo3 the subtipo3 to set
     */
    public void setSubtipo3(Integer subtipo3) {
        this.subtipo3 = subtipo3;
    }

    /**
     * @return the subtipo4
     */
    public Integer getSubtipo4() {
        return subtipo4;
    }

    /**
     * @param subtipo4 the subtipo4 to set
     */
    public void setSubtipo4(Integer subtipo4) {
        this.subtipo4 = subtipo4;
    }

    /**
     * @return the subtipo5
     */
    public Integer getSubtipo5() {
        return subtipo5;
    }

    /**
     * @param subtipo5 the subtipo5 to set
     */
    public void setSubtipo5(Integer subtipo5) {
        this.subtipo5 = subtipo5;
    }
    
}
