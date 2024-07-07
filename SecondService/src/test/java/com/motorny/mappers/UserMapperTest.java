package com.motorny.mappers;

import com.motorny.dto.UserDto;
import com.motorny.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    private static User user;
    private UserDto userDto;

    @BeforeAll
    static void setUp() {
        user = User.builder()
                .id(10L)
                .fullName("Kirill Shadow")
                .age(4)
                .books(new HashSet<>(List.of())).build();
    }

    @Test
    void shouldProperlyMapModelToDto() {
        userDto = userMapper.toUserDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFullName(), userDto.getFullName());
        assertEquals(user.getAge(), userDto.getAge());
        assertTrue(user.getBooks().isEmpty());
    }

    @Test
    void shouldProperlyMapDtoToModel() {
        userDto = userMapper.toUserDto(user);

        user = userMapper.toUser(userDto);

        assertNotNull(user);
        assertEquals(10L, user.getId());
        assertEquals("Kirill Shadow", user.getFullName());
        assertEquals(4, user.getAge());
        assertTrue(userDto.getBooks().isEmpty());
    }
}