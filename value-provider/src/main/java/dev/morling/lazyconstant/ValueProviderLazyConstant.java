package dev.morling.lazyconstant;

import dev.morling.lazyconstant.bytebuddy.Instrumented;
import dev.morling.lazyconstant.bytebuddy.Lazy;

@Instrumented
public class ValueProviderLazyConstant implements ValueProvider {

    @Lazy(source="doGetValue")
    private static final String VALUE = null;

    @Override
    public String getValue() {
        return VALUE;
    }

    private static String doGetValue() {
        return System.getenv("THE_VALUE");
    }
}
