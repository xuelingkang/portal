package com.xzixi.self.portal.compile.annotation.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author 薛凌康
 */
@SupportedAnnotationTypes("com.xzixi.self.portal.compile.annotation.BaseCache")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BaseCacheProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        for (TypeElement te : annotations) {
            messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + te.toString());
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.toString());
            }
        }
        return true;
    }
}
