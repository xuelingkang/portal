package com.xzixi.self.portal.enhance.annotation.processor;

import com.sun.tools.javac.code.BoundKind;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.xzixi.self.portal.enhance.annotation.CacheEnhance;
import com.xzixi.self.portal.enhance.annotation.util.StringUtils;

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
            CacheEnhance cacheEnhance = element.getAnnotation(CacheEnhance.class);

            if (StringUtils.isBlank(cacheEnhance.baseCacheName())) {
                messager.printMessage(Diagnostic.Kind.ERROR, "baseCacheName不能为空！");
            }

            if (StringUtils.isBlank(cacheEnhance.casualCacheName())) {
                messager.printMessage(Diagnostic.Kind.ERROR, "casualCacheName不能为空！");
            }

            // 类定义
            JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) elements.getTree(element);

            // 当前类对应的实体类的类名
            String modelClassName = ((JCTree.JCTypeApply) classDecl.extending.getTree()).arguments.get(1).type.toString();

            // 修改类，追加方法
            if (cacheEnhance.getById()) {
                classDecl.defs = classDecl.defs.append(getByIdDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.listByIds()) {
                classDecl.defs = classDecl.defs.append(listByIdsDecl(modelClassName));
            }
            if (cacheEnhance.getOne()) {
                classDecl.defs = classDecl.defs.append(getOneDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.list()) {
                classDecl.defs = classDecl.defs.append(listDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.page()) {
                classDecl.defs = classDecl.defs.append(pageDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.count()) {
                classDecl.defs = classDecl.defs.append(countDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.updateById()) {
                classDecl.defs = classDecl.defs.append(updateByIdDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.updateBatchById()) {
                classDecl.defs = classDecl.defs.append(updateBatchByIdDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.save()) {
                classDecl.defs = classDecl.defs.append(saveDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.saveBatch()) {
                classDecl.defs = classDecl.defs.append(saveBatchDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.saveOrUpdate()) {
                classDecl.defs = classDecl.defs.append(saveOrUpdateDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.saveOrUpdateBatch()) {
                classDecl.defs = classDecl.defs.append(saveOrUpdateBatchDecl(cacheEnhance, modelClassName));
            }
            if (cacheEnhance.removeById()) {
                classDecl.defs = classDecl.defs.append(removeByIdDecl(cacheEnhance));
            }
            if (cacheEnhance.removeByIds()) {
                classDecl.defs = classDecl.defs.append(removeByIdsDecl(cacheEnhance));
            }
        }
        // 返回true，其他processor不再处理这个注解
        return true;
    }

    private static final String OVERRIDE = "java.lang.Override";
    private static final String TRANSACTIONAL = "org.springframework.transaction.annotation.Transactional";
    private static final String CACHE_ABLE = "org.springframework.cache.annotation.Cacheable";
    private static final String CACHE_EVICT = "org.springframework.cache.annotation.CacheEvict";
    private static final String CACHING = "org.springframework.cache.annotation.Caching";

    private static final String EXCEPTION = "java.lang.Exception.class";
    private static final String SERIALIZABLE = "java.io.Serializable";
    private static final String STREAM = "java.util.stream.Stream";
    private static final String COLLECTORS = "java.util.stream.Collectors";
    private static final String COLLECTION = "java.util.Collection";
    private static final String LIST = "java.util.List";
    private static final String PAGINATION = "com.xzixi.self.portal.framework.model.search.Pagination";
    private static final String QUERY_PARAMS = "com.xzixi.self.portal.framework.model.search.QueryParams";

    private static final String ROLLBACK_FOR = "rollbackFor";
    private static final String CACHE_NAMES = "cacheNames";
    private static final String KEY_GENERATOR = "keyGenerator";
    private static final String ALL_ENTRIES = "allEntries";
    private static final String EVICT = "evict";

    private static final String DEFAULT_BASE_KEY_GENERATOR = "defaultBaseKeyGenerator";
    private static final String DEFAULT_CASUAL_KEY_GENERATOR = "defaultCasualKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ID_KEY_GENERATOR = "defaultEvictByIdKeyGenerator";
    private static final String DEFAULT_EVICT_BY_IDS_KEY_GENERATOR = "defaultEvictByIdsKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ENTITY_KEY_GENERATOR = "defaultEvictByEntityKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ENTITIES_KEY_GENERATOR = "defaultEvictByEntitiesKeyGenerator";

    /**
     * getById
     */
    private JCTree.JCMethodDecl getByIdDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "getById";
        final String superMethod = "super.getById";
        final String id = "id";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), baseCache(cacheEnhance.baseCacheName()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = memberAccess(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(id, SERIALIZABLE));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, id)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * listByIds
     * 这个方法不加缓存，调用getById方法提高缓存的利用率
     */
    public JCTree.JCMethodDecl listByIdsDecl(String modelClassName) {
        final String method = "listByIds";
        final String idList = "idList";
        final String idListCallStream = "idList.stream";
        final String stream = "stream";
        final String streamCallMap = "stream.map";
        final String tStream = "tStream";
        final String id = "id";
        final String thisCallGetById = "this.getById";
        final String tStreamCallCollect = "tStream.collect";
        final String collectorsCallToList = String.format("%s.toList", COLLECTORS);
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = collectionType(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(extendsWildCollectionParamDecl(idList, SERIALIZABLE));
        // Stream<? extends Serializable> stream = idList.stream()
        JCTree.JCStatement defStreamStat = treeMaker.VarDef(treeMaker.Modifiers(0), getNameFromString(stream), extendsWildStreamType(SERIALIZABLE),
            treeMaker.Apply(List.nil(), memberAccess(idListCallStream), List.nil()));
        // Stream<T> tStream = stream.map(id -> this.getById(id))
        JCTree.JCStatement defTStreamStat = treeMaker.VarDef(treeMaker.Modifiers(0), getNameFromString(tStream), streamType(modelClassName),
            treeMaker.Apply(List.nil(), memberAccess(streamCallMap),
                List.of(treeMaker.Lambda(List.of(typeParamDecl(id, SERIALIZABLE)),
                    treeMaker.Apply(List.nil(), memberAccess(thisCallGetById), List.of(memberAccess(id)))))));
        // return tStream.collect(Collectors.toList())
        JCTree.JCStatement returnStat = treeMaker.Return(treeMaker.Apply(List.nil(), memberAccess(tStreamCallCollect),
            List.of(treeMaker.Apply(List.nil(), memberAccess(collectorsCallToList), List.nil()))));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(defStreamStat, defTStreamStat, returnStat));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * getOne
     */
    public JCTree.JCMethodDecl getOneDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "getOne";
        final String superMethod = "super.getOne";
        final String queryParams = "queryParams";
        final String throwEx = "throwEx";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache(cacheEnhance.casualCacheName()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = memberAccess(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(queryParamsParamDecl(queryParams, modelClassName), booleanParamDecl(throwEx));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, queryParams, throwEx)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * list
     */
    public JCTree.JCMethodDecl listDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "list";
        final String superMethod = "super.list";
        final String queryParams = "queryParams";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache(cacheEnhance.casualCacheName()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = listType(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(queryParamsParamDecl(queryParams, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, queryParams)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * page
     */
    public JCTree.JCMethodDecl pageDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "page";
        final String superMethod = "super.page";
        final String page = "page";
        final String queryParams = "queryParams";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache(cacheEnhance.casualCacheName()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = paginationType(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(paginationParamDecl(page, modelClassName), queryParamsParamDecl(queryParams, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, page, queryParams)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * count
     */
    private JCTree.JCMethodDecl countDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "count";
        final String superMethod = "super.count";
        final String queryParams = "queryParams";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache(cacheEnhance.casualCacheName()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.LONG);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(queryParamsParamDecl(queryParams, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, queryParams)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * updateById
     */
    private JCTree.JCMethodDecl updateByIdDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "updateById";
        final String superMethod = "super.updateById";
        final String entity = "entity";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byEntityEvict(cacheEnhance.baseCacheName()), casualEvict(cacheEnhance.casualCacheName())));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(entity, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entity)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * updateBatchById
     */
    private JCTree.JCMethodDecl updateBatchByIdDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "updateBatchById";
        final String superMethod = "super.updateBatchById";
        final String entityList = "entityList";
        final String batchSize = "batchSize";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byEntitiesEvict(cacheEnhance.baseCacheName()), casualEvict(cacheEnhance.casualCacheName())));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(collectionParamDecl(entityList, modelClassName), intParamDecl(batchSize));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entityList, batchSize)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * save
     */
    private JCTree.JCMethodDecl saveDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "save";
        final String superMethod = "super.save";
        final String entity = "entity";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), casualEvict(cacheEnhance.casualCacheName()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(entity, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entity)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * saveBatch
     */
    private JCTree.JCMethodDecl saveBatchDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "saveBatch";
        final String superMethod = "super.saveBatch";
        final String entityList = "entityList";
        final String batchSize = "batchSize";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), casualEvict(cacheEnhance.casualCacheName()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(collectionParamDecl(entityList, modelClassName), intParamDecl(batchSize));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entityList, batchSize)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * saveOrUpdate
     */
    private JCTree.JCMethodDecl saveOrUpdateDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "saveOrUpdate";
        final String superMethod = "super.saveOrUpdate";
        final String entity = "entity";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byEntityEvict(cacheEnhance.baseCacheName()), casualEvict(cacheEnhance.casualCacheName())));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(entity, modelClassName));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entity)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * saveOrUpdateBatch
     */
    private JCTree.JCMethodDecl saveOrUpdateBatchDecl(CacheEnhance cacheEnhance, String modelClassName) {
        final String method = "saveOrUpdateBatch";
        final String superMethod = "super.saveOrUpdateBatch";
        final String entityList = "entityList";
        final String batchSize = "batchSize";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byEntitiesEvict(cacheEnhance.baseCacheName()), casualEvict(cacheEnhance.casualCacheName())));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(collectionParamDecl(entityList, modelClassName), intParamDecl(batchSize));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, entityList, batchSize)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * removeById
     */
    private JCTree.JCMethodDecl removeByIdDecl(CacheEnhance cacheEnhance) {
        final String method = "removeById";
        final String superMethod = "super.removeById";
        final String id = "id";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byIdEvict(cacheEnhance.baseCacheName()), casualEvict(cacheEnhance.casualCacheName())));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(id, SERIALIZABLE));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, id)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * removeByIds
     */
    private JCTree.JCMethodDecl removeByIdsDecl(CacheEnhance cacheEnhance) {
        final String method = "removeByIds";
        final String superMethod = "super.removeByIds";
        final String idList = "idList";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byIdsEvict(cacheEnhance.baseCacheName()), casualEvict(cacheEnhance.casualCacheName())));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(extendsWildCollectionParamDecl(idList, SERIALIZABLE));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, idList)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * Collection&lt;innerClassName>
     */
    private JCTree.JCTypeApply collectionType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(COLLECTION), List.of(memberAccess(innerClassName)));
    }

    /**
     * Collection&lt;? extends innerClassName>
     */
    private JCTree.JCTypeApply extendsWildCollectionType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(COLLECTION),
                List.of(treeMaker.Wildcard(treeMaker.TypeBoundKind(BoundKind.EXTENDS), memberAccess(innerClassName))));
    }

    /**
     * Stream&lt;innerClassName>
     */
    private JCTree.JCTypeApply streamType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(STREAM), List.of(memberAccess(innerClassName)));
    }

    /**
     * Stream&lt;? extends innerClassName>
     */
    private JCTree.JCTypeApply extendsWildStreamType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(STREAM),
            List.of(treeMaker.Wildcard(treeMaker.TypeBoundKind(BoundKind.EXTENDS), memberAccess(innerClassName))));
    }

    /**
     * List&lt;innerClassName>
     */
    private JCTree.JCTypeApply listType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(LIST), List.of(memberAccess(innerClassName)));
    }

    /**
     * Wrapper&lt;innerClassName>
     */
    private JCTree.JCTypeApply queryParamsType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(QUERY_PARAMS), List.of(memberAccess(innerClassName)));
    }

    /**
     * IPage&lt;innerClassName>
     */
    private JCTree.JCTypeApply paginationType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(PAGINATION), List.of(memberAccess(innerClassName)));
    }

    /**
     * boolean
     */
    private JCTree.JCVariableDecl booleanParamDecl(String name) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), treeMaker.TypeIdent(TypeTag.BOOLEAN), null);
    }

    /**
     * int
     */
    private JCTree.JCVariableDecl intParamDecl(String name) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), treeMaker.TypeIdent(TypeTag.INT), null);
    }

    /**
     * 包装类型
     */
    private JCTree.JCVariableDecl typeParamDecl(String name, String modelClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), memberAccess(modelClassName), null);
    }

    /**
     * Collection&lt;innerClassName>
     */
    private JCTree.JCVariableDecl collectionParamDecl(String name, String innerClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), collectionType(innerClassName), null);
    }

    /**
     * Collection&lt;? extends innerClassName>
     */
    private JCTree.JCVariableDecl extendsWildCollectionParamDecl(String name, String innerClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), extendsWildCollectionType(innerClassName), null);
    }

    /**
     * Wrapper&lt;innerClassName>
     */
    private JCTree.JCVariableDecl queryParamsParamDecl(String name, String innerClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), queryParamsType(innerClassName), null);
    }

    /**
     * IPage&lt;innerClassName>
     */
    private JCTree.JCVariableDecl paginationParamDecl(String name, String innerClassName) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER), getNameFromString(name), paginationType(innerClassName), null);
    }

    /**
     * 调用父类方法并return
     */
    private JCTree.JCReturn superReturn(String method, String... parameters) {
        List<JCTree.JCExpression> expressions = List.nil();
        for (String parameter : parameters) {
            expressions = expressions.append(memberAccess(parameter));
        }
        return treeMaker.Return(treeMaker.Apply(List.nil(), memberAccess(method), expressions));
    }

    /**
     * {@code @Override}
     */
    private JCTree.JCAnnotation override() {
        return treeMaker.Annotation(memberAccess(OVERRIDE), List.nil());
    }

    /**
     * {@code @Transactional(rollbackFor = Exception.class)}
     */
    private JCTree.JCAnnotation transactional() {
        return treeMaker.Annotation(memberAccess(TRANSACTIONAL), List.of(treeMaker.Assign(memberAccess(ROLLBACK_FOR), memberAccess(EXCEPTION))));
    }

    /**
     * {@code @Cacheable(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultBaseKeyGenerator")}
     */
    private JCTree.JCAnnotation baseCache(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_ABLE),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_BASE_KEY_GENERATOR))));
    }

    /**
     * {@code @Cacheable(cacheNames=CASUAL_CACHE_NAME, keyGenerator="defaultCasualKeyGenerator")}
     */
    private JCTree.JCAnnotation casualCache(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_ABLE),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_CASUAL_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByIdKeyGenerator")}
     */
    private JCTree.JCAnnotation byIdEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_ID_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByIdsKeyGenerator")}
     */
    private JCTree.JCAnnotation byIdsEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_IDS_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByEntityKeyGenerator")}
     */
    private JCTree.JCAnnotation byEntityEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_ENTITY_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByEntitiesKeyGenerator")}
     */
    private JCTree.JCAnnotation byEntitiesEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_ENTITIES_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=CASUAL_CACHE_NAME, allEntries=true)}
     */
    private JCTree.JCAnnotation casualEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(ALL_ENTRIES), treeMaker.Literal(true))));
    }

    /**
     * {@code @Caching(evict={})}
     */
    private JCTree.JCAnnotation evicts(JCTree.JCAnnotation... cacheEvicts) {
        if (cacheEvicts.length == 0) {
            messager.printMessage(Diagnostic.Kind.ERROR, "cacheEvicts不能为空！");
        }
        List<JCTree.JCExpression> evicts = List.nil();
        for (JCTree.JCAnnotation cacheEvict : cacheEvicts) {
            evicts = evicts.append(cacheEvict);
        }
        JCTree.JCExpression rhs = treeMaker.NewArray(null, List.nil(), evicts);
        return treeMaker.Annotation(memberAccess(CACHING),
                List.of(treeMaker.Assign(memberAccess(EVICT), rhs)));
    }
}
