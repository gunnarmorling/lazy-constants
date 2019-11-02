package dev.morling.lazyconstant;

public class ValueProviderEagerFinal implements ValueProvider {

    private final String VALUE = System.getenv("THE_VALUE");

    @Override
    public String getValue() {
        return VALUE;
    }
}
