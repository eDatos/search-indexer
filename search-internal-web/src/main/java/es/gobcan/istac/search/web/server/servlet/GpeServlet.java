package es.gobcan.istac.search.web.server.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;

import es.gobcan.istac.jaxi.pxservice.api.dominio.NucleoMetadatos;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerService;
import es.gobcan.istac.search.core.idxmanager.service.util.ApplicationContextProvider;

/**
 * Clase que recibe un HttpPost con una serializacion del bean del nucleo de metadatos del istac.
 *
 * @author arte
 */
public class GpeServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean hayError = false;

        /*
         * Deserializacion del objeto enviado
         */
        String direccion = req.getHeader("direccion");
        if (direccion.equals("entrada")) {
            XStream xstream = new XStream();
            ObjectInputStream in = xstream.createObjectInputStream(req.getInputStream());
            NucleoMetadatos nucleoMetadatos = null;

            try {
                nucleoMetadatos = (NucleoMetadatos) in.readObject();
            } catch (ClassNotFoundException e) {
                hayError = true;
            }

            /*
             * Indexacion del objeto enviado
             */

            if (!hayError) {
                NucleoIstacIndexerService nucleoIstacIndexerService = (NucleoIstacIndexerService) ApplicationContextProvider.getApplicationContext().getBean("nucleoIstacIndexerServiceImpl");

                try {
                    nucleoIstacIndexerService.indexarElementoGPE(nucleoMetadatos, req.getHeader("formatoRecurso"));
                    nucleoIstacIndexerService.commitANDoptimize();
                } catch (ServiceExcepcion e) {
                    hayError = true;
                }
            }
        } else if (direccion.equals("salida")) {
            ObjectInputStream in = new ObjectInputStream(req.getInputStream());
            String id = null;

            try {
                id = (String) in.readObject();
            } catch (ClassNotFoundException e) {
                hayError = true;
            }

            /*
             * DesIndexacion del objeto enviado
             */

            NucleoIstacIndexerService nucleoIstacIndexerService = (NucleoIstacIndexerService) ApplicationContextProvider.getApplicationContext().getBean("nucleoIstacIndexerServiceImpl");

            try {
                nucleoIstacIndexerService.eliminarElemento(id);
                nucleoIstacIndexerService.commitANDoptimize();
            } catch (ServiceExcepcion e) {
                hayError = true;
            }
        }

        /*
         * Respuesta satisfactoria/insatisfactoria
         */

        resp.setContentType("text/html");
        if (!hayError) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}