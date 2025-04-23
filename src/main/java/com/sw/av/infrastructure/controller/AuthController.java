package com.sw.av.infrastructure.controller;

import com.sw.av.infrastructure.base.StatusRest;
import com.sw.av.application.dto.AuthResponseDto;
import com.sw.av.application.dto.LoginRequestDto;
import com.sw.av.application.dto.TokenRefreshRequestDto;
import com.sw.av.infrastructure.secutiry.JwtService;
import com.sw.av.application.dto.ErrorResponseDto;
import com.sw.av.application.dto.UserDto;
import com.sw.av.domain.ports.inbound.UserServicePort;
import com.sw.av.infrastructure.base.exceptions.RequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    //private final AuthService authService;
    private final UserServicePort userServicePort;
    private final JwtService jwtService;

    @PostMapping(value = "login", 
                 consumes = MediaType.APPLICATION_JSON_VALUE, 
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Get authentication token",
               parameters = {
                                /*
                                @Parameter(in = ParameterIn.HEADER,
                                           name = "language",
                                           example = "EN",
                                           description = "Default language.",
                                           required = true,
                                           schema = @Schema(type = "string")
                                )
                                ,
                                @Parameter(in = ParameterIn.HEADER,
                                           name = "cypher",
                                           example = "false",
                                           description = "Passwords are encrypted or not (\"true\" / \"false\").",
                                           required = false,
                                           schema = @Schema(type = "string")
                                ) 
                                */
               }
    )
    public ResponseEntity<AuthResponseDto> login(HttpServletRequest request,
                                              @RequestBody(required = true) LoginRequestDto loginRequest) throws Exception {

        
        UserDto userDto = userServicePort.login(loginRequest);
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDto.getId());
        claims.put("roles", userDto.getRoles());

        String token = jwtService.getToken(claims, userDto, null);
        String refreshToken = jwtService.generateRefreshToken(claims, userDto);

        HttpHeaders respoHeaders = new HttpHeaders();
        respoHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        respoHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        AuthResponseDto authResponse = new AuthResponseDto();
        authResponse.setAccessToken(token);
        authResponse.setRefreshToken(refreshToken);
        
        return ResponseEntity.ok()
                .headers(respoHeaders)
                .body(authResponse);

    }
    
    @PostMapping(value = "refresh",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "refesh token",
            parameters = {
                @Parameter(in = ParameterIn.HEADER,
                        name = "language",
                        example = "EN",
                        description = "Default language.",
                        required = true,
                        schema = @Schema(type = "string")
                ),
                @Parameter(in = ParameterIn.HEADER,
                        name = "cypher",
                        example = "false",
                        description = "Passwords are encrypted or not (\"true\" / \"false\").",
                        required = false,
                        schema = @Schema(type = "string")
                )
            }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "" + StatusRest.OK,
                     description = "Operation completed successfully",
                     content = {
                        @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = AuthResponseDto.class))
                     }
        ),
        @ApiResponse(responseCode = "" + StatusRest.BAD_REQUEST,
                description = "Invalid params supplied or bad header",
                content = {
                    @Content(mediaType = "application/json",
                             schema = @Schema(implementation = AuthResponseDto.class))
                }
        ),
        @ApiResponse(responseCode = "" + StatusRest.FORBIDDEN + "",
                description = "The user don't have permission to consume this service",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class))
                }
        ),
        @ApiResponse(responseCode = "" + StatusRest.INTERNAL_SERVER_ERROR + "",
                description = "Server error",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class))
                }
        )
    }
    )
    public ResponseEntity<Object> refreshtoken(HttpServletRequest request,
                                                         @RequestBody(required = true) TokenRefreshRequestDto  tokenRefreshRequest ) throws Exception {

        try{
            UserDto userDto = jwtService.refreshToken(tokenRefreshRequest.getRefreshToken());

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", userDto.getId());

            String token = jwtService.getToken(claims, userDto, null);
            String refreshToken = jwtService.generateRefreshToken(claims, userDto);

            HttpHeaders respoHeaders = new HttpHeaders();
            respoHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            respoHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            AuthResponseDto authResponse = new AuthResponseDto();
            authResponse.setAccessToken(token);
            authResponse.setRefreshToken(refreshToken);


            return ResponseEntity.ok()
                    .headers(respoHeaders)
                    .body(authResponse);
        }catch (RequestException ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            ResponseEntity.badRequest().body(errorResponseDto);
        } catch (Exception ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            return ResponseEntity.internalServerError().body(errorResponseDto);
        }

        return null;
    }
}
