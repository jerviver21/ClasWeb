package com.vi.clasificados.publicacion.controller;

import com.vi.clasificados.dominio.Clasificado; 
import com.vi.clasificados.dominio.DetallePrecioClasificado;
import com.vi.clasificados.dominio.ImgClasificado;
import com.vi.clasificados.dominio.Pedido;
import com.vi.clasificados.dominio.SubtipoPublicacion;
import com.vi.clasificados.dominio.TipoClasificado;
import com.vi.clasificados.services.PedidoService;
import com.vi.clasificados.services.PublicacionService;
import com.vi.clasificados.services.TipoClasificadoService;
import com.vi.comun.util.FechaUtils;
import com.vi.comun.util.Log;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.net.UnknownHostException;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;

/**
 * @author Jerson Viveros
 */
@ManagedBean(name="pubWebController")
@SessionScoped
public class PubWebController {
    //Tipos de clasificados WEB
    private  final int SUBTIPOGRATIS = 4;
    private  final int SUBTIPO15 = 5;
    private  final int SUBTIPO25 = 6;

    private int subtipoWeb = SUBTIPOGRATIS;
    private boolean cargarImgs = true;
    
    //Objetos para procesarImpreso la información del clasificado
    private List<DetallePrecioClasificado> detallePrecio;
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
    private List<SelectItem> subtiposPublicacion;
    
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
    
