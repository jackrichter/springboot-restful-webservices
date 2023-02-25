package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.EmailAlreadyExistsException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.reposirory.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        // Convert UserDto into User JPA Entity and save to db
        // User user = UserMapper.mapToUser(userDto);

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email Aldready Exists for User");
        }

        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);

        // Convert User JPA Entity into UserDto
        // UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);

        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);

        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );

        //UserDto userDto = UserMapper.mapToUserDto(userOptional.get());
        UserDto userDto = modelMapper.map(user, UserDto.class);

        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
/*        return users.stream()
                .map(UserMapper :: mapToUserDto)
                .collect(Collectors.toList());*/

        return users.stream()
                .map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User existingUser = userRepository.findById(userDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userDto.getId())
        );

        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        User updatedUser = userRepository.save(existingUser);
        //return UserMapper.mapToUserDto(updatedUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user", "id", userId)
        );

        userRepository.deleteById(userId);
    }
}
