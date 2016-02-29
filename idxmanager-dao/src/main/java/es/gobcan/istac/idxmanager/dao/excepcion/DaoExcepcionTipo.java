package es.gobcan.istac.idxmanager.dao.excepcion;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.gobcan.istac.idxmanager.domain.util.LocaleUtil;

public enum DaoExcepcionTipo {
    DAO_GENERAL;

    private static final Map<DaoExcepcionTipo, String> MENSAJE_MAP = new HashMap<DaoExcepcionTipo, String>();
    static {
        MENSAJE_MAP.put(DaoExcepcionTipo.DAO_GENERAL, "excepcion.dao.general");
    }

    /**
     * Returns a localized message for this reason type and locale.
     *
     * @param locale The locale.
     * @return A localized message given a reason type and locale.
     */
    public String getMessageForReasonType(Locale locale) {
        return LocaleUtil.getLocalizedMessageFromBundle("i18n/mensajes-dao", MENSAJE_MAP.get(this), locale);
    }

    /**
     * Returns a message for this reason type in the default locale.
     *
     * @return A message message for this reason type in the default locale.
     */
    public String getMessageForReasonType() {
        return getMessageForReasonType(null);
    }

    /**
     * Returns a lower case string of this enum.
     *
     * @return a lower case string of this enum
     */
    public String lowerCaseString() {
        return this.toString().toLowerCase();
    }
}
