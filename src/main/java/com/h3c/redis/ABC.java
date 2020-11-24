package com.h3c.redis;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class ABC {


    public static void main(String[] args) {
        TypeVariable<Class<TypeVariableSample>>[] typeParameters = TypeVariableSample.class.getTypeParameters();

        for (int i = 0; i < typeParameters.length; i++) {
            TypeVariable<Class<TypeVariableSample>> typeVariable = typeParameters[i];
            System.out.println(typeVariable.getName());
            System.out.println(typeVariable.getGenericDeclaration());

            Type[] bounds = typeVariable.getBounds();
            for (int j = 0; j < bounds.length; j++) {
                Type bound = bounds[j];
                System.out.println(bound.getTypeName());
            }
            AnnotatedType[] annotatedBounds = typeVariable.getAnnotatedBounds();
            for (int j = 0; j < annotatedBounds.length; j++) {
                StringBuilder sb = new StringBuilder();
                AnnotatedType annotatedType = annotatedBounds[j];
                System.out.println("AnnotatedType:" + annotatedType.getType());
                Annotation[] annotations = annotatedType.getDeclaredAnnotations();
                for (int k = 0; k < annotations.length; k++) {
                    Annotation annotation = annotations[k];
                    sb.append(annotation);
                }
                sb.append(" "+ annotatedType.getType().getTypeName());
                System.out.println(sb.toString());
            }
        }
    }

}

class TypeVariableSample<T extends Serializable> {

}
