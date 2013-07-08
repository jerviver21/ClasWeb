package com.vi.clasificados.controller;

import com.vi.clasificados.locator.ClasificadosCachingLocator;
import com.vi.clasificados.dominio.Clasificado; 
import com.vi.clasificados.dominio.Pedido;
import com.vi.clasificados.dominio.TipoClasificado;
import com.vi.clasificados.services.ClasificadosServices;
import com.vi.clasificados.services.PedidoService;
import com.vi.clasificados.services.TipoClasificadoService;
import com.vi.clasificados.services.TiposPublicacionService;
import com.vi.clasificados.utils.ClasificadosTipo;
import com.vi.comun.util.FechaUtils;
import com.vi.comun.util.Log;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
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
    private Clasificado clasificadoDetalle;
    private Clasificado clasificado;
    private TipoClasificado tipoClasificado;
    private Pedido pedido;
    
    //Objetos para guardar información de listas desplegables
    private List<SelectItem> tipos;
    private List<SelectItem> subtipos1;
    private List<SelectItem> subtipos2;
    private List<SelectItem> subtipos3;
    private List<SelectItem> subtipos4;
    private List<SelectItem> subtipos5;
    private List<SelectItem> entidades;
    private List<SelectItem> monedas;
    private List<String> tiposPublicacion;
    
    //Objetos para los titulos de los subtipos de cada tipo
    private String nsubtipo1;
    private String nsubtipo2;
    private String nsubtipo3;
    private String nsubtipo4;
    private String nsubtipo5;
    
    //Variables de control permiten hacer un despliegue dinámico y precargar en memoria todos los subtipos, para que sean rapidamente accedidos
    //<TIPO,<SUBTIPO,List<Subtipos>>>, por cada tipo hay varios subtipos
    Map<Integer, Map<Integer, List<TipoClasificado>>> mapaSubtipos;
    Map<Integer, TipoClasificado> mapaTipos;
    private boolean modoEdicion = false;
    private Date minDate;

    
    //Servicios
    @EJB
    TipoClasificadoService tipoService;
    @EJB
    TiposPublicacionService tipoPubService;
    @EJB
    ClasificadosServices clasificadosService;
    @EJB
    PedidoService pedidoService;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        clasificado = new Clasificado();
        mapaTipos = tipoService.getTipos();
        mapaSubtipos = tipoService.getSubtipos();
        
        
        seleccionarSubtipos(clasificado.getTipo().getId());
        tipos = FacesUtil.getSelectsItem(mapaTipos);
        tiposPublicacion = tipoPubService.findAll();
        pedido = new Pedido(FacesUtil.getUsuario());
        minDate = FechaUtils.getFechaMasPeriodo(new Date(), 1, Calendar.DATE);
        clasificado.setFechaIni(minDate);
        entidades = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_ENTIDAD));
        setMonedas(FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_MONEDAS)));
    }
    
    public void cambiarTipo(ValueChangeEvent event) {
        Integer idTipo = (Integer) event.getNewValue();
        seleccionarSubtipos(idTipo);
    }
    
    
    public void seleccionarSubtipos(int idTipo) {
        tipoClasificado = mapaTipos.get(idTipo);
        Map<Integer, List<TipoClasificado>> subtipos = mapaSubtipos.get(idTipo); 
        Set<Integer> nSubs = subtipos.keySet();
        clasificado.resetSubtipos();
        for(Integer subtipo : nSubs){
            switch(subtipo){
                case 1:
                    subtipos1 = FacesUtil.getSelectsItem(subtipos.get(subtipo));
                    clasificado.setSubtipo1(new TipoClasificado());
                    setNsubtipo1(subtipos.get(subtipo).get(0).getNombre());
                    break;
                case 2:
                    nsubtipo2 = subtipos.get(subtipo).get(0).getNombre();
                    subtipos2 = FacesUtil.getSelectsItem(subtipos.get(subtipo));
                    clasificado.setSubtipo2(new TipoClasificado());
                    break;
                case 3:
                    nsubtipo3 = subtipos.get(subtipo).get(0).getNombre();
                    subtipos3 = FacesUtil.getSelectsItem(subtipos.get(subtipo));
                    clasificado.setSubtipo3(new TipoClasificado());
                    break;
                case 4:
                    nsubtipo4 = subtipos.get(subtipo).get(0).getNombre();
                    subtipos4 = FacesUtil.getSelectsItem(subtipos.get(subtipo));
                    clasificado.setSubtipo4(new TipoClasificado());
                    break;
                case 5:
                    nsubtipo5 = subtipos.get(subtipo).get(0).getNombre();
                    subtipos5 = FacesUtil.getSelectsItem(subtipos.get(subtipo));
                    clasificado.setSubtipo5(new TipoClasificado());
                    break;
            }
        }
    }
    
    
    public String procesarClasificado(){
        try {
            if(clasificado.getOpcionesPublicacion().isEmpty()){
                FacesUtil.addMessage(FacesUtil.ERROR, "Debe seleccionar al menos un medio de publicación");
                return null;          
            }
            List<Clasificado> clasificados = clasificadosService.procesarClasificado(clasificado);
            pedido.getClasificados().addAll(clasificados);
            pedido.setValorTotal(clasificadosService.calcularTotalPedido(pedido.getClasificados()));
            int idTipo = clasificado.getTipo().getId();
            clasificado = new Clasificado();
            clasificado.setTipo(new TipoClasificado(idTipo));
            seleccionarSubtipos(idTipo);
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al procesar el clasificado");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return "/publicacion/publicacion_pedido.xhtml";
    }
    
    public String procesarEdicion(){
        try {
            clasificado = clasificadosService.editarClasificado(clasificado);
            pedido.setValorTotal(clasificadosService.calcularTotalPedido(pedido.getClasificados()));
            setModoEdicion(false);
            int idTipo = clasificado.getTipo().getId();
            clasificado = new Clasificado();
            seleccionarSubtipos(idTipo);
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al procesar el clasificado");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        
        return "/publicacion/publicacion_pedido.xhtml";
    }
    
    //Eventos de la página publicacion_pedido.xhtml
    public String verDetalle(Clasificado clasificado){
        this.clasificadoDetalle = clasificado;
        return "/publicacion/publicacion_detalle.xhtml";
    }
    
    public String editar(Clasificado clasificado){
        this.clasificado = clasificado;
        setModoEdicion(true);
        return "/publicacion/publicacion_clasificado.xhtml";
    }
    
    public String crearNuevo(){
        modoEdicion = false;
        return "/publicacion/publicacion_clasificado.xhtml";
    }
    
    public String borrar(Clasificado clasificado){
        for(int i = 0; i < pedido.getClasificados().size() ; i++){
            Clasificado c = pedido.getClasificados().get(i);
            if(c.getTipoPublicacion().equals(clasificado.getTipoPublicacion()) 
                    && c.getClasificado().equals(clasificado.getClasificado()) 
                    && c.getFechaIni().equals(clasificado.getFechaIni())
                    && c.getFechaFin().equals(clasificado.getFechaFin())
                    && c.getTipo().equals(clasificado.getTipo())){
                //Es preferible hacer esto a guardar en base de datos antes de que el usuario este seguro de enviar todo el pedido
                pedido.getClasificados().remove(i);
                break;
            }
        }
        pedido.setValorTotal(clasificadosService.calcularTotalPedido(pedido.getClasificados()));
        return null;
    }
    
    public String generarCodigoPago(){
         try {
            pedido = pedidoService.save(pedido);
            FacesUtil.addMessage(FacesUtil.INFO, pedido.getMensajePago());
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al procesar el clasificado");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    
    //Eventos desde otros beans
    void agregarClasificadoVencidoAPedido(Clasificado clasificado) {
        clasificado.setPedido(pedido);
        pedido.getClasificados().add(clasificado);
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
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * @param clasificadosPedido the clasificadosPedido to set
     */
    public void setPedido(Pedido clasificadosPedido) {
        this.pedido = clasificadosPedido;
    }

    /**
     * @return the minDate
     */
    public Date getMinDate() {
        return minDate;
    }

    /**
     * @param minDate the minDate to set
     */
    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }


    /**
     * @return the modoEdicion
     */
    public boolean isModoEdicion() {
        return modoEdicion;
    }

    /**
     * @param modoEdicion the modoEdicion to set
     */
    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }

    /**
     * @return the clasificadoDetalle
     */
    public Clasificado getClasificadoDetalle() {
        return clasificadoDetalle;
    }

    /**
     * @param clasificadoDetalle the clasificadoDetalle to set
     */
    public void setClasificadoDetalle(Clasificado clasificadoDetalle) {
        this.clasificadoDetalle = clasificadoDetalle;
    }

    /**
     * @return the entidadesPago
     */
    public List<SelectItem> getEntidades() {
        return entidades;
    }

    /**
     * @return the monedas
     */
    public List<SelectItem> getMonedas() {
        return monedas;
    }

    /**
     * @param monedas the monedas to set
     */
    public void setMonedas(List<SelectItem> monedas) {
        this.monedas = monedas;
    }



    
}
