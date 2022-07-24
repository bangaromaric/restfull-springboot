package ga.banga.restfull.domain.mapper;

import ga.banga.restfull.domain.dto.EntreprisePostDto;
import ga.banga.restfull.domain.dto.EntrepriseGetDto;
import ga.banga.restfull.domain.entity.Entreprise;
import org.mapstruct.*;

/**
 * @author Romaric BANGA
 * @version 1.0
 * @since 7/24/22
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntrepriseMapper {
    Entreprise entrepriseDtoToEntreprise(EntreprisePostDto entrepriseDto);

    EntreprisePostDto entrepriseToEntrepriseDto(Entreprise entreprise);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Entreprise updateEntrepriseFromEntrepriseDto(EntreprisePostDto entrepriseDto, @MappingTarget Entreprise entreprise);

    Entreprise entrepriseGetDtoToEntreprise(EntrepriseGetDto entrepriseGetDto);

    EntrepriseGetDto entrepriseToEntrepriseGetDto(Entreprise entreprise);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Entreprise updateEntrepriseFromEntrepriseGetDto(EntrepriseGetDto entrepriseGetDto, @MappingTarget Entreprise entreprise);
}
