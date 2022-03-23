package com.starlight.service;

import com.starlight.dto.LotDto;
import com.starlight.dto.UserDto;
import com.starlight.model.Lot;
import com.starlight.model.User;
import com.starlight.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements CommonService<UserDto, Long> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(UserDto model) {
        userRepository.save(convertToUser(model));
    }

    @Override
    public UserDto findById(Long value) {
        return null;
    }

    @Override
    public UserDto update(UserDto model) {
        return null;
    }

    @Override
    public void delete(Long value) {
    }

    @Override
    public List<UserDto> getAll() {
        return null;
    }

    private User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
