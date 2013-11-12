
package com.vi.clasificados.consultas.controller;

import com.vi.clasificados.locator.ClasificadosCachingLocator;
import com.vi.clasificados.dominio.Clasificado;
import com.vi.clasificados.services.ClasificadosService;
import com.vi.clasificados.to.ImgClasificadoTO;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.io.InputStream;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name="variosController")
@RequestScoped
public class VariosController {
    private List<Clasificado> clasificados;
    private int tipo = 0;  
    private List<SelectItem> tipos;
    
    
    @EJB
    ClasificadosService service;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    
    //Imagenes
    private StreamedContent image;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        clasificados = service.getFiltro(ClasificadosCachingLocator.VARIOS, getTipo());
        tipos = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.VAR_TIPO));
    }
    
    public String cambiarFiltro() {
        clasificados = service.getFiltro(ClasificadosCachingLocator.VARIOS, getTipo());
        return null;
    }

    //Métodos que permiten renderizar las imagenes.
    public StreamedContent getImage()throws Exception{
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = contextClassLoader.getResourceAsStream("a1.png");
        StreamedContent defaultFileContent = new DefaultStreamedContent(inputStream, "image/png");
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String imageID = externalContext.getRequestParameterMap().get("id");
        System.out.println("Varios: Ingreso por: "+imageID);
        if(imageID == null){
            return defaultFileContent;
        }
        Clasificado clasificado = service.findWithImgs(Long.parseLong(imageID));
        
        ImgClasificadoTO imgTO = clasificado.getImagenes().get(0);
        image = imgTO.getPrimeImg();
        System.out.println("Varios: Cargado: "+imgTO.getRutaImg()+" - Consecutivo: "+imageID);
        return image;
    }
    
     public void setImage(StreamedContent image)
     {
         this.image = image;
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
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the tipos
     */
    public List<SelectItem> getTipos() {
        return tipos;
    }

   
    
}
