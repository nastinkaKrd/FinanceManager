package org.example.financemanager.mapper;

import java.util.List;
import java.util.Set;

public interface BaseMapper<Entity, Dto>  {
    Entity toDomain(Dto dto);

    Dto toDto(Entity entity);

    List<Entity> toDomains(List<Dto> dtos);

    List<Dto> toDtos(List<Entity> entities);

    Set<Dto> toDtos(Set<Entity> entities);
}
