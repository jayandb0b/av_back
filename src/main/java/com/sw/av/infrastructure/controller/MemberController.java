package com.sw.av.infrastructure.controller;

import com.sw.av.infrastructure.base.StatusRest;
import com.sw.av.infrastructure.secutiry.JwtService;
import com.sw.av.application.dto.MemberDto;
import com.sw.av.application.dto.ErrorResponseDto;
import com.sw.av.application.dto.UserDto;
import com.sw.av.infrastructure.base.JWTTokenNeeded;
import com.sw.av.infrastructure.base.exceptions.RequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.sw.av.domain.ports.inbound.MemberServicePort;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private MemberServicePort memberServicePort;
    
    
    @GetMapping(value = "", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @JWTTokenNeeded
    @Operation(summary = "Get members paged list",
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
                        name = "text",
                        example = "0",
                        description = "name and surname, dni and email, the service will filter with LIKE",
                        required = false,
                        schema = @Schema(type = "number")
                ),
                @Parameter(in = ParameterIn.QUERY,
                        name = "dateUpdate",
                        example = "0",
                        description = "Date in milliseconds in Array format, if it has only one element, "
                                + "    the service will filter by the = operator, if it has two elements, "
                                + "    the service will filter by the >= and <= operator.",
                        required = false,
                        //schema = @Schema(type = "number")
                        array = @ArraySchema(schema = @Schema(type = "Integer", format = "int32"))
                ),
                @Parameter(in = ParameterIn.QUERY,
                        name = "page",
                        example = "0",
                        description = "Page number. If the parameter does not exist, it defaults to 0 ",
                        required = false,
                        schema = @Schema(type = "integer")
                ),
                @Parameter(in = ParameterIn.QUERY,
                        name = "size",
                        example = "5",
                        description = "Registers per page. If the parameter does not exist, it defaults to 5 ",
                        required = false,
                        schema = @Schema(type = "integer")
                ),
                @Parameter(in = ParameterIn.QUERY,
                        name = "sort",
                        example = "id,ASC",
                        description = "Sort, you can concatenate several &sort of type (field,order), if the parameter does not exist, it will default to ‘id, ASC’. ",
                        required = false,
                        array = @ArraySchema(schema = @Schema(type = "string"))
                )
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
    public ResponseEntity<Object> find(HttpServletRequest request,
                                       @RequestParam(value = "text", required = false) String text,
                                       @RequestParam(value = "updateDate", required = false) List<Long> updateDateList,
                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "size", defaultValue = "5") int size,
                                       @RequestParam(value = "sort", defaultValue = "id,ASC") String[] sort) {

                
        Page<MemberDto> memberDtoPage = null;

        try {

            if(updateDateList!=null && updateDateList.size()>2){
                ErrorResponseDto errorResponseDto = new ErrorResponseDto();
                errorResponseDto.setMessage("updateDateList List max 2 elements");
                errorResponseDto.setInternalErrorMessage("fechaRegistroList size " + updateDateList.size());
                errorResponseDto.setData(null);
                return ResponseEntity.badRequest().body(errorResponseDto);
            }
            
            memberDtoPage = memberServicePort.findBy(text, updateDateList, page, size, sort);

            return ResponseEntity.ok().body(memberDtoPage);
    
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
    
    @GetMapping(value = "/{idMember}", 
                produces = MediaType.APPLICATION_JSON_VALUE)
    @JWTTokenNeeded
    @Operation(summary = "Get Member",
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
    public ResponseEntity<Object> find(HttpServletRequest request,
                                       @Valid @PathVariable Long  idMember
                                       ) {

        try {

            MemberDto memberDto = memberServicePort.findById(idMember);

            return ResponseEntity.ok().body(memberDto);
    
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
    
    @GetMapping(value = "/{dateFrom}/{dateTo}", 
                            produces = MediaType.APPLICATION_JSON_VALUE)
    @JWTTokenNeeded
    @Operation(summary = "Get members paged list",
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
                        name = "text",
                        example = "0",
                        description = "name and surname, dni and email, the service will filter with LIKE",
                        required = false,
                        schema = @Schema(type = "number")
                ),
                @Parameter(in = ParameterIn.QUERY,
                        name = "dateUpdate",
                        example = "0",
                        description = "Date in milliseconds in Array format, if it has only one element, "
                                + "    the service will filter by the = operator, if it has two elements, "
                                + "    the service will filter by the >= and <= operator.",
                        required = false,
                        //schema = @Schema(type = "number")
                        array = @ArraySchema(schema = @Schema(type = "Integer", format = "int32"))
                ),
                @Parameter(in = ParameterIn.QUERY,
                        name = "page",
                        example = "0",
                        description = "Page number. If the parameter does not exist, it defaults to 0 ",
                        required = false,
                        schema = @Schema(type = "integer")
                ),
                @Parameter(in = ParameterIn.QUERY,
                        name = "size",
                        example = "5",
                        description = "Registers per page. If the parameter does not exist, it defaults to 5 ",
                        required = false,
                        schema = @Schema(type = "integer")
                ),
                @Parameter(in = ParameterIn.QUERY,
                        name = "sort",
                        example = "id,ASC",
                        description = "Sort, you can concatenate several &sort of type (field,order), if the parameter does not exist, it will default to ‘id, ASC’. ",
                        required = false,
                        array = @ArraySchema(schema = @Schema(type = "string"))
                )
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
    public ResponseEntity<Object> find(HttpServletRequest request,
                                                            @PathVariable(value = "dateFrom", required = true) Long dateFrom,
                                                            @PathVariable(value = "dateTo", required = true) Long dateTo,
                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "size", defaultValue = "5") int size,
                                                            @RequestParam(value = "sort", defaultValue = "id,ASC") String[] sort) {

        Page<MemberDto> memberDtoPage = null;

        try {
            
            memberDtoPage = memberServicePort.findBy(dateFrom, dateTo, page, size, sort);

            return ResponseEntity.ok().body(memberDtoPage);
    
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
    @Operation(summary = "Insert member",
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
                                       @Valid @RequestBody MemberDto memberDto) {

        try {
            UserDto userDto = jwtService.findByUsername(jwtService.getUsernameFromToken(jwtService.getTokenFromRequest(request)));
            memberDto = memberServicePort.save(memberDto, userDto.getId());

            return ResponseEntity.ok().body(memberDto);
    
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
    
    @PutMapping(value = "/{idMember}", 
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @JWTTokenNeeded
    @Operation(summary = "Get idMember",
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
                                      @Valid @PathVariable Long idMember,
                                      @Valid @RequestBody MemberDto memberDto
                                      ) {
        
        try {
            UserDto userDto = jwtService.findByUsername(jwtService.getUsernameFromToken(jwtService.getTokenFromRequest(request)));
            memberDto = memberServicePort.merge(idMember,memberDto,userDto.getId());

            return ResponseEntity.ok().body(memberDto);
    
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
