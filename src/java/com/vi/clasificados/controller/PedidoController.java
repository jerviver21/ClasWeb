
package com.vi.clasificados.controller;

import com.vi.clasificados.dominio.Clasificado;
import com.vi.clasificados.dominio.EstadosClasificado;
import com.vi.clasificados.dominio.EstadosPedido;
import com.vi.clasificados.dominio.Pedido;
import com.vi.clasificados.services.PedidoService;
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

@ManagedBean(name="pedidoController")
@SessionScoped
public class PedidoController {
    
    private Pedido pedido;
    private List<Pedido> pedidos;
    private List<Clasificado> clasificados;

    @EJB
    PedidoService pedidoService;
    
    private Integer estado = 1;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    private List<SelectItem> estados;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        String usr = FacesUtil.getUsuario();
        setPedidos(pedidoService.getPedidos(usr, new EstadosPedido(estado)));
        estados = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_ESTADOS_PEDIDOS));
    }
    
    public void cambiarEstado(ValueChangeEvent event){
        estado = (Integer) event.getNewValue();
        setPedidos(pedidoService.getPedidos(FacesUtil.getUsuario(), new EstadosPedido(estado)));
    }
    
    public String consultarClasificados(Pedido pedido){
        clasificados = pedidoService.getClasificados(pedido);
        this.pedido = pedido;
        return "/admon/clasificados_pedido.xhtml";
    }

    /**
     * @return the pedido
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * @param pedido the pedido to set
     */
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * @return the pedidos
     */
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * @param pedidos the pedidos to set
     */
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    /**
     * @return the estado
     */
    public Integer getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    /**
     * @return the estados
     */
    public List<SelectItem> getEstados() {
        return estados;
    }

    /**
     * @return the clasificados
     */
    public List<Clasificado> getClasificados() {
        return clasificados;
    }
    
}
