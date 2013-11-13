
package com.vi.clasificados.consultas.controller;

import com.vi.clasificados.dominio.Clasificado;
import com.vi.clasificados.services.ClasificadosService;
import com.vi.clasificados.to.ImgClasificadoTO;
import com.vi.locator.ComboLocator;
import com.vi.usuarios.dominio.Users;
import com.vi.usuarios.services.UsuariosServicesLocal;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name="detalleController")
@SessionScoped
public class DetalleController {
    private Clasificado clas1;
    private Users usuario;
    
    @EJB
    UsuariosServicesLocal usrService;
    
    @EJB
    ClasificadosService clasService;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    //Imagenes
    private int pagelinks = 0;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
    }
    
    
    public String mostrarClasificado(Clasificado clasificado){
        init();
        this.clas1 = clasService.findWithImgs(clasificado.getId());
        pagelinks = this.clas1.getImagenes().size();
        this.usuario = usrService.findByUser(clasificado.getPedido().getUsuario());
        System.out.println("Ingreso a mostrar clasificado: "+this.clas1.getImagenes().size());
        return "/consultas/detalle.xhtml";
    }
    
    public StreamedContent getImage()throws Exception{
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = contextClassLoader.getResourceAsStream("a1.png");
        StreamedContent defaultFileContent = new DefaultStreamedContent(inputStream, "image/png");
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String imageID = externalContext.getRequestParameterMap().get("id");
        System.out.println("Detalle: Consulta imagen: "+imageID);
        if(imageID == null){
            return defaultFileContent;
        }
        
        ImgClasificadoTO imgTO = clas1.getImagenes().get(Integer.parseInt(imageID));
        System.out.println("Detalle: Ingreso por: "+imageID+" - "+imgTO.getRutaImg());
        return imgTO.getPrimeImg();
    }

    /**
     * @return the clasificado
     */
    public Clasificado getClas1() {
        return clas1;
    }

    /**
     * @return the usuario
     */
    public Users getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Users usuario) {
        this.usuario = usuario;
    }


    /**
     * @return the pagelinks
     */
    public int getPagelinks() {
        return pagelinks;
    }


}
