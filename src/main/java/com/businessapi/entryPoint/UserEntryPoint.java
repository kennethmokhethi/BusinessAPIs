package com.businessapi.entryPoint;

import com.businessapi.annotations.Secured;
import com.businessapi.dto.UserDTO;
import com.businessapi.model.request.CreateUserRequestModel;
import com.businessapi.model.request.UpdateUserRequestModel;
import com.businessapi.model.response.DeleteUserProfileResponseModel;
import com.businessapi.model.response.RequestOperation;
import com.businessapi.model.response.ResponseStatus;
import com.businessapi.model.response.UserProfileRest;
import com.businessapi.service.UserService;
import com.businessapi.service.serviceImpl.UserServiceImpl;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
@Api("/Users")
@SwaggerDefinition(tags = {@Tag(name = "Users", description = "Rest endpoint for users")})
public class UserEntryPoint {


    @ApiOperation("Sign up")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfileRest createUser(CreateUserRequestModel requestObject) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(requestObject, userDTO);
        userDTO.setBondRequestArrayList(requestObject.getBondRequestArrayList());
        UserService userService = new UserServiceImpl();
        UserDTO createdUserProfile = userService.createUser(userDTO);
        UserDTO userResponse = userService.convertToResponse(createdUserProfile);
        UserProfileRest returnValue = new UserProfileRest();
        BeanUtils.copyProperties(userResponse, returnValue);
        return returnValue;
    }

    @ApiOperation("Retrieve user by public id")
    @Secured
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Bearer <token>", required = true, dataType = "string", paramType = "header")})
    public UserProfileRest getUserprofile(@PathParam("id") String id) {
        UserService userService = new UserServiceImpl();
        UserDTO userProf = userService.getUser(id);
        UserDTO userResponse = userService.convertToResponse(userProf);
        UserProfileRest returnValue = new UserProfileRest();
        BeanUtils.copyProperties(userResponse, returnValue); //usage
        return returnValue;
    }

    @ApiOperation("Retrieve users")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserProfileRest> getUsers(@DefaultValue("0") @QueryParam("start") int start, @DefaultValue("20") @QueryParam("limit") int limit) {
        UserService userService = new UserServiceImpl();
        List<UserDTO> users = userService.getUsers(start, limit);
        List<UserProfileRest> returnValue = new ArrayList<>();
        for (UserDTO user : users) {
            UserDTO userResponse = userService.convertToResponse(user);
            UserProfileRest userModel = new UserProfileRest();
            BeanUtils.copyProperties(userResponse, userModel);
            //rem
            userModel.setHref("/users/" + user.getUserId());
            returnValue.add(userModel);
        }
        return returnValue;
    }

    @ApiOperation("Update user")
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfileRest updateUserDetails(@PathParam("id") String id, UpdateUserRequestModel userRequestModel) {
        UserService userService = new UserServiceImpl();
        UserDTO storedUser = userService.getUser(id);

        if (StringUtils.isNotBlank(userRequestModel.getFirstname()) && StringUtils.isNotBlank(userRequestModel.getLastname())) {
            storedUser.setFirstname(userRequestModel.getFirstname());
            storedUser.setLastname(userRequestModel.getLastname());
        }
        userService.updateUserDetails(storedUser);
        UserProfileRest returnValue = new UserProfileRest();
        UserDTO userResponse = userService.convertToResponse(storedUser);
        BeanUtils.copyProperties(userResponse, returnValue);
        return returnValue;
    }

    @ApiOperation("Delete user")
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DeleteUserProfileResponseModel deleteUserProfile(@PathParam("id") String id) {
        DeleteUserProfileResponseModel returnValue = new DeleteUserProfileResponseModel();
        returnValue.setRequestOperation(RequestOperation.DELETE);
        UserService userService = new UserServiceImpl();
        UserDTO storedUser = userService.getUser(id);
        userService.deleteUser(storedUser);
        returnValue.setResponseStatus(ResponseStatus.SUCCESS);
        return returnValue;
    }
}
