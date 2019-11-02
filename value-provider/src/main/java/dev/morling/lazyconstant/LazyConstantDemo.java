package dev.morling.lazyconstant;

import static java.lang.System.out;

import dev.morling.lazyconstant.bytebuddy.Instrumented;
import dev.morling.lazyconstant.bytebuddy.Lazy;

@Instrumented
public class LazyConstantDemo {

    @Lazy(source="doGetLazyValue")
    private static final String LAZY_VALUE = null;

    private static final String EAGER_VALUE = doGetEagerValue();

    public LazyConstantDemo() {
        out.println("LazyConstantDemo()");
    }

    public String getLazyValue() {
        out.println("getLazyValue()");
        return LAZY_VALUE;
    }

    public String getEagerValue() {
        return EAGER_VALUE;
    }

    private static String doGetLazyValue() {
        out.println("doGetLazyValue()");
        return System.getenv("LAZY_VALUE");
    }

    private static String doGetEagerValue() {
        out.println("doGetEagerValue()");
        return System.getenv("EAGER_VALUE");
    }

    public static void main(String... args) {
        LazyConstantDemo vp = new LazyConstantDemo();
        vp.getEagerValue();
        vp.getLazyValue();
        vp.getLazyValue();
    }
}
