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
import ru.shop.service.RoleService;

import java.util.Set;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        uses = {RoleService.class},
        imports = {UUID.class, Set.class, Role.class}
)
public abstract class UserMapper {


    protected static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "password", expression = "java(encoder.encode(customerDto.getPassword()))")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "roles", expression = "java(Set.of(roleService.findRoleByName(customerDto.getRole())))")
    public abstract Customer register(CustomerDto customerDto, @Context RoleService roleService);

    @InheritInverseConfiguration
    @Mapping(target = "password", ignore = true)
    public abstract CustomerDto userToUserDto(Customer user);


}
