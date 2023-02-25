package net.javaguides.springboot.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.ErrorDetails;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.createUser(userDto);

        return  new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long userId) {
        UserDto userDto = userService.getUserById(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUserDto = userService.getAllUsers();
        return new ResponseEntity<>(allUserDto, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> upDateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto userDto) {
        userDto.setId(userId);
        UserDto updatedUserDto = userService.updateUser(userDto);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }

    /**
     * Handle custom exceptions directly in the Controller class
     */
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
//                                                                        WebRequest webRequest) {
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webRequest.getDescription(false),
//                "USER_NOT_FOUND"
//        );
//
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }
}
