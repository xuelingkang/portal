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
import javax.tools.Diagnostic;
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
//            classDecl.defs = classDecl.defs.append(getByIdDecl(modelClassName));
            for (JCTree tree: classDecl.defs) {
                if (!(tree instanceof JCTree.JCMethodDecl)) {
                    continue;
                }
                if (!"listByIds".equals(((JCTree.JCMethodDecl) tree).name.toString())) {
                    continue;
                }
                messager.printMessage(Diagnostic.Kind.NOTE, "method: " + tree.toString());
//                messager.printMessage(Diagnostic.Kind.NOTE, "mods: \n" + ((JCTree.JCMethodDecl) tree).mods);
//                messager.printMessage(Diagnostic.Kind.NOTE, "name: \n" + ((JCTree.JCMethodDecl) tree).name);
//                messager.printMessage(Diagnostic.Kind.NOTE, "restype: \n" + ((JCTree.JCMethodDecl) tree).restype);
//                messager.printMessage(Diagnostic.Kind.NOTE, "typarams: \n" + ((JCTree.JCMethodDecl) tree).typarams);
//                messager.printMessage(Diagnostic.Kind.NOTE, "recvparam: \n" + ((JCTree.JCMethodDecl) tree).recvparam);
//                messager.printMessage(Diagnostic.Kind.NOTE, "params: \n" + ((JCTree.JCMethodDecl) tree).params);
//                messager.printMessage(Diagnostic.Kind.NOTE, "thrown: \n" + ((JCTree.JCMethodDecl) tree).thrown);
//                messager.printMessage(Diagnostic.Kind.NOTE, "body: \n" + ((JCTree.JCMethodDecl) tree).body);
//                messager.printMessage(Diagnostic.Kind.NOTE, "defaultValue: \n" + ((JCTree.JCMethodDecl) tree).defaultValue);
//                messager.printMessage(Diagnostic.Kind.NOTE, "sym: \n" + ((JCTree.JCMethodDecl) tree).sym);
//                messager.printMessage(Diagnostic.Kind.NOTE, "pos: \n" + ((JCTree.JCMethodDecl) tree).pos);
//                messager.printMessage(Diagnostic.Kind.NOTE, "type: \n" + ((JCTree.JCMethodDecl) tree).type);
                for (JCTree.JCVariableDecl variableDecl: ((JCTree.JCMethodDecl) tree).params) {
//                    messager.printMessage(Diagnostic.Kind.NOTE, "mods: \n" + variableDecl.mods);
//                    messager.printMessage(Diagnostic.Kind.NOTE, "name: \n" + variableDecl.name);
//                    messager.printMessage(Diagnostic.Kind.NOTE, "nameexpr: \n" + variableDecl.nameexpr);
//                    messager.printMessage(Diagnostic.Kind.NOTE, "vartype: \n" + variableDecl.vartype);
//                    messager.printMessage(Diagnostic.Kind.NOTE, "init: \n" + variableDecl.init);
//                    messager.printMessage(Diagnostic.Kind.NOTE, "sym: \n" + variableDecl.sym);
                    if (!(variableDecl.vartype instanceof JCTree.JCTypeApply)) {
                        continue;
                    }
                    JCTree.JCTypeApply vartype = (JCTree.JCTypeApply) variableDecl.vartype;
                    messager.printMessage(Diagnostic.Kind.NOTE, "clazz: " + vartype.clazz);
                    messager.printMessage(Diagnostic.Kind.NOTE, "arguments: " + vartype.arguments);
                    for (JCTree.JCExpression arg: vartype.arguments) {
                        if (!(arg instanceof JCTree.JCWildcard)) {
                            continue;
                        }
                        JCTree.JCWildcard wildArg = (JCTree.JCWildcard) arg;
                        messager.printMessage(Diagnostic.Kind.NOTE, "kind: " + wildArg.kind);
                        messager.printMessage(Diagnostic.Kind.NOTE, "kind.kind: " + wildArg.kind.kind);
                        messager.printMessage(Diagnostic.Kind.NOTE, "kind.kind.name(): " + wildArg.kind.kind.name());
                        messager.printMessage(Diagnostic.Kind.NOTE, "inner: " + wildArg.inner);
                    }
                }
            }
        }
        return true;
    }

    private static final String OVERRIDE = "java.lang.Override";

    private static final String CACHE_ABLE = "org.springframework.cache.annotation.Cacheable";

    private static final String CACHE_NAMES = "cacheNames";
    private static final String BASE_CACHE_NAME = "BASE_CACHE_NAME";
    private static final String CASUAL_CACHE_NAME = "CASUAL_CACHE_NAME";

    private static final String KEY_GENERATOR = "BASE_CACHE_NAME";
    private static final String DEFAULT_BASE_KEY_GENERATOR = "defaultBaseKeyGenerator";
    private static final String DEFAULT_CASUAL_KEY_GENERATOR = "defaultCasualKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ID_KEY_GENERATOR = "defaultEvictByIdKeyGenerator";
    private static final String DEFAULT_EVICT_BY_IDS_KEY_GENERATOR = "defaultEvictByIdsKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ENTITY_KEY_GENERATOR = "defaultEvictByEntityKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ENTITIES_KEY_GENERATOR = "defaultEvictByEntitiesKeyGenerator";

    private static final String SERIALIZABLE = "java.io.Serializable";

    private static final String GET_BY_ID = "getById";
    private static final String SUPER_GET_BY_ID = "super.getById";
    private static final String ID = "id";

    /**
     * getById方法
     */
    private JCTree.JCMethodDecl getByIdDecl(String modelClassName) {
        // @Override注解
        JCTree.JCAnnotation overrideAnnotation = treeMaker.Annotation(memberAccess(OVERRIDE), List.nil());
        // @Cacheable注解 TODO 还是有错
        JCTree.JCAnnotation cacheableAnnotation = treeMaker.Annotation(memberAccess(CACHE_ABLE),
            List.of(makeAssignment(memberAccess(CACHE_NAMES), memberAccess(BASE_CACHE_NAME)),
                makeAssignment(memberAccess(KEY_GENERATOR), memberAccess(DEFAULT_BASE_KEY_GENERATOR))));
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(overrideAnnotation, cacheableAnnotation);
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(GET_BY_ID);
        // 返回值类型
        JCTree.JCExpression returnType = memberAccess(modelClassName);
        // 泛型参数列表
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();
        // 参数列表
        List<JCTree.JCVariableDecl> parameters =
            List.of(makeVarDef(treeMaker.Modifiers(Flags.PARAMETER), ID, memberAccess(SERIALIZABLE), null));
        // 抛出异常
        List<JCTree.JCExpression> throwsClauses = List.nil();
        // 方法体
        List<JCTree.JCStatement> statementList =
            List.of(treeMaker.Return(
                treeMaker.Apply(List.of(memberAccess(SERIALIZABLE)), memberAccess(SUPER_GET_BY_ID), List.of(memberAccess(ID)))));
        JCTree.JCBlock block = treeMaker.Block(0, statementList);
        return treeMaker.MethodDef(modifiers, name, returnType, methodGenericParams, parameters, throwsClauses, block, null);
    }
}
