package com.sw.av.infrastructure.controller;

import com.sw.av.infrastructure.base.StatusRest;
import com.sw.av.infrastructure.secutiry.JwtService;
import com.sw.av.application.dto.MemberDto;
import com.sw.av.application.dto.ErrorResponseDto;
import com.sw.av.application.dto.MemberPaymentDto;
import com.sw.av.application.dto.UserDto;
import com.sw.av.domain.ports.inbound.MemberPaymentServicePort;
import com.sw.av.infrastructure.base.JWTTokenNeeded;
import com.sw.av.infrastructure.base.exceptions.RequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/memberPayment")
@RequiredArgsConstructor
@Slf4j
public class MemberPaymentController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private MemberPaymentServicePort memberPaymentServicePort;
    
    
    @GetMapping(value = "", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @JWTTokenNeeded
    @Operation(summary = "Get members payment list",
            parameters = {
                /*
                @Parameter(in = ParameterIn.HEADER,
                        name = "language",
                        example = "EN",
                        description = "Default language.",
                        required = true,
                        schema = @Schema(type = "string")
                ),
                */
                @Parameter(in = ParameterIn.QUERY,
                        name = "idMember",
                        example = "0",
                        description = "id Member",
                        required = false,
                        schema = @Schema(type = "number")
                )
            },
            security = @SecurityRequirement(name = "bearerAuth")
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "" + StatusRest.OK,
                    description = "Operation completed successfully",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = MemberPaymentDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.BAD_REQUEST + "",
                    description = "Invalid params supplied or bad header",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.FORBIDDEN + "",
                    description = "The user don't have permission to consume this service",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.INTERNAL_SERVER_ERROR + "",
                    description = "Server error",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            )
        }
    )
    public ResponseEntity<Object> find(HttpServletRequest request,
                                       @RequestParam(value = "idMember", required = false) Long idMember)
                                       {

                
        List<MemberPaymentDto> memberPaymentDtoList = null;

        try {
            
            memberPaymentDtoList = memberPaymentServicePort.findBy(idMember);

            return ResponseEntity.ok().body(memberPaymentDtoList);
    
        }catch (RequestException ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            return ResponseEntity.badRequest().body(errorResponseDto);
        } catch (Exception ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            return ResponseEntity.internalServerError().body(errorResponseDto);
        }
    }
    
    @GetMapping(value = "/{idMemberPayment}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @JWTTokenNeeded
    @Operation(summary = "Get a Member Phone",
            parameters = {
                /*
                @Parameter(in = ParameterIn.HEADER,
                        name = "language",
                        example = "EN",
                        description = "Default language.",
                        required = true,
                        schema = @Schema(type = "string")
                ),
                */
            },
            security = @SecurityRequirement(name = "bearerAuth")
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "" + StatusRest.OK,
                    description = "Operation completed successfully",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = MemberDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.BAD_REQUEST + "",
                    description = "Invalid params supplied or bad header",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.FORBIDDEN + "",
                    description = "The user don't have permission to consume this service",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.INTERNAL_SERVER_ERROR + "",
                    description = "Server error",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            )
        }
    )
    public ResponseEntity<Object> findPhone(HttpServletRequest request,
                                            @Valid @PathVariable Long  idMemberPayment
                                              ) {

        try {

            MemberPaymentDto memberPaymentDto = memberPaymentServicePort.findById(idMemberPayment);

            return ResponseEntity.ok().body(memberPaymentDto);
    
        }catch (RequestException ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            return ResponseEntity.badRequest().body(errorResponseDto);
        } catch (Exception ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            return ResponseEntity.internalServerError().body(errorResponseDto);
        }
    }
    
    @PostMapping(value = "", 
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @JWTTokenNeeded
    @Operation(summary = "Insert payment member",
            parameters = {
                /*
                @Parameter(in = ParameterIn.HEADER,
                        name = "language",
                        example = "EN",
                        description = "Default language.",
                        required = true,
                        schema = @Schema(type = "string")
                ),
                */
            },
            security = @SecurityRequirement(name = "bearerAuth")
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "" + StatusRest.OK,
                    description = "Operation completed successfully",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = MemberDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.BAD_REQUEST + "",
                    description = "Invalid params supplied or bad header",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.FORBIDDEN + "",
                    description = "The user don't have permission to consume this service",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.INTERNAL_SERVER_ERROR + "",
                    description = "Server error",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            )
        }
    )
    public ResponseEntity<Object> post(HttpServletRequest request,
                                       @Valid @RequestBody MemberPaymentDto memberPaymentDto) {

        try {
            UserDto userDto = jwtService.findByUsername(jwtService.getUsernameFromToken(jwtService.getTokenFromRequest(request)));
            memberPaymentDto = memberPaymentServicePort.save(memberPaymentDto, userDto.getId());

            return ResponseEntity.ok().body(memberPaymentDto);
    
        }catch (RequestException ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            return ResponseEntity.badRequest().body(errorResponseDto);
        } catch (Exception ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            return ResponseEntity.internalServerError().body(errorResponseDto);
        }
    }
    
    @PutMapping(value = "/{idMemberPayment}", 
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @JWTTokenNeeded
    @Operation(summary = "Update Phone",
            parameters = {
                /*
                @Parameter(in = ParameterIn.HEADER,
                        name = "language",
                        example = "EN",
                        description = "Default language.",
                        required = true,
                        schema = @Schema(type = "string")
                ),
                */
            },
            security = @SecurityRequirement(name = "bearerAuth")
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "" + StatusRest.OK,
                    description = "Operation completed successfully",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = MemberDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.BAD_REQUEST + "",
                    description = "Invalid params supplied or bad header",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.FORBIDDEN + "",
                    description = "The user don't have permission to consume this service",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            ),
            @ApiResponse(responseCode = "" + StatusRest.INTERNAL_SERVER_ERROR + "",
                    description = "Server error",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDto.class))
                    }
            )
        }
    )
    public ResponseEntity<Object> put(HttpServletRequest request,
                                      @Valid @PathVariable Long idMemberPayment,
                                      @Valid @RequestBody MemberPaymentDto memberPaymentDto
                                      ) {
        
        try {
            UserDto userDto = jwtService.findByUsername(jwtService.getUsernameFromToken(jwtService.getTokenFromRequest(request)));
            memberPaymentDto = memberPaymentServicePort.merge(idMemberPayment,memberPaymentDto,userDto.getId());

            return ResponseEntity.ok().body(memberPaymentDto);
    
        }catch (RequestException ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            return ResponseEntity.badRequest().body(errorResponseDto);
        } catch (Exception ex) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setMessage("An unexpected error has occurred, please contact the IT department");
            errorResponseDto.setInternalErrorMessage(ex.getMessage());
            errorResponseDto.setData(null);
            return ResponseEntity.internalServerError().body(errorResponseDto);
        }
    }
    
    
}
