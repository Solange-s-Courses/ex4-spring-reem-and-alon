package com.example.ex4.mappers;

import com.example.ex4.dto.PlanPackageDTO;
import com.example.ex4.dto.PlanPackageOptionDTO;
import com.example.ex4.entity.PlanPackage;
import com.example.ex4.entity.PlanPackageOption;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanPackageMapper {

    // מיפוי בסיסי - רק את הפילדים הפשוטים
    @Mapping(target = "providerProfileId", source = "providerProfile.id")
    @Mapping(target = "planOptions", ignore = true)
    PlanPackageDTO toDto(PlanPackage entity);

    @Mapping(target = "providerProfile", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "planOptions", ignore = true)
    PlanPackage toEntity(PlanPackageDTO dto);

    List<PlanPackageDTO> toDtoList(List<PlanPackage> entities);

    @Mapping(target = "periodName", source = "period.name")
    @Mapping(target = "months", source = "period.months")
    @Mapping(target = "periodId", source = "period.id")
    PlanPackageOptionDTO toDto(PlanPackageOption entity);

    List<PlanPackageOptionDTO> toPeriodDtoList(List<PlanPackageOption> periods);
}