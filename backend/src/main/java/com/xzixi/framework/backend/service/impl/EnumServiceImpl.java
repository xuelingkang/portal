package com.xzixi.framework.backend.service.impl;

import com.xzixi.framework.backend.model.vo.EnumItemVO;
import com.xzixi.framework.backend.model.vo.EnumVO;
import com.xzixi.framework.boot.webmvc.model.IBaseEnum;
import com.xzixi.framework.backend.service.IEnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class EnumServiceImpl implements IEnumService {

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public <E extends Enum<E>> void init() throws IOException, ClassNotFoundException {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        Resource[] resources = resolver.getResources(ENUM_SCAN);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader reader = metaReader.getMetadataReader(resource);
                Class<E> cls = (Class<E>) Class.forName(reader.getClassMetadata().getClassName());
                if (!IBaseEnum.class.isAssignableFrom(cls)) {
                    continue;
                }
                if (!cls.isEnum()) {
                    continue;
                }
                EnumSet<E> enumSet = EnumSet.allOf(cls);
                List<EnumItemVO> items = enumSet.stream()
                        .map(e -> new EnumItemVO(e.name(), ((IBaseEnum) e).getValue()))
                        .collect(Collectors.toList());
                ENUMS.add(new EnumVO(cls.getSimpleName(), items));
            }
        }
    }
}
