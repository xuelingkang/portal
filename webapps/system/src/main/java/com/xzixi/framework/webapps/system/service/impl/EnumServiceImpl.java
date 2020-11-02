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

package com.xzixi.framework.webapps.system.service.impl;

import com.xzixi.framework.boot.webmvc.model.IBaseEnum;
import com.xzixi.framework.webapps.common.model.vo.EnumItemVO;
import com.xzixi.framework.webapps.common.model.vo.EnumVO;
import com.xzixi.framework.webapps.system.service.IEnumService;
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
