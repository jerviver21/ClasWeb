
package com.vi.clasificados.controller;

import com.vi.clasificados.dominio.Pedido;
import com.vi.clasificados.services.PedidoService;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jerviver21
 */

@ManagedBean(name="pedidoController")
@SessionScoped
public class PedidoController {
    
    private Pedido pedido;
    private List<Pedido> pedidos;

    @EJB
    PedidoService pedidoService;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        String usr = FacesUtil.getUsuario();
        setPedidos(pedidoService.getPedidosActivos(usr));
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
    
}
