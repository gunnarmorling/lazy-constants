package dev.morling.lazyconstant;

public class ValueProviderConstantDynamicTemplate implements ValueProvider {

    @Override
    public String getValue() {
        return "THE VALUE";
    }

    public static String doGetValue() {
        return System.getenv("THE_VALUE");
    }

    public static void main(String[] args) {
        new ValueProviderConstantDynamicTemplate().run();
    }

    private void run() {
        System.out.println(getValue());
    }
}
