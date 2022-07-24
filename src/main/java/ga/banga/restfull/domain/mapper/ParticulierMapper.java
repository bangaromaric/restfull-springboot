package ga.banga.restfull.domain.mapper;

import ga.banga.restfull.domain.dto.ParticulierGetDto;
import ga.banga.restfull.domain.dto.ParticulierPostDto;
import ga.banga.restfull.domain.entity.Particulier;
import org.mapstruct.*;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/24/22
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ParticulierMapper {
    Particulier particulierPostDtoToParticulier(ParticulierPostDto particulierPostDto);

    ParticulierPostDto particulierToParticulierPostDto(Particulier particulier);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Particulier updateParticulierFromParticulierPostDto(ParticulierPostDto particulierPostDto, @MappingTarget Particulier particulier);

    Particulier particulierGetDtoToParticulier(ParticulierGetDto particulierGetDto);

    ParticulierGetDto particulierToParticulierGetDto(Particulier particulier);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Particulier updateParticulierFromParticulierGetDto(ParticulierGetDto particulierGetDto, @MappingTarget Particulier particulier);
}
