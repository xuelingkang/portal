package com.xzixi.self.portal.enhance.annotation.processor;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.xzixi.self.portal.enhance.annotation.CacheEnhance;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author 薛凌康
 */
@SupportedAnnotationTypes("com.xzixi.self.portal.enhance.annotation.CacheEnhance")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CacheEnhanceProcessor extends AbstractBaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 遍历所有@CacheEnhance标注的类
        for (Element element: roundEnv.getElementsAnnotatedWith(CacheEnhance.class)) {
            // 类定义
            JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) elements.getTree(element);

            // 当前类对应的实体类的类名
            String modelClassName = ((JCTree.JCTypeApply) classDecl.extending.getTree()).arguments.get(1).type.toString();
            // 修改类，追加方法
            classDecl.defs = classDecl.defs.append(getByIdDecl(modelClassName));
        }
        return true;
    }

    /**
     * getById方法
     */
    private JCTree.JCMethodDecl getByIdDecl(String modelClassName) {
        // 访问修饰词和注解列表
        // TODO 配置缓存注解，参考JCAssign和TreeMaker第832行
        List<JCTree.JCAnnotation> annotationList = List.nil();
        JCTree.JCAnnotation overrideAnnotation = treeMaker.Annotation(expression("java.lang.Override"), List.nil());
        annotationList = annotationList.append(overrideAnnotation);
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = names.fromString("getById");
        // 返回值类型
        JCTree.JCExpression returnType = expression(modelClassName);
        // 泛型参数列表
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.nil();
        JCTree.JCVariableDecl param = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), names.fromString("id"), expression("java.io.Serializable"), null);
        parameters = parameters.append(param);
        // 抛出异常
        List<JCTree.JCExpression> throwsClauses = List.nil();
        // 方法体
        List<JCTree.JCStatement> statementList = List.nil();
        List<JCTree.JCExpression> args = List.<JCTree.JCExpression>nil().append(expression("id"));
        JCTree.JCStatement returnStatement = treeMaker.Return(treeMaker.Apply(null, expression("super.getById"), args));
        statementList = statementList.append(returnStatement);
        JCTree.JCBlock block = treeMaker.Block(0, statementList);
        return treeMaker.MethodDef(modifiers, name, returnType, methodGenericParams, parameters, throwsClauses, block, null);
    }
}
