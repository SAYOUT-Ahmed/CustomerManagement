package io.ahmed.securecapita.dtomapper;

import io.ahmed.securecapita.dto.UserDTO;
import io.ahmed.securecapita.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    //the method fromUser allows to transfer data from user to userDTO because we don't want to pass our password to the frontend
    public static UserDTO fromUser(User user){
        //we don't have any userDTO, so we will need to create one first.
        UserDTO userDTO = new UserDTO();
        //we will copy from user to userDTO.
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }

        public static User toUser(UserDTO userDTO){
        //we don't have any userDTO, so we will need to create one first.
        User user = new User();
        //we will copy from user to userDTO.
        BeanUtils.copyProperties(userDTO,user);
        return user;
    }




}
