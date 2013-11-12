
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
    private StreamedContent image1;
    private StreamedContent image2;
    private StreamedContent image3;
    private StreamedContent image4;
    private int pagelinks = 0;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        image1 = null;
        image2 = null;
        image3 = null;
        image4 = null;
    }
    
    
    public String mostrarClasificado(Clasificado clasificado){
        init();
        this.clas1 = clasService.findWithImgs(clasificado.getId());
        pagelinks = this.clas1.getImagenes().size();
        if(getPagelinks() >= 1){
            image1 = this.clas1.getImagenes().get(0).getPrimeImg();
        }
        if(getPagelinks() >= 2){
            image2 = this.clas1.getImagenes().get(1).getPrimeImg();
        }
        if(getPagelinks() >= 3){
            image3 = this.clas1.getImagenes().get(2).getPrimeImg();
        }
        if(getPagelinks() >= 4){
            image4 = this.clas1.getImagenes().get(3).getPrimeImg();
        }
        this.usuario = usrService.findByUser(clasificado.getPedido().getUsuario());
        return "/consultas/detalle.xhtml";
    }
    
    public StreamedContent getImage()throws Exception{
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = contextClassLoader.getResourceAsStream("a1.png");
        StreamedContent defaultFileContent = new DefaultStreamedContent(inputStream, "image/png");
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String imageID = externalContext.getRequestParameterMap().get("id");
        
        if(imageID == null){
            return defaultFileContent;
        }
        
        ImgClasificadoTO imgTO = clas1.getImagenes().get(Integer.parseInt(imageID));
        System.out.println("Varios: Ingreso por: "+imageID+" - "+imgTO.getRutaImg());
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
     * @return the image1
     */
    public StreamedContent getImage1() {
        return image1;
    }

    /**
     * @return the image2
     */
    public StreamedContent getImage2() {
        return image2;
    }

    /**
     * @return the image3
     */
    public StreamedContent getImage3() {
        return image3;
    }

    /**
     * @return the image4
     */
    public StreamedContent getImage4() {
        return image4;
    }

    /**
     * @return the pagelinks
     */
    public int getPagelinks() {
        return pagelinks;
    }


}