    //Servicios
    @EJB
    TipoClasificadoService tipoService;
    @EJB
    PublicacionService publicacionService;
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
        subtiposPublicacion = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_TIPOPUBIMP));
        pedido = new Pedido(FacesUtil.getUsuario());
        entidades = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_ENTIDAD));
        monedas = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_MONEDAS));
        
        
        
    }
    
    public void iniciarPedido(){
        pedido = new Pedido(FacesUtil.getUsuario());
        pedido.setTipoPedido("CLASIFICADOS IMPRESOS");
        iniciarClasificado();
    }
    
    public void iniciarClasificado(){
        modoEdicion=false;
        cargarImgs = true;
        int idTipo = clasificado.getTipo().getId();
        clasificado = new Clasificado();
        clasificado.setTipo(new TipoClasificado(idTipo));
        seleccionarSubtipos(idTipo);
    }
    
    //Eventos de la página tipoWeb.xhtml
    public String seleccionarSubtipoWeb(int tipo){
        subtipoWeb = tipo;
        return "/publicacion/pubweb.xhtml";
    }
    
    //Eventos desde la página pubweb.xhtml
    public void cambiarTipo(ValueChangeEvent event) {
        Integer idTipo = (Integer) event.getNewValue();
        seleccionarSubtipos(idTipo);
        System.out.println("Tipo cambiado!");
    }
    
    //Eventos desde la página tipoCla.xhtml
    public String redirectTipoWeb(){
        return "/publicacion/tipoweb.xhtml";
    }
    
    //Eventos desde la página tipoCla.xhtml
    public String redirectTipoCla(){
        return "/publicacion/tipocla.xhtml";
    }
    
    /*
    * Descripción: Lógica de la vista, para que el usuario  cambie la interfaz 
    *              de ingreso del clasificado de acuerdo al tipo que seleccione
    *              ( VIVIENDA, EMPLEO, VEHICULOS o VARIOS )
    */
    public void seleccionarSubtipos(int idTipo) {
        tipoClasificado = mapaTipos.get(idTipo);
        Map<Integer, List<TipoClasificado>> subtipos = mapaSubtipos.get(idTipo); 
        Set<Integer> nSubs = subtipos.keySet();
        clasificado.resetSubtipos();
        for(Integer subtipo : nSubs){
            switch(subtipo){
                case 1:
                    nsubtipo1 = subtipos.get(subtipo).get(0).getNombre();
                    subtipos1 = FacesUtil.getSelectsItem(subtipos.get(subtipo));
                    clasificado.setSubtipo1(new TipoClasificado());
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
    
    public String procesar(){
        try {
            clasificado.setSubtipoPublicacion(new SubtipoPublicacion(subtipoWeb));
            publicacionService.procesarweb(clasificado);
            if(!modoEdicion){
                pedido.getClasificados().add(clasificado);
            }
            pedido.setValorTotal(clasificado.getPrecio());
            iniciarClasificado();
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al procesar el clasificado");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return "/publicacion/pedidoweb.xhtml";
    }
    
    //Método de cargar imagenes
    public void cargarImg(FileUploadEvent event){
        try {
            ImgClasificado datosImg = new ImgClasificado();
            datosImg.setExtension(event.getFile().getFileName().replaceAll( ".*\\.(.*)", "$1"));
            datosImg.setImg(event.getFile().getInputstream());
            clasificado.getImgs().add(datosImg);
            if(subtipoWeb == SUBTIPOGRATIS && clasificado.getImgs().size() >= 6){
                cargarImgs = false;
            }else if((subtipoWeb == SUBTIPO15) && clasificado.getImgs().size() >= 10){
                cargarImgs = false;
            }else if((subtipoWeb == SUBTIPO25) && clasificado.getImgs().size() >= 15){
                cargarImgs = false;
            }else{
                cargarImgs = true;
            }
        } catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al cargar el archivo");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }  
    }

    
    //Eventos de la página pedidoweb.xhtml
    public String editar(Clasificado clasificado){
        seleccionarSubtipos(clasificado.getTipo().getId());
        this.clasificado = clasificado;
        modoEdicion = true;
        return "/publicacion/pubweb.xhtml";
    }
    
    public String guardarPedido(){
         try {
            pedido = pedidoService.guardarPedido(pedido);
            FacesUtil.addMessage(FacesUtil.INFO, pedido.getMensajePago());
            iniciarPedido();
        }catch (UnknownHostException e) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            FacesUtil.addMessage(FacesUtil.ERROR, "En el momento no podemos, conectarnos con el servidor, intente más tarde");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al procesar el clasificado");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    
    //Eventos desde la página mis_clasificados.xhtml
    public String agregarClasificadoVencidoAPedido(Clasificado clasificado) {
        clasificado.setPedido(pedido);
        clasificado.setFechaIni(FechaUtils.getFechaMasPeriodo(new Date(), 2, Calendar.DATE));
        if(clasificado.getFechaFin().before(clasificado.getFechaIni())){
           clasificado.setFechaFin(clasificado.getFechaIni()); 
        }
        this.clasificado = clasificado;
        procesar();
        return "/publicacion/pedidoweb.xhtml";
    }
    
    
    //------------------------------------------------ATRIBUTOS-----------------------------------------------------------------------------------
    public Clasificado getClasificado() {
        return clasificado;
    }

    public void setClasificado(Clasificado clasificado) {
        this.clasificado = clasificado;
    }

    public List<SelectItem> getTipos() {
        return tipos;
    }

    public List<SelectItem> getSubtipos1() {
        return subtipos1;
    }

    public List<SelectItem> getSubtipos2() {
        return subtipos2;
    }

    public List<SelectItem> getSubtipos3() {
        return subtipos3;
    }

    public List<SelectItem> getSubtipos4() {
        return subtipos4;
    }

    public List<SelectItem> getSubtipos5() {
        return subtipos5;
    }

    public TipoClasificado getTipoClasificado() {
        return tipoClasificado;
    }

    public String getNsubtipo1() {
        return nsubtipo1;
    }

    public String getNsubtipo2() {
        return nsubtipo2;
    }

    public String getNsubtipo3() {
        return nsubtipo3;
    }

    public String getNsubtipo4() {
        return nsubtipo4;
    }

    public String getNsubtipo5() {
        return nsubtipo5;
    }

    public List<SelectItem> getSubtiposPublicacion() {
        return subtiposPublicacion;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido clasificadosPedido) {
        this.pedido = clasificadosPedido;
    }

    public boolean isModoEdicion() {
        return modoEdicion;
    }

    public List<SelectItem> getEntidades() {
        return entidades;
    }

    public List<SelectItem> getMonedas() {
        return monedas;
    }

    public List<DetallePrecioClasificado> getDetallePrecio() {
        return detallePrecio;
    }  

    public int getSubtipoWeb() {
        return subtipoWeb;
    }
    
    public int getSUBTIPOGRATIS() {
        return SUBTIPOGRATIS;
    }

    public int getSUBTIPO15() {
        return SUBTIPO15;
    }

    public int getSUBTIPO25() {
        return SUBTIPO25;
    }

    /**
     * @return the cargarImgs
     */
    public boolean isCargarImgs() {
        return cargarImgs;
    }

    /**
     * @param cargarImgs the cargarImgs to set
     */
    public void setCargarImgs(boolean cargarImgs) {
        this.cargarImgs = cargarImgs;
    }

}
