/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vi.clasificados.controller;

import com.vi.clasificados.dominio.Pedido;
import com.vi.clasificados.services.PedidoService;
import com.vi.comun.exceptions.ValidacionException;
import com.vi.comun.util.Log;
import com.vi.usuarios.dominio.Users;
import com.vi.usuarios.services.UsuariosServicesLocal;
import com.vi.util.FacesUtil;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jerviver21
 */
@ManagedBean(name="pagosController")
@SessionScoped
public class PagosController {
    
    private String codPago;
    
    private boolean verDetalle = false;
    
    private Pedido pedido;
    private Users usuario;
    
    @EJB
    PedidoService service;
    
    @EJB
    UsuariosServicesLocal usrService;
    
    
    public String simularPago(){
        try {
           service.simularPago(codPago);
           service.recibirPago();
           FacesUtil.addMessage(FacesUtil.INFO, "Pago simulado con exito!!");
        } catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al procesar el pago");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    
    public String consultarPago(){
        try {
           pedido = service.findByNro(codPago);
           if(pedido == null){
               FacesUtil.addMessage(FacesUtil.ERROR, "No existe un pago con el código "+codPago);
               verDetalle = false;
               return null;
           }
           usuario = usrService.findByUser(pedido.getUsuario());
           verDetalle = true;
        } catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al procesar el pago");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    
    public String realizarPago(){
        try {
           service.realizarPago(codPago);
           FacesUtil.addMessage(FacesUtil.INFO, "Pago realizado con exito!");
        } catch (ValidacionException e) {
            FacesUtil.addMessage(FacesUtil.ERROR, e.getMessage());
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al procesar el pago");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    

    /**
     * @return the codPago
     */
    public String getCodPago() {
        return codPago;
    }

    /**
     * @param codPago the codPago to set
     */
    public void setCodPago(String codPago) {
        this.codPago = codPago;
    }

    /**
     * @return the verDetalle
     */
    public boolean isVerDetalle() {
        return verDetalle;
    }

    /**
     * @return the pedido
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * @return the usuario
     */
    public Users getUsuario() {
        return usuario;
    }

    
}
