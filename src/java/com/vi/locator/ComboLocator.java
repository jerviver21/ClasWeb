package com.vi.locator;

import com.vi.comun.locator.ParameterLocator;
import com.vi.comun.services.CommonServicesLocal;
import com.vi.comun.util.Utils;  
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;

/**
 * @author jerviver21
 */
public class ComboLocator {
    //Identificadores de cache para combos
    public static int PARAMETROS = 0;
    public static int COMB_ID_MENU = 1;
    public static int COMB_ID_GRUPO = 2;
    public static int COMB_ID_ROL = 3;
    public static int COMB_ID_TIPOID = 4;
    public static int COMB_ID_IDIOMA = 5;
    public static int COMB_ID_TIPOPUB = 6;
    public static int COMB_ID_ENTIDAD = 7;
    
    public static int COMB_ID_STEMPLEO = 8;
    public static int COMB_ID_STAREAEMPLEO = 9;
    public static int COMB_ID_STRANGOSAL = 10;
    
    public static int COMB_ID_STTIPOOFERIMB = 11;
    public static int COMB_ID_STTIPOINM = 12;
    public static int COMB_ID_STUBICACION = 13;
    public static int COMB_ID_STAREASINM = 14;
    public static int COMB_ID_STRANGOPRECIMB = 15;
    
    public static int COMB_ID_STTIPOSVEH = 16;
    public static int COMB_ID_STMARCASVEH = 17;
    public static int COMB_ID_STPRECIOSVEH = 18;
    
    public static int COMB_ID_STTIPOSVAR = 19;
    

    
    private Map cache;
    private CommonServicesLocal commonFacade;
    private static ComboLocator instance;
    
    
    private ComboLocator()throws Exception{
        try {
            InitialContext contexto = new InitialContext();
            commonFacade = (CommonServicesLocal)contexto.lookup(Utils.getPropiedad("jndi_common"));
            cache = Collections.synchronizedMap(new HashMap());
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace(System.err);
        }
    }
    
    public static ComboLocator getInstance(){
        if(instance == null){
            try {
                instance = new ComboLocator();
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace(System.err);
            }
        }
        return instance;
    }
    

    public Map getDataForCombo(int TABLA){
        Map resultado = (Map)getCache().get(TABLA);
        if(resultado == null){
            if(TABLA == COMB_ID_MENU){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, nombre FROM menu");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_GRUPO){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, codigo FROM groups");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_ROL){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, codigo FROM rol");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_TIPOID){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, codigo FROM tipo_id");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_IDIOMA){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, nombre FROM idiomas ");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_TIPOPUB){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, nombre FROM tipo_publicacion ");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_ENTIDAD){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, nombre FROM entidades_pago ");
                getCache().put(TABLA, resultado);
            }else if(TABLA == PARAMETROS){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT nombre, valor FROM parametro");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STEMPLEO){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=2 AND subtipo = 1");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STAREAEMPLEO){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=2 AND subtipo = 2");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STRANGOSAL){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=2 AND subtipo = 3");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STTIPOOFERIMB){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=1 AND subtipo = 1");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STTIPOINM){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=1 AND subtipo = 2");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STUBICACION){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=1 AND subtipo = 3");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STAREASINM){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=1 AND subtipo = 4");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STRANGOPRECIMB){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=1 AND subtipo = 5");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STTIPOSVEH){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=3 AND subtipo = 1");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STMARCASVEH){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=3 AND subtipo = 2");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STPRECIOSVEH){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=3 AND subtipo = 3");
                getCache().put(TABLA, resultado);
            }else if(TABLA == COMB_ID_STTIPOSVAR){
                resultado = getCommonFacade().getReferenceTableForCombo("SELECT id, dato FROM tipo_clasificado WHERE id_padre=4 AND subtipo = 1");
                getCache().put(TABLA, resultado);
            }
        }
        return resultado;
    }

    public Map getDataForSubcombo(int TABLA){
        Map resultado = (Map)cache.get(TABLA);
        if(resultado == null){
            /*if(TABLA == SUBC_SECTOR_EQUIPO){
                resultado = commonFacade.getReferenceTableForSubcombo("SELECT sec.id as ID_SEC, eq.id as ID_EQ, eq.nombre as NOMBRE "
                        + "FROM equipo eq, sector sec "
                        + "WHERE eq.id_sector = sec.id ");
                cache.put(TABLA, resultado);
            }*/
        }
        return resultado;
    }
    
    public void restartCache(){
        cache = new HashMap();
    }

    /**
     * @return the cache
     */
    public Map getCache() {
        return cache;
    }

    /**
     * @return the commonFacade
     */
    public CommonServicesLocal getCommonFacade() {
        return commonFacade;
    }
    
    
    
    
    
}
