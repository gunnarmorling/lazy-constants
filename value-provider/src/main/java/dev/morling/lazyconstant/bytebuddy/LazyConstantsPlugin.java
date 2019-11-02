package dev.morling.lazyconstant.bytebuddy;

import static net.bytebuddy.matcher.ElementMatchers.annotationType;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

import net.bytebuddy.asm.MemberSubstitution;
import net.bytebuddy.asm.MemberSubstitution.Substitution;
import net.bytebuddy.build.Plugin;
import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationValue;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList.Generic;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.Implementation.Context;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.jar.asm.ConstantDynamic;
import net.bytebuddy.jar.asm.Handle;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.pool.TypePool;

public class LazyConstantsPlugin implements Plugin {

    @Override
    public boolean matches(TypeDescription target) {
        return target.getDeclaredAnnotations().isAnnotationPresent(Instrumented.class);
    }

    @Override
    public Builder<?> apply(Builder<?> builder, TypeDescription typeDescription, ClassFileLocator classFileLocator) {
        final String typeName = typeDescription.getDescriptor().substring(1, typeDescription.getDescriptor().length() - 1);
        System.out.println("TYPE: " + typeName);

        return builder
//            .visit(MemberSubstitution.strict()
//                .field(isAnnotatedWith(Lazy.class))
//                    .onWrite()
//                        .stub()
//                            .on(isTypeInitializer()))
//            .visit(MemberSubstitution.strict()
//                    .method(named("doGetValue"))
//                        .stub()
//                            .on(isTypeInitializer()))
             .visit(MemberSubstitution.strict()
                .field(isAnnotatedWith(Lazy.class))
                    .onRead()
                .replaceWith(new Substitution.Factory() {

                    @Override
                    public Substitution make(TypeDescription instrumentedType, MethodDescription instrumentedMethod,
                            TypePool typePool) {

                        return new Substitution() {

                            @Override
                            public StackManipulation resolve(TypeDescription targetType, ByteCodeElement target, Generic parameters,
                                    net.bytebuddy.description.type.TypeDescription.Generic result, int freeOffset) {

                                return new StackManipulation() {

                                    @Override
                                    public boolean isValid() {
                                        return true;
                                    }

                                    @Override
                                    public Size apply(MethodVisitor methodVisitor, Context implementationContext) {
                                        System.out.println("##### Target: " + target);

                                        MethodDescription.InDefinedShape bootstrap = TypeDescription.ForLoadedType.of(Lazy.class).getDeclaredMethods().filter(named("source")).getOnly();

                                        AnnotationDescription lazy = target.getDeclaredAnnotations().filter(annotationType(Lazy.class)).getOnly();
                                        AnnotationValue<?, ?> value = lazy.getValue(bootstrap);
                                        Handle bsm = new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/ConstantBootstraps", "invoke", MethodType.methodType(Object.class, Lookup.class, String.class, Class.class, MethodHandle.class, Object[].class).toMethodDescriptorString(), false);
                                        Handle delegate = new Handle(Opcodes.H_INVOKESTATIC, typeName, value.resolve(String.class), MethodType.methodType(String.class).toMethodDescriptorString(), false);

                                        methodVisitor.visitLdcInsn(new ConstantDynamic("my-constant", "Ljava/lang/String;", bsm, delegate));
                                        return StackSize.ZERO.toIncreasingSize();
                                    }
                                };
                            }
                        };
                    }
                })
                .on(any())
                )
                ;
      }

    @Override
    public void close() throws IOException {
        System.out.println("##### close() #####");
    }
}