package com.xzixi.self.portal.enhance.annotation.processor;

import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;

/**
 * @author 薛凌康
 */
public abstract class AbstractBaseProcessor extends AbstractProcessor {

    protected Messager messager;
    protected JavacElements elements;
    protected JavacTrees trees;
    protected TreeMaker treeMaker;
    protected Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        this.elements = (JavacElements) processingEnv.getElementUtils();
        this.trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    /**
     * 使用路径访问类，属性
     *
     * @param expression 路径
     * @return JCExpression
     */
    protected JCTree.JCExpression expression(String expression) {
        String[] expressionArray = expression.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(names.fromString(expressionArray[0]));
        for (int i = 1; i < expressionArray.length; i++) {
            expr = treeMaker.Select(expr, names.fromString(expressionArray[i]));
        }
        return expr;
    }
}
