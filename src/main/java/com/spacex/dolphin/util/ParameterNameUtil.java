package com.spacex.dolphin.util;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ParameterNameUtil {
    public static String[] getMethodParameterNamesByAsm(Class<?> clazz, Method method) {
        final Class<?>[] paramerTypes = method.getParameterTypes();

        if (paramerTypes == null || paramerTypes.length == 0) {
            return null;
        }

        final Type[] types = new Type[paramerTypes.length];
        for (int i = 0; i < paramerTypes.length; i++) {
            types[i] = Type.getType(paramerTypes[i]);
        }

        final String[] parameterNames = new String[paramerTypes.length];
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(".");
        className = className.substring(lastDotIndex + 1) + ".class";

        InputStream is = clazz.getResourceAsStream(className);

        try {
            ClassReader classReader = new ClassReader(is);
            classReader.accept(new ClassVisitor(Opcodes.ASM4) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    Type[] argumentTypes = Type.getArgumentTypes(desc);
                    if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
                        return null;
                    }

                    return new MethodVisitor(Opcodes.ASM4) {
                        @Override
                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                            //super.visitLocalVariable(name, desc, signature, start, end, index);

                            if (Modifier.isStatic(method.getModifiers())) {
                                parameterNames[index] = name;
                            } else if (index > 0) {
                                parameterNames[index - 1] = name;
                            }
                        }
                    };
                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parameterNames;
    }

    private static boolean matchTypes() {
        return true;
    }
}
