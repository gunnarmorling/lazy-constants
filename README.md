# Lazy Constants

JDK 11 introduced a new constant pool entry type, `CONSTANT_dynamic`
(see [JEP 309](https://openjdk.java.net/jeps/309)).
This allows to load constants lazily,
which is useful for quicker class initialization.
The constant value is obtained from a bootstrap method upon first usage.
So far this functionality is solely present at the JVM level:
The `ldc` byte code operator has been expanded so it accepts a reference to a constant bootstrap method.
The feature has not surfaced in the Java language at this point.
But with some byte code hackery (using ByteBuddy and its Maven plug-in),
lazy constants can be implemented using the `CONSTANT_dynamic`.

## Example

Let there be the following class:

```
@Instrumented
public class LazyConstantDemo {

    @Lazy(source="doGetLazyValue")
    private static final String LAZY_VALUE = null;

    private static final String EAGER_VALUE = doGetEagerValue();

    public LazyConstantDemo() {
        out.println("LazyConstantDemo()");
    }

    public String getLazyValue() {
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
}
```

`@Instrumented` is the hint for a custom plug-in for the ByteBuddy Maven plug-in to process this class.
`@Lazy` is the hint to the same plug-in to replace all references to the annotated field with an `ldc` instruction, referencing the specified bootstrap method.
This will be invoked once upon first reference to the lazy constant.

So when running the following:

```
LazyConstantDemo vp = new LazyConstantDemo();

vp.getEagerValue();
vp.getLazyValue();
vp.getLazyValue();
```

Then you'll see the following output:

```
doGetEagerValue()
ValueProviderDemo()
doGetLazyValue()
```

Note how `doGetLazyValue()` is invoked only once upon first access to the `LAZY_VALUE` static final field.

## Performance Characteristics

There's a JMH benchmark in the _test-runner_ module.
It shows that the constant dynamic access has the same performance characteristics as regular static final access or the static holder pattern,
without incurring the eager initialization cost of the former or the additional metadata (inner class) of the latter.
