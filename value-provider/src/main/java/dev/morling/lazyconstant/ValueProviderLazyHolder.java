package dev.morling.lazyconstant;

public class ValueProviderLazyHolder implements ValueProvider {

    private static class Holder {
        private static final String value = System.getenv("THE_VALUE");
    }
    @Override
    public String getValue() {
        return Holder.value;
    }
}
