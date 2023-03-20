package ru.shop.mapper;

import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.shop.dto.CustomerDto;
import ru.shop.entity.Customer;
import ru.shop.entity.Role;
import ru.shop.entity.Sale;
import ru.shop.service.RoleService;

import java.util.Set;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        imports = {UUID.class, Set.class, Role.class}
)
public abstract class SaleMapper {
    public static final SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);


    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    public abstract Sale map(Sale sale);

    @InheritInverseConfiguration
    @Mapping(target = "password", ignore = true)
    public abstract CustomerDto userToUserDto(Customer user);


}
