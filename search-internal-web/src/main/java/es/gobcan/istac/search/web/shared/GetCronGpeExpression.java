package es.gobcan.istac.search.web.shared;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetCronGpeExpression {

    @Out(1)
    String cronGpeExpression;

}
