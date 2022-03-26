package br.com.fatec.server.mappers;

import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

public class ProjectionMapper {
    public static <T>T convertObject(Class<T> klass, Object object) {
        ProjectionFactory pFactory = new SpelAwareProxyProjectionFactory();
        return pFactory.createProjection(klass, object);
    }
}
