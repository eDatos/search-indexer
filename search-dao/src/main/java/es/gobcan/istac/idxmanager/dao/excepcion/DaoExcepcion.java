package es.gobcan.istac.idxmanager.dao.excepcion;

/**
 * Excepcion para ser usada por los metodos de la librería de DAO Cada excepción tiene un tipo <code>ExcepcionTipo</code> y un mensaje.
 */

public class DaoExcepcion extends Exception {

    private static final long serialVersionUID = -2548661408533934349L;

    private DaoExcepcionTipo reasonType;
    private String messageToUser = null;

    /**
     * Constructor privado para prevenir construir excepciones sin mensajes.
     */
    private DaoExcepcion() {
    }

    public DaoExcepcion(DaoExcepcionTipo reasonType, String messageToUser) {
        super(messageToUser);
        this.messageToUser = messageToUser;
        this.reasonType = reasonType;
    }

    /**
     * El mensaje por defecto es el definido en el tipo de la excepción.
     *
     * @param reasonType
     */
    public DaoExcepcion(DaoExcepcionTipo reasonType) {
        super(reasonType.getMessageForReasonType());
        this.messageToUser = reasonType.getMessageForReasonType();
        this.reasonType = reasonType;
    }

    public String getMessageToUser() {
        return this.messageToUser;
    }

    public DaoExcepcionTipo getReasonType() {
        return this.reasonType;
    }

    @Override
    @Deprecated
    public String getMessage() {
        return super.getMessage();
    }
}
