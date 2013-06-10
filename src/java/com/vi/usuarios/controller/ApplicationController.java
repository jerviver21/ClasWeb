
package com.vi.usuarios.controller;

import com.vi.comun.locator.ParameterLocator;
import com.vi.comun.services.CommonServicesLocal;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;

/**
 * @author Jerson Viveros
 */
@ManagedBean(name="applicationController" ,eager=true)
@ApplicationScoped
public class ApplicationController {
    ParameterLocator locator;
    
    //Roles que condicionan los componentes de la vista
    private String ROL_MASTER;
    
    //Define la plantilla y layout de la aplicacion

    private String p1 = "../plantilla1.xhtml";
    private String p2 = "../plantilla2.xhtml";
    private String p3 = "../plantilla3.xhtml";
    private String plantilla = p3;
    private String plantillaIndex = p3;
    private Map<String, String> themes; 
    private String theme = "bluesky";
    private String url;
    

    @EJB
    private CommonServicesLocal commonServices;
    

    @PostConstruct
    public void init(){
        locator = ParameterLocator.getInstance();
        System.out.println("Iniciando la aplicación MH System...");
        commonServices.updateEstructuraMenus();
        System.out.println("Menus actualizados...");
        //licenciaServices.initTimer();
        //System.out.println("Timer iniciado...");
        url = locator.getParameter("url");

        ROL_MASTER = locator.getParameter("rolMaster");
        
        themes = new TreeMap<String, String>();  
        themes.put("Afterdark", "afterdark");  
        themes.put("Casablanca", "casablanca");  
        themes.put("South-Street", "south-street");  
        themes.put("UI-Lightness", "ui-lightness");  
        themes.put("Blue-Sky", "bluesky"); 
        themes.put("Afternoon", "afternoon"); 
        themes.put("Cupertino", "cupertino"); 
        themes.put("Redmond", "redmond"); 
        themes.put("Glass-X", "glass-x"); 
    }
    
    
    public void cambiarTema(ValueChangeEvent event){
        System.out.println("Nuevo tema: "+event.getNewValue());
        theme = (String)event.getNewValue();
    }
    
    public String preCargar(){
        return null;
    }
    
    public String cambiarLookandfeel(){
        return null;
    }


    /**
     * @return the ROL_MASTER
     */
    public String getROL_MASTER() {
        return ROL_MASTER;
    }   

    /**
     * @return the plantilla
     */
    public String getPlantilla() {
        return plantilla;
    }
    
    

    /**
     * @param plantilla the plantilla to set
     */
    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    /**
     * @return the p1
     */
    public String getP1() {
        return p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(String p1) {
        this.p1 = p1;
    }

    /**
     * @return the p2
     */
    public String getP2() {
        return p2;
    }

    /**
     * @param p2 the p2 to set
     */
    public void setP2(String p2) {
        this.p2 = p2;
    }



    /**
     * @return the themes
     */
    public Map<String, String> getThemes() {
        return themes;
    }

    /**
     * @param themes the themes to set
     */
    public void setThemes(Map<String, String> themes) {
        this.themes = themes;
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * @param plantillaIndex the plantillaIndex to set
     */
    public void setPlantillaIndex(String plantillaIndex) {
        this.plantillaIndex = plantillaIndex;
    }
    
    public String getPlantillaIndex() {
        return plantilla.replaceAll("(.*plantilla)(\\d.xhtml)", "$1I$2");
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
