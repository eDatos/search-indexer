package es.gobcan.istac.search.core.utils;

import java.util.Locale;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;

public final class SearchLocaleUtil {

    private SearchLocaleUtil() {

    }

    public static String getTranslatedMessage(String message) throws MetamacException {
        Locale locale = getConfigurationService().retrieveLanguageDefaultLocale();
        return LocaleUtil.getMessageForCode(message, locale);
    }

    private static SearchConfigurationService getConfigurationService() {
        return (SearchConfigurationService) ApplicationContextProvider.getApplicationContext().getBean("configurationService");
    }

}
