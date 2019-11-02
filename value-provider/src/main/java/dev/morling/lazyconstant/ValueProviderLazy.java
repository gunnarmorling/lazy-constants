package dev.morling.lazyconstant;

public class ValueProviderLazy implements ValueProvider {

    private volatile String value;

    @Override
    public String getValue() {
        String value = this.value;
        if (value == null) {
          synchronized (this) {
            value = this.value;
              if (value == null) {
                value = System.getenv("THE_VALUE");
              }
           }
         }
         return value;
    }
}
