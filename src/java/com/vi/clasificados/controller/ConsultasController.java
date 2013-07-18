package com.vi.clasificados.controller;

import com.vi.comun.locator.ParameterLocator;
import com.vi.comun.util.Log;
import com.vi.locator.ComboLocator;
import com.vi.reportes.dominio.Reporte;
import com.vi.reportes.dto.ResultReporteDTO;
import com.vi.reportes.services.ReporteServicesLocal;
import com.vi.usuarios.controller.SessionController;
import com.vi.util.FacesUtil;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;



@ManagedBean(name="consultasController")
@SessionScoped
public class ConsultasController {
    
    private Date fecha;
    private Integer tipo;
    
    @EJB
    ReporteServicesLocal reporteService;
    ParameterLocator locator;
    ComboLocator comboLocator;
    
    Reporte reporte;
    
    //Para permitir la descarga de archivos
    private String rutaArchivo;
    private StreamedContent file;
    private boolean renderDownload = false;
    
    
    //Combos
    private List<SelectItem> tipos;
    
    @PostConstruct
    public void init(){
        locator = ParameterLocator.getInstance();
        comboLocator = ComboLocator.getInstance();
        tipos = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.ID_TIPOCLASIFICADO));
    }
    
    public String generar(){
        try {
            reporte = reporteService.find(Integer.parseInt(locator.getParameter("num_reporte")));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tipo", tipo);
            params.put("fecha", format.format(fecha));
            ResultReporteDTO resultado = reporteService.generarReporte(reporte, params);
            rutaArchivo = resultado.getRutaZip();
            setRenderDownload(true);
        } catch (Exception e) {
            Log.getLogger().log(Level.SEVERE, "Error al generar la consulta", e);
            FacesUtil.addMessage(FacesUtil.ERROR,"Error al generar la consulta");
        }
        
        return null;
    }
    
    public void descargar(ActionEvent evt)throws Exception{
        if(rutaArchivo == null){
            return;
        }
        FileInputStream stream = new FileInputStream(rutaArchivo);
        setFile(new DefaultStreamedContent(stream, "application/zip", reporte.getNombre().replaceAll("\\s", "")+".zip"));
        setRenderDownload(false);
    }

    /**
     * @return the fechaIni
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fechaIni the fechaIni to set
     */
    public void setFecha(Date fechaIni) {
        this.fecha = fechaIni;
    }

    /**
     * @return the renderDownload
     */
    public boolean isRenderDownload() {
        return renderDownload;
    }

    /**
     * @param renderDownload the renderDownload to set
     */
    public void setRenderDownload(boolean renderDownload) {
        this.renderDownload = renderDownload;
    }

    /**
     * @return the file
     */
    public StreamedContent getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(StreamedContent file) {
        this.file = file;
    }

    /**
     * @return the tipos
     */
    public List<SelectItem> getTipos() {
        return tipos;
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
    
}
