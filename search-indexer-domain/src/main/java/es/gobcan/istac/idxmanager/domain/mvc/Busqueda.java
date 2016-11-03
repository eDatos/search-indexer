package es.gobcan.istac.idxmanager.domain.mvc;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class Busqueda {

    // Búsqueda
    private String userQuery;

    // Search Type
    private String searchType;

    // Búsqueda avanzada
    private String filtroTexto;
    private String filtroTextoQuery;
    private String filtroSeccion;
    private String filtroSeccionArea;
    private String filtroSeccionOperacion;

    // Control
    private String startResult = "0";
    private static final String ALL = "ALL";
    private String pestania = ALL;
    private String sort = null;

    // Facetes
    private String svyCodFF = null;
    private String subCodFF = null;
    private String cvgTCodFF = null;
    private String cvgSCodFF = null;
    private String formato = null;

    // Facets Seleccionada para ampliar informacion
    private String ff_select = null;

    public Busqueda() {
    }

    public Busqueda(Busqueda source) {
        // Búsqueda
        setUserQuery(source.getUserQuery());

        // Search Type
        setSearchType(source.getSearchType());

        // Búsqueda avanzada
        setFiltroTexto(source.getFiltroTexto());
        setFiltroTextoQuery(source.getFiltroTextoQuery());
        setFiltroSeccion(source.getFiltroSeccion());
        setFiltroSeccionArea(source.getFiltroSeccionArea());
        setFiltroSeccionOperacion(source.getFiltroSeccionOperacion());

        // Control
        setStartResult(source.getStartResult());
        setPestania(source.getPestania());
        setSort(source.getSort());

        // Facets
        setCvgSCodFF(source.getCvgSCodFF());
        setCvgTCodFF(source.getCvgTCodFF());
        setSvyCodFF(source.getSvyCodFF());
        setSubCodFF(source.getSubCodFF());
        setFormato(source.getFormato());

        // Facets Seleccionada para ampliar informacion
        setFf_select(source.getFf_select());
    }

    /**************************************************************************
     * GETTERS/SETTERS
     *************************************************************************/

    public String getUserQuery() {
        return userQuery;
    }

    public void setUserQuery(String userQuery) {
        this.userQuery = userQuery;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void setFiltroTexto(String filtroTexto) {
        this.filtroTexto = filtroTexto;
    }

    public String getFiltroTexto() {
        return filtroTexto;
    }

    public String getFiltroTextoQuery() {
        return filtroTextoQuery;
    }

    public void setFiltroTextoQuery(String filtroTextoQuery) {
        this.filtroTextoQuery = filtroTextoQuery;
    }

    public void setFiltroSeccion(String filtroSeccion) {
        this.filtroSeccion = filtroSeccion;
    }

    public String getFiltroSeccion() {
        return filtroSeccion;
    }

    public String getFiltroSeccionArea() {
        return filtroSeccionArea;
    }

    public void setFiltroSeccionArea(String filtroSeccionArea) {
        this.filtroSeccionArea = filtroSeccionArea;
    }

    public String getFiltroSeccionOperacion() {
        return filtroSeccionOperacion;
    }

    public void setFiltroSeccionOperacion(String filtroSeccionOperacion) {
        this.filtroSeccionOperacion = filtroSeccionOperacion;
    }

    public String getStartResult() {
        return startResult;
    }

    public void setStartResult(String startResult) {
        this.startResult = startResult;
    }

    public String getPestania() {
        if (StringUtils.isEmpty(pestania)) {
            return ALL;
        }
        return pestania;
    }

    public void setPestania(String pestania) {
        this.pestania = pestania;
    }

    public void setFf_select(String ff_select) {
        this.ff_select = unScapeHtml(ff_select);
    }

    public String getFf_select() {
        return ff_select;
    }

    public String getCvgSCodFF() {
        return cvgSCodFF;
    }

    public void setCvgSCodFF(String cvgSCodFF) {
        this.cvgSCodFF = cvgSCodFF;
    }

    public String getCvgTCodFF() {
        return cvgTCodFF;
    }

    public void setCvgTCodFF(String cvgTCodFF) {
        this.cvgTCodFF = cvgTCodFF;
    }

    public String getSvyCodFF() {
        return svyCodFF;
    }

    public void setSvyCodFF(String svyCodFF) {
        this.svyCodFF = svyCodFF;
    }

    public String getSubCodFF() {
        return subCodFF;
    }

    public void setSubCodFF(String subCodFF) {
        this.subCodFF = subCodFF;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formatoFf) {
        formato = formatoFf;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return sort;
    }

    /**************************************************************************
     * HELPERS
     **************************************************************************/

    /**
     * Util invoked in JSP pages for render or not, the tag cloud
     *
     * @return
     */
    public boolean isFilterFacetSelected() {
        boolean result = false;
        result = result || !StringUtils.isEmpty(subCodFF);
        result = result || !StringUtils.isEmpty(svyCodFF);
        result = result || !StringUtils.isEmpty(cvgTCodFF);
        result = result || !StringUtils.isEmpty(cvgSCodFF);
        result = result || !StringUtils.isEmpty(formato);

        return result;
    }

    public String unScapeHtml(String campo) {
        return StringEscapeUtils.unescapeHtml(campo);
    }

}
