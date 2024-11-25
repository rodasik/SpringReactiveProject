package com.rodasik.springex.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.Builder;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.InjectionStrategy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true)
)
public interface GlobalMapperConfig {
}
