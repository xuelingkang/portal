/*
 * The xzixi framework is based on spring framework, which simplifies development.
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.boot.enhance.annotation.processor;

import com.sun.tools.javac.code.BoundKind;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.xzixi.framework.boot.enhance.annotation.CacheEnhance;
import com.xzixi.framework.boot.enhance.annotation.CacheNames;
import com.xzixi.framework.boot.enhance.annotation.util.StringUtils;

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
@SupportedAnnotationTypes("com.xzixi.framework.boot.enhance.annotation.CacheEnhance")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CacheEnhanceProcessor extends AbstractBaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 遍历所有@CacheEnhance标注的类
        for (Element element: roundEnv.getElementsAnnotatedWith(CacheEnhance.class)) {
            CacheEnhance cacheEnhance = element.getAnnotation(CacheEnhance.class);

            // 类定义
            JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) elements.getTree(element);

            // 当前类对应的实体类的类名
            String modelClassName = ((JCTree.JCTypeApply) classDecl.extending.getTree()).arguments.get(1).type.toString();

            String modelSimpleClassName = StringUtils.toLowerCaseHead(modelClassName.substring(modelClassName.lastIndexOf(".") + 1));
            CacheNames cacheNames = new CacheNames(modelSimpleClassName + ":base", modelSimpleClassName + ":casual");

            // 修改类，追加方法
            if (cacheEnhance.getById()) {
                classDecl.defs = classDecl.defs.append(getByIdDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.listByIds()) {
                classDecl.defs = classDecl.defs.appendList(List.of(redisCacheTimeToLiveDecl(),
                        redisPipelineServiceDecl(), listByIdsDecl(cacheNames, classDecl.name.toString(), modelClassName)));
            }
            if (cacheEnhance.getOne()) {
                classDecl.defs = classDecl.defs.append(getOneDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.list()) {
                classDecl.defs = classDecl.defs.append(listDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.page()) {
                classDecl.defs = classDecl.defs.append(pageDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.count()) {
                classDecl.defs = classDecl.defs.append(countDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.updateById()) {
                classDecl.defs = classDecl.defs.append(updateByIdDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.updateBatchById()) {
                classDecl.defs = classDecl.defs.append(updateBatchByIdDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.save()) {
                classDecl.defs = classDecl.defs.append(saveDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.saveBatch()) {
                classDecl.defs = classDecl.defs.append(saveBatchDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.saveOrUpdate()) {
                classDecl.defs = classDecl.defs.append(saveOrUpdateDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.saveOrUpdateBatch()) {
                classDecl.defs = classDecl.defs.append(saveOrUpdateBatchDecl(cacheNames, modelClassName));
            }
            if (cacheEnhance.removeById()) {
                classDecl.defs = classDecl.defs.append(removeByIdDecl(cacheNames));
            }
            if (cacheEnhance.removeByIds()) {
                classDecl.defs = classDecl.defs.append(removeByIdsDecl(cacheNames));
            }
        }
        // 返回true，其他processor不再处理这个注解
        return true;
    }

    private static final String OVERRIDE_ANNOTATION = "java.lang.Override";
    private static final String TRANSACTIONAL_ANNOTATION = "org.springframework.transaction.annotation.Transactional";
    private static final String CACHE_ABLE_ANNOTATION = "org.springframework.cache.annotation.Cacheable";
    private static final String CACHE_EVICT_ANNOTATION = "org.springframework.cache.annotation.CacheEvict";
    private static final String CACHING_ANNOTATION = "org.springframework.cache.annotation.Caching";
    private static final String VALUE_ANNOTATION = "org.springframework.beans.factory.annotation.Value";
    private static final String AUTOWIRED_ANNOTATION = "org.springframework.beans.factory.annotation.Autowired";

    private static final String EXCEPTION_DOT_CLASS = "java.lang.Exception.class";
    private static final String EXCEPTION_CLASS = "java.lang.Exception";
    private static final String STRING_CLASS = "java.lang.String";
    private static final String COLLECTION_UTILS_CLASS = "org.apache.commons.collections.CollectionUtils";
    private static final String OBJECTS_CLASS = "java.util.Objects";
    private static final String SERIALIZABLE_CLASS = "java.io.Serializable";
    private static final String STREAM_CLASS = "java.util.stream.Stream";
    private static final String COLLECTORS_CLASS = "java.util.stream.Collectors";
    private static final String COLLECTION_CLASS = "java.util.Collection";
    private static final String LIST_CLASS = "java.util.List";
    private static final String ARRAY_LIST_CLASS = "java.util.ArrayList";
    private static final String PAGINATION_CLASS = "com.xzixi.framework.boot.core.model.search.Pagination";
    private static final String QUERY_PARAMS_CLASS = "com.xzixi.framework.boot.core.model.search.QueryParams";
    private static final String DURATION_CLASS = "java.time.Duration";
    private static final String REDIS_PIPELINE_SERVICE_CLASS = "com.xzixi.framework.boot.redis.service.impl.RedisPipelineService";
    private static final String PAIR_CLASS = "org.apache.commons.lang3.tuple.Pair";
    private static final String IMMUTABLE_PAIR_CLASS = "org.apache.commons.lang3.tuple.ImmutablePair";

    private static final String SECONDS = "java.util.concurrent.TimeUnit.SECONDS";
    private static final String SET_IF_ABSENT = "org.springframework.data.redis.connection.RedisStringCommands.SetOption.SET_IF_ABSENT";

    private static final String ROLLBACK_FOR = "rollbackFor";
    private static final String CACHE_NAMES = "cacheNames";
    private static final String KEY_GENERATOR = "keyGenerator";
    private static final String ALL_ENTRIES = "allEntries";
    private static final String EVICT = "evict";
    private static final String VALUE = "value";

    private static final String DEFAULT_BASE_KEY_GENERATOR = "defaultBaseKeyGenerator";
    private static final String DEFAULT_CASUAL_KEY_GENERATOR = "defaultCasualKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ID_KEY_GENERATOR = "defaultEvictByIdKeyGenerator";
    private static final String DEFAULT_EVICT_BY_IDS_KEY_GENERATOR = "defaultEvictByIdsKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ENTITY_KEY_GENERATOR = "defaultEvictByEntityKeyGenerator";
    private static final String DEFAULT_EVICT_BY_ENTITIES_KEY_GENERATOR = "defaultEvictByEntitiesKeyGenerator";
    private static final String REDIS_CACHE_TIME_TO_LIVE = "redisCacheTimeToLive";
    private static final String REDIS_PIPELINE_SERVICE = "redisPipelineService";

    private static final String REDIS_CACHE_TIME_TO_LIVE_EXPRESSION = "${spring.cache.redis.time-to-live}";

    /**
     * getById
     */
    private JCTree.JCMethodDecl getByIdDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "getById";
        final String superMethod = "super.getById";
        final String id = "id";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), baseCache(cacheNames.getBaseCacheName()));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = memberAccess(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(id, SERIALIZABLE_CLASS));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, id)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * <pre>
     *     {@code
     *     @Value("${spring.cache.redis.time-to-live}")
     *     private Duration redisCacheTimeToLive;
     *     }
     * </pre>
     */
    public JCTree.JCVariableDecl redisCacheTimeToLiveDecl() {
        List<JCTree.JCAnnotation> annotationList = List.of(value(REDIS_CACHE_TIME_TO_LIVE_EXPRESSION));
        return privateTypeParamDecl(REDIS_CACHE_TIME_TO_LIVE, DURATION_CLASS, annotationList);
    }

    /**
     * <pre>
     *     {@code
     *     @Autowired
     *     private KeyGenerator defaultBaseKeyGenerator;
     *     }
     * </pre>
     */
    private JCTree.JCVariableDecl redisPipelineServiceDecl() {
        List<JCTree.JCAnnotation> annotationList = List.of(autowired());
        return privateTypeParamDecl(REDIS_PIPELINE_SERVICE, REDIS_PIPELINE_SERVICE_CLASS, annotationList);
    }

    /**
     * listByIds
     * <p>这个方法不加缓存，使用redis pipeline查询getById方法的缓存
     */
    public JCTree.JCMethodDecl listByIdsDecl(CacheNames cacheNames, String className, String modelClassName) {
        final String getById = "getById";
        final String method = "listByIds";
        final String idList = "idList";
        final String id = "id";
        final String ids = "ids";
        final String idsSize = "idsSize";
        final String result = "result";
        final String idsCallSize = "ids.size";
        final String idsCallStream = "ids.stream";
        final String idsStream = "idsStream";
        final String idsStreamCallMap = "idsStream.map";
        final String keysStream = "keysStream";
        final String stringCallFormat = "java.lang.String.format";
        final String keys = "keys";
        final String keysStreamCallCollect = "keysStream.collect";
        final String collectorsCallToList = String.format("%s.toList", COLLECTORS_CLASS);
        final String values = "values";
        final String redisPipelineServiceCallGet = "redisPipelineService.get";
        final String idsNotFound = "idsNotFound";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override());
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = listType(modelClassName);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(extendsWildCollectionParamDecl(idList, SERIALIZABLE_CLASS));
        // List<? extends Serializable> ids = new ArrayList<>(idList);
        JCTree.JCStatement defIdsStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(ids),
                extendsWildListType(SERIALIZABLE_CLASS),
                newObject(ARRAY_LIST_CLASS, idList)
        );
        // int idsSize = ids.size();
        JCTree.JCStatement defIdsSizeStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(idsSize),
                treeMaker.TypeIdent(TypeTag.INT),
                treeMaker.Apply(List.nil(), memberAccess(idsCallSize), List.nil())
        );
        // List<ModelClass> result = new ArrayList<>(idsSize);
        JCTree.JCStatement defResultStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(result),
                listType(modelClassName),
                newObject(ARRAY_LIST_CLASS, idsSize)
        );
        // Stream<? extends Serializable> idsStream = ids.stream();
        JCTree.JCStatement defIdsStreamStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(idsStream),
                extendsWildStreamType(SERIALIZABLE_CLASS),
                treeMaker.Apply(List.nil(), memberAccess(idsCallStream), List.nil())
        );
        // Stream<String> keysStream = idsStream.map(id -> String.format("[cacheEnhance.baseCacheName()]::[modelClassName]:[getById]:%s", id));
        JCTree.JCStatement defKeysStreamStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(keysStream),
                streamType(STRING_CLASS),
                treeMaker.Apply(List.nil(), memberAccess(idsStreamCallMap),
                        List.of(
                                treeMaker.Lambda(
                                        List.of(typeParamDecl(id, SERIALIZABLE_CLASS)),
                                        treeMaker.Apply(
                                                List.nil(),
                                                memberAccess(stringCallFormat),
                                                List.of(
                                                        treeMaker.Literal(cacheNames.getBaseCacheName() + "::" + className + ":" + getById + ":%s"),
                                                        memberAccess(id)
                                                )
                                        )
                                )
                        )
                )
        );
        // Collection<String> keys = keysStream.collect(Collectors.toList());
        JCTree.JCStatement defKeysStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(keys),
                collectionType(STRING_CLASS),
                treeMaker.Apply(
                        List.nil(),
                        memberAccess(keysStreamCallCollect),
                        List.of(treeMaker.Apply(List.nil(), memberAccess(collectorsCallToList), List.nil()))
                )
        );
        // List<T> values = redisPipelineService.get(keys);
        JCTree.JCStatement defValuesStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(values),
                listType(modelClassName),
                treeMaker.Apply(List.nil(), memberAccess(redisPipelineServiceCallGet), List.of(memberAccess(keys)))
        );
        // List<Serializable> idsNotFound = new ArrayList<>();
        JCTree.JCStatement defIdsNotFoundStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(idsNotFound),
                listType(SERIALIZABLE_CLASS),
                newObject(ARRAY_LIST_CLASS)
        );
        // for循环
        JCTree.JCStatement forLoopStat = forLoop(modelClassName);
        // if
        JCTree.JCStatement ifStat = ifStat(cacheNames, className, modelClassName);
        JCTree.JCStatement returnStat = treeMaker.Return(memberAccess(result));
        JCTree.JCBlock block = treeMaker.Block(0, List.of(defIdsStat, defIdsSizeStat, defResultStat, defIdsStreamStat,
                defKeysStreamStat, defKeysStat, defValuesStat, defIdsNotFoundStat, forLoopStat, ifStat, returnStat));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * <pre>
     *     {@code
     *         for (int i = 0; i < idsSize; i++) {
     *             Serializable id = ids.get(i);
     *             Object value = values.get(i);
     *             if (Objects.isNull(value)) {
     *                 idsNotFound.add(id);
     *                 continue;
     *             }
     *             result.add((T) value);
     *         }
     *     }
     * </pre>
     */
    public JCTree.JCStatement forLoop(String modelClassName) {
        final String idsSize = "idsSize";
        final String i = "i";
        final String id = "id";
        final String idsCallGet = "ids.get";
        final String value = "value";
        final String valuesCallGet = "values.get";
        final String objectsCallIsNull = String.format("%s.isNull", OBJECTS_CLASS);
        final String idsNotFoundCallAdd = "idsNotFound.add";
        final String resultCallAdd = "result.add";
        // int i = 0;
        JCTree.JCStatement defInitStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(i),
                treeMaker.TypeIdent(TypeTag.INT),
                treeMaker.Literal(0)
        );
        // i < idsSize;
        JCTree.JCParens defCondStat = treeMaker.Parens(treeMaker.Binary(JCTree.Tag.LT, memberAccess(i), memberAccess(idsSize)));
        // i++
        JCTree.JCExpressionStatement defStepStat = treeMaker.Exec(treeMaker.Unary(JCTree.Tag.POSTINC, memberAccess(i)));
        // 循环体 开始
        // Serializable id = ids.get(i);
        JCTree.JCStatement defIdStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(id),
                memberAccess(SERIALIZABLE_CLASS),
                treeMaker.Apply(List.nil(), memberAccess(idsCallGet), List.of(memberAccess(i)))
        );
        // T value = values.get(i);
        JCTree.JCStatement defObjectStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(value),
                memberAccess(modelClassName),
                treeMaker.Apply(List.nil(), memberAccess(valuesCallGet), List.of(memberAccess(i)))
        );
        // if 开始
        // Objects.isNull(value)
        JCTree.JCParens ifCondStat = treeMaker.Parens(treeMaker.Apply(List.nil(), memberAccess(objectsCallIsNull), List.of(memberAccess(value))));
        // idsNotFound.add(id);
        JCTree.JCStatement defIdsNotFoundCallAddStat = treeMaker.Exec(treeMaker.Apply(List.nil(), memberAccess(idsNotFoundCallAdd), List.of(memberAccess(id))));
        // continue;
        JCTree.JCStatement continueStat = treeMaker.Continue(null);
        JCTree.JCBlock ifBlock = treeMaker.Block(0, List.of(defIdsNotFoundCallAddStat, continueStat));
        JCTree.JCStatement ifStat = treeMaker.If(ifCondStat, ifBlock, null);
        // if 结束
        // result.add(value);
        JCTree.JCStatement resultAddStat = treeMaker.Exec(treeMaker.Apply(List.nil(), memberAccess(resultCallAdd), List.of(memberAccess(value))));
        JCTree.JCBlock block = treeMaker.Block(0, List.of(defIdStat, defObjectStat, ifStat, resultAddStat));
        // 循环体 结束
        return treeMaker.ForLoop(List.of(defInitStat), defCondStat, List.of(defStepStat), block);
    }

    /**
     * <pre>
     *     {@code
     *         if (CollectionUtils.isNotEmpty(idsNotFound)) {
     *             List<T> selectFromDb = super.listByIds(idsNotFound);
     *             if (CollectionUtils.isNotEmpty(selectFromDb)) {
     *                 result.addAll(selectFromDb);
     *                 try {
     *                     long redisCacheTimeToLiveInSeconds = redisCacheTimeToLive.getSeconds();
     *                     Stream<T> selectFromDbStream = selectFromDb.stream();
     *                     Stream<Pair<String, T>> pairsStream = selectFromDbStream.map(model -> ImmutablePair.of(String.format("[cacheEnhance.baseCacheName()]::[modelClassName]:[getById]:%s", model.getId()), model));
     *                     List<Pair<String, T>> pairs = pairsStream.collect(Collectors.toList());
     *                     redisPipelineService.set(pairs, redisCacheTimeToLiveInSeconds, TimeUnit.SECONDS, RedisStringCommands.SetOption.SET_IF_ABSENT);
     *                 } catch (Exception ignore) {}
     *             }
     *         }
     *     }
     * </pre>
     */
    public JCTree.JCStatement ifStat(CacheNames cacheNames, String className, String modelClassName) {
        final String getById = "getById";
        final String idsNotFound = "idsNotFound";
        final String collectionUtilsCallIsNotEmpty = String.format("%s.isNotEmpty", COLLECTION_UTILS_CLASS);
        final String selectFromDb = "selectFromDb";
        final String superCallListByIds = "super.listByIds";
        final String resultCallAddAll = "result.addAll";
        final String redisCacheTimeToLiveInSeconds = "redisCacheTimeToLiveInSeconds";
        final String redisCacheTimeToLiveCallGetSeconds = "redisCacheTimeToLive.getSeconds";
        final String selectFromDbStream = "selectFromDbStream";
        final String selectFromDbCallStream = "selectFromDb.stream";
        final String pairsStream = "pairsStream";
        final String selectFromDbStreamCallMap = "selectFromDbStream.map";
        final String model = "model";
        final String immutablePairCallOf = String.format("%s.of", IMMUTABLE_PAIR_CLASS);
        final String stringCallFormat = "java.lang.String.format";
        final String modelCallGetId = "model.getId";
        final String pairs = "pairs";
        final String pairsStreamCallCollect = "pairsStream.collect";
        final String collectorsCallToList = String.format("%s.toList", COLLECTORS_CLASS);
        final String redisPipelineServiceCallSet = "redisPipelineService.set";
        // CollectionUtils.isNotEmpty(idsNotFound)
        JCTree.JCParens ifCondStat = treeMaker.Parens(treeMaker.Apply(List.nil(), memberAccess(collectionUtilsCallIsNotEmpty), List.of(memberAccess(idsNotFound))));
        // List<T> selectFromDb = super.listByIds(idsNotFound);
        JCTree.JCStatement selectFromDbStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(selectFromDb),
                listType(modelClassName),
                treeMaker.Apply(List.nil(), memberAccess(superCallListByIds), List.of(memberAccess(idsNotFound)))
        );
        // if 2 开始
        // CollectionUtils.isNotEmpty(selectFromDb)
        JCTree.JCParens ifCondStat2 = treeMaker.Parens(treeMaker.Apply(List.nil(), memberAccess(collectionUtilsCallIsNotEmpty), List.of(memberAccess(selectFromDb))));
        // result.addAll(selectFromDb);
        JCTree.JCStatement resultAddAllStat = treeMaker.Exec(treeMaker.Apply(List.nil(), memberAccess(resultCallAddAll), List.of(memberAccess(selectFromDb))));
        // try 开始
        // long redisCacheTimeToLiveInSeconds = redisCacheTimeToLive.getSeconds();
        JCTree.JCStatement defRedisCacheTimeToLiveInSeconds = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(redisCacheTimeToLiveInSeconds),
                treeMaker.TypeIdent(TypeTag.LONG),
                treeMaker.Apply(List.nil(), memberAccess(redisCacheTimeToLiveCallGetSeconds), List.nil())
        );
        // Stream<T> selectFromDbStream = selectFromDb.stream();
        JCTree.JCStatement defSelectFromDbStreamStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(selectFromDbStream),
                streamType(modelClassName),
                treeMaker.Apply(List.nil(), memberAccess(selectFromDbCallStream), List.nil())
        );
        // Stream<Pair<String, T>> pairsStream = selectFromDbStream.map(model -> ImmutablePair.of(String.format("[cacheEnhance.baseCacheName()]::[modelClassName]:[getById]:%s", model.getId()), model));
        JCTree.JCTypeApply pairType = pairType(STRING_CLASS);
        JCTree.JCStatement defPairStreamStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(pairsStream),
                streamType(pairType),
                treeMaker.Apply(List.nil(), memberAccess(selectFromDbStreamCallMap),
                        List.of(
                                treeMaker.Lambda(
                                        List.of(typeParamDecl(model, modelClassName)),
                                        treeMaker.Apply(
                                                List.nil(),
                                                memberAccess(immutablePairCallOf),
                                                List.of(
                                                        treeMaker.Apply(
                                                                List.nil(),
                                                                memberAccess(stringCallFormat),
                                                                List.of(
                                                                        treeMaker.Literal(cacheNames.getBaseCacheName() + "::" + className + ":" + getById + ":%s"),
                                                                        treeMaker.Apply(
                                                                                List.nil(),
                                                                                memberAccess(modelCallGetId),
                                                                                List.nil()
                                                                        )
                                                                )
                                                        ),
                                                        memberAccess(model)
                                                )
                                        )
                                )
                        )
                )
        );
        // List<Pair<String, T>> pairs = pairsStream.collect(Collectors.toList());
        JCTree.JCStatement defPairsStat = treeMaker.VarDef(
                treeMaker.Modifiers(0),
                getNameFromString(pairs),
                listType(pairType),
                treeMaker.Apply(
                        List.nil(),
                        memberAccess(pairsStreamCallCollect),
                        List.of(treeMaker.Apply(List.nil(), memberAccess(collectorsCallToList), List.nil()))
                )
        );
        // redisPipelineService.set(pairs, redisCacheTimeToLiveInSeconds, TimeUnit.MICROSECONDS, RedisStringCommands.SetOption.SET_IF_ABSENT);
        JCTree.JCStatement redisPipelineServiceSetStat = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                memberAccess(redisPipelineServiceCallSet),
                List.of(
                        memberAccess(pairs),
                        memberAccess(redisCacheTimeToLiveInSeconds),
                        memberAccess(SECONDS),
                        memberAccess(SET_IF_ABSENT)
                )
        ));
        JCTree.JCBlock tryBlock = treeMaker.Block(0, List.of(defRedisCacheTimeToLiveInSeconds, defSelectFromDbStreamStat, defPairStreamStat, defPairsStat, redisPipelineServiceSetStat));
        // catch (Exception ignore) {}
        JCTree.JCCatch catchStat = treeMaker.Catch(typeParamDecl("ignore", EXCEPTION_CLASS), treeMaker.Block(0, List.nil()));
        JCTree.JCTry tryStat = treeMaker.Try(tryBlock, List.of(catchStat), null);
        // try 结束
        JCTree.JCBlock block2 = treeMaker.Block(0, List.of(resultAddAllStat, tryStat));
        JCTree.JCIf ifStat = treeMaker.If(ifCondStat2, block2, null);
        // if 2 结束
        JCTree.JCBlock block = treeMaker.Block(0, List.of(selectFromDbStat, ifStat));
        return treeMaker.If(ifCondStat, block, null);
    }

    /**
     * getOne
     */
    public JCTree.JCMethodDecl getOneDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "getOne";
        final String superMethod = "super.getOne";
        final String queryParams = "queryParams";
        final String throwEx = "throwEx";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache(cacheNames.getCasualCacheName()));
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
    public JCTree.JCMethodDecl listDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "list";
        final String superMethod = "super.list";
        final String queryParams = "queryParams";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache(cacheNames.getCasualCacheName()));
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
    public JCTree.JCMethodDecl pageDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "page";
        final String superMethod = "super.page";
        final String page = "page";
        final String queryParams = "queryParams";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache(cacheNames.getCasualCacheName()));
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
    private JCTree.JCMethodDecl countDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "count";
        final String superMethod = "super.count";
        final String queryParams = "queryParams";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), casualCache(cacheNames.getCasualCacheName()));
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
    private JCTree.JCMethodDecl updateByIdDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "updateById";
        final String superMethod = "super.updateById";
        final String entity = "entity";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byEntityEvict(cacheNames.getBaseCacheName()), casualEvict(cacheNames.getCasualCacheName())));
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
    private JCTree.JCMethodDecl updateBatchByIdDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "updateBatchById";
        final String superMethod = "super.updateBatchById";
        final String entityList = "entityList";
        final String batchSize = "batchSize";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byEntitiesEvict(cacheNames.getBaseCacheName()), casualEvict(cacheNames.getCasualCacheName())));
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
    private JCTree.JCMethodDecl saveDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "save";
        final String superMethod = "super.save";
        final String entity = "entity";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), casualEvict(cacheNames.getCasualCacheName()));
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
    private JCTree.JCMethodDecl saveBatchDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "saveBatch";
        final String superMethod = "super.saveBatch";
        final String entityList = "entityList";
        final String batchSize = "batchSize";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(), casualEvict(cacheNames.getCasualCacheName()));
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
    private JCTree.JCMethodDecl saveOrUpdateDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "saveOrUpdate";
        final String superMethod = "super.saveOrUpdate";
        final String entity = "entity";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byEntityEvict(cacheNames.getBaseCacheName()), casualEvict(cacheNames.getCasualCacheName())));
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
    private JCTree.JCMethodDecl saveOrUpdateBatchDecl(CacheNames cacheNames, String modelClassName) {
        final String method = "saveOrUpdateBatch";
        final String superMethod = "super.saveOrUpdateBatch";
        final String entityList = "entityList";
        final String batchSize = "batchSize";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byEntitiesEvict(cacheNames.getBaseCacheName()), casualEvict(cacheNames.getCasualCacheName())));
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
    private JCTree.JCMethodDecl removeByIdDecl(CacheNames cacheNames) {
        final String method = "removeById";
        final String superMethod = "super.removeById";
        final String id = "id";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byIdEvict(cacheNames.getBaseCacheName()), casualEvict(cacheNames.getCasualCacheName())));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(typeParamDecl(id, SERIALIZABLE_CLASS));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, id)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * removeByIds
     */
    private JCTree.JCMethodDecl removeByIdsDecl(CacheNames cacheNames) {
        final String method = "removeByIds";
        final String superMethod = "super.removeByIds";
        final String idList = "idList";
        // 注解列表
        List<JCTree.JCAnnotation> annotationList = List.of(override(), transactional(),
            evicts(byIdsEvict(cacheNames.getBaseCacheName()), casualEvict(cacheNames.getCasualCacheName())));
        // 访问修饰词和注解列表
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC, annotationList);
        // 方法名
        Name name = getNameFromString(method);
        // 返回值类型
        JCTree.JCExpression returnType = treeMaker.TypeIdent(TypeTag.BOOLEAN);
        // 参数列表
        List<JCTree.JCVariableDecl> parameters = List.of(extendsWildCollectionParamDecl(idList, SERIALIZABLE_CLASS));
        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, List.of(superReturn(superMethod, idList)));
        return treeMaker.MethodDef(modifiers, name, returnType, List.nil(), parameters, List.nil(), block, null);
    }

    /**
     * Collection&lt;innerClassName>
     */
    private JCTree.JCTypeApply collectionType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(COLLECTION_CLASS), List.of(memberAccess(innerClassName)));
    }

    /**
     * Collection&lt;? extends innerClassName>
     */
    private JCTree.JCTypeApply extendsWildCollectionType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(COLLECTION_CLASS),
                List.of(treeMaker.Wildcard(treeMaker.TypeBoundKind(BoundKind.EXTENDS), memberAccess(innerClassName))));
    }

    /**
     * Stream&lt;innerClassName>
     */
    private JCTree.JCTypeApply streamType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(STREAM_CLASS), List.of(memberAccess(innerClassName)));
    }

    /**
     * Stream&lt;typeApply>
     */
    private JCTree.JCTypeApply streamType(JCTree.JCTypeApply typeApply) {
        return treeMaker.TypeApply(memberAccess(STREAM_CLASS), List.of(typeApply));
    }

    /**
     * Stream&lt;? extends innerClassName>
     */
    private JCTree.JCTypeApply extendsWildStreamType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(STREAM_CLASS),
            List.of(treeMaker.Wildcard(treeMaker.TypeBoundKind(BoundKind.EXTENDS), memberAccess(innerClassName))));
    }

    /**
     * List&lt;innerClassName>
     */
    private JCTree.JCTypeApply listType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(LIST_CLASS), List.of(memberAccess(innerClassName)));
    }

    /**
     * List&lt;typeApply>
     */
    private JCTree.JCTypeApply listType(JCTree.JCTypeApply typeApply) {
        return treeMaker.TypeApply(memberAccess(LIST_CLASS), List.of(typeApply));
    }

    /**
     * List&lt;? extends innerClassName>
     */
    private JCTree.JCTypeApply extendsWildListType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(LIST_CLASS),
                List.of(treeMaker.Wildcard(treeMaker.TypeBoundKind(BoundKind.EXTENDS), memberAccess(innerClassName))));
    }

    /**
     * Wrapper&lt;innerClassName>
     */
    private JCTree.JCTypeApply queryParamsType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(QUERY_PARAMS_CLASS), List.of(memberAccess(innerClassName)));
    }

    /**
     * IPage&lt;innerClassName>
     */
    private JCTree.JCTypeApply paginationType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(PAGINATION_CLASS), List.of(memberAccess(innerClassName)));
    }

    /**
     * Pair&lt;innerClassName, ?>
     */
    private JCTree.JCTypeApply pairType(String innerClassName) {
        return treeMaker.TypeApply(memberAccess(PAIR_CLASS), List.of(memberAccess(innerClassName), treeMaker.Wildcard(treeMaker.TypeBoundKind(BoundKind.UNBOUND), null)));
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
     * 带注解的 private 包装类型
     */
    private JCTree.JCVariableDecl privateTypeParamDecl(String name, String modelClassName, List<JCTree.JCAnnotation> annotationList) {
        return treeMaker.VarDef(treeMaker.Modifiers(Flags.PRIVATE, annotationList), getNameFromString(name), memberAccess(modelClassName), null);
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
        return treeMaker.Annotation(memberAccess(OVERRIDE_ANNOTATION), List.nil());
    }

    /**
     * {@code @Transactional(rollbackFor = Exception.class)}
     */
    private JCTree.JCAnnotation transactional() {
        return treeMaker.Annotation(memberAccess(TRANSACTIONAL_ANNOTATION), List.of(treeMaker.Assign(memberAccess(ROLLBACK_FOR), memberAccess(EXCEPTION_DOT_CLASS))));
    }

    /**
     * {@code @Cacheable(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultBaseKeyGenerator")}
     */
    private JCTree.JCAnnotation baseCache(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_ABLE_ANNOTATION),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_BASE_KEY_GENERATOR))));
    }

    /**
     * {@code @Cacheable(cacheNames=CASUAL_CACHE_NAME, keyGenerator="defaultCasualKeyGenerator")}
     */
    private JCTree.JCAnnotation casualCache(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_ABLE_ANNOTATION),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_CASUAL_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByIdKeyGenerator")}
     */
    private JCTree.JCAnnotation byIdEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT_ANNOTATION),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_ID_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByIdsKeyGenerator")}
     */
    private JCTree.JCAnnotation byIdsEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT_ANNOTATION),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_IDS_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByEntityKeyGenerator")}
     */
    private JCTree.JCAnnotation byEntityEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT_ANNOTATION),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_ENTITY_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=BASE_CACHE_NAME, keyGenerator="defaultEvictByEntitiesKeyGenerator")}
     */
    private JCTree.JCAnnotation byEntitiesEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT_ANNOTATION),
                List.of(treeMaker.Assign(memberAccess(CACHE_NAMES), treeMaker.Literal(cacheName)),
                        treeMaker.Assign(memberAccess(KEY_GENERATOR), treeMaker.Literal(DEFAULT_EVICT_BY_ENTITIES_KEY_GENERATOR))));
    }

    /**
     * {@code @CacheEvict(cacheNames=CASUAL_CACHE_NAME, allEntries=true)}
     */
    private JCTree.JCAnnotation casualEvict(String cacheName) {
        return treeMaker.Annotation(memberAccess(CACHE_EVICT_ANNOTATION),
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
        return treeMaker.Annotation(memberAccess(CACHING_ANNOTATION),
                List.of(treeMaker.Assign(memberAccess(EVICT), rhs)));
    }

    /**
     * {@code @Value(value=expression)}
     */
    private JCTree.JCAnnotation value(String expression) {
        return treeMaker.Annotation(memberAccess(VALUE_ANNOTATION),
                List.of(treeMaker.Assign(memberAccess(VALUE), treeMaker.Literal(expression))));
    }

    /**
     * {@code @Autowired}
     */
    private JCTree.JCAnnotation autowired() {
        return treeMaker.Annotation(memberAccess(AUTOWIRED_ANNOTATION), List.nil());
    }

    /**
     * 用构造器创建对象
     */
    private JCTree.JCNewClass newObject(String className, String... parameters) {
        List<JCTree.JCExpression> expressions = List.nil();
        for (String parameter : parameters) {
            expressions = expressions.append(memberAccess(parameter));
        }
        return treeMaker.NewClass(
                null,
                List.nil(),
                treeMaker.TypeApply(memberAccess(className), List.nil()),
                expressions,
                null
        );
    }
}
