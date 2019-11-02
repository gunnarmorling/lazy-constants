package dev.morling.lazyconstant.asm;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.ConstantDynamic;
import net.bytebuddy.jar.asm.Handle;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

public class ValueProviderConstantDynamicCreator implements Opcodes {

    public static void main(final String[] args) throws Exception {
        System.out.println("CS: " + ValueProviderConstantDynamicCreator.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        final String outputClassName = "dev/morling/lazyconstant/ValueProviderConstantDynamic";
        try (FileOutputStream fos
                = new FileOutputStream(new File(ValueProviderConstantDynamicCreator.class.getProtectionDomain().getCodeSource().getLocation().getPath() + outputClassName + ".class"))) {
            fos.write(dump());
        }
    }

    public static byte[] dump () throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        MethodVisitor methodVisitor;

        classWriter.visit(V11, ACC_PUBLIC | ACC_SUPER, "dev/morling/lazyconstant/ValueProviderConstantDynamic", null, "java/lang/Object", new String[] { "dev/morling/lazyconstant/ValueProvider" });

        classWriter.visitSource("ValueProviderConstantDynamic.java", null);

        // <init>
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(3, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Ldev/morling/lazyconstant/ValueProviderConstantDynamic;", null, label0, label1, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }

        // getValue()
        {
            Handle bsm = new Handle(H_INVOKESTATIC, "java/lang/invoke/ConstantBootstraps", "invoke", MethodType.methodType(Object.class, Lookup.class, String.class, Class.class, MethodHandle.class, Object[].class).toMethodDescriptorString(), false);
            Handle delegate = new Handle(H_INVOKESTATIC, "dev/morling/lazyconstant/ValueProviderConstantDynamic", "doGetValue", MethodType.methodType(String.class).toMethodDescriptorString(), false);

            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "getValue", "()Ljava/lang/String;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(7, label0);
//            methodVisitor.visitLdcInsn("THE VALUE");
            methodVisitor.visitLdcInsn(new ConstantDynamic("name", "Ljava/lang/String;", bsm, delegate));
            methodVisitor.visitInsn(ARETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Ldev/morling/lazyconstant/ValueProviderConstantDynamic;", null, label0, label1, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }

        // doGetValue()
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "doGetValue", "()Ljava/lang/String;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(11, label0);
            methodVisitor.visitLdcInsn("THE_VALUE");
            methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "getenv", "(Ljava/lang/String;)Ljava/lang/String;", false);
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(1, 0);
            methodVisitor.visitEnd();
        }

        // main()
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(15, label0);
            methodVisitor.visitTypeInsn(NEW, "dev/morling/lazyconstant/ValueProviderConstantDynamic");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "dev/morling/lazyconstant/ValueProviderConstantDynamic", "<init>", "()V", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "dev/morling/lazyconstant/ValueProviderConstantDynamic", "run", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(16, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("args", "[Ljava/lang/String;", null, label0, label2, 0);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }

        // run()
        {
            methodVisitor = classWriter.visitMethod(ACC_PRIVATE, "run", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(19, label0);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "dev/morling/lazyconstant/ValueProviderConstantDynamic", "getValue", "()Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(20, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Ldev/morling/lazyconstant/ValueProviderConstantDynamic;", null, label0, label2, 0);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}