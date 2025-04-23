package com.sw.av.infrastructure.secutiry;



import com.sw.av.application.dto.EndpointMethodDto;
import com.sw.av.application.dto.UserDto;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import com.sw.av.infrastructure.base.exceptions.LoginResquestException;
import com.sw.av.infrastructure.base.exceptions.PermisionResquestException;
import com.sw.av.domain.entities.Parameter;
import com.sw.av.domain.exceptions.DAOException;
import com.sw.av.domain.ports.inbound.EndpointMethodServicePort;
import com.sw.av.domain.ports.inbound.MethodSecurityServicePort;
import com.sw.av.domain.ports.inbound.UserServicePort;
import com.sw.av.domain.ports.outbound.ParameterService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class JwtService {

    private UserServicePort userServicePort;
    
    @Autowired
    private EndpointMethodServicePort endpointMethodServicePort;
    
    @Autowired
    private MethodSecurityServicePort methodSecurityServicePort;
    
    @Autowired
    private ParameterService parameterService;
   
    private String JWT_SECRET_KEY = "JWT_SECRET_KEY";
    private String JWT_EXP_MINUTES = "JWT_EXP_MINUTES";
    private String JWT_REFRESH_EXP_MINUTES = "JWT_REFRESH_EXP_MINUTES"; //43200 30 días en minutos

    public JwtService(@Lazy UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }
    
    
    
    public String generateRefreshToken( Map<String, Object> claims, UserDetails user) throws DAOException {
        
        Integer expTime = 43200;
        
        Optional<Parameter> parameter = parameterService.findByCod(JWT_REFRESH_EXP_MINUTES);
        if(parameter!=null){
            expTime = Integer.valueOf(parameter.get().getValue());
        }
        
        return getToken(claims, user, expTime);
    }
    
    public String getToken(UserDetails user) throws DAOException {
        
        Integer expTime = 43200;
        
        Optional<Parameter> parameter = parameterService.findByCod(JWT_EXP_MINUTES);
        if(parameter!=null){
            expTime = Integer.valueOf(parameter.get().getValue());
        }
        
        return getToken(new HashMap<>(), user, null);
    }

    public String getToken(Map<String, Object> extraClaims, UserDetails userDetail, Integer expTime) throws DAOException {

        UserDto userDto= (UserDto) userDetail;

        if (expTime == null) {
            expTime = 20;

        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expTime);
        Date fechaExp = calendar.getTime();

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDto.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .setExpiration(fechaExp)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {

        String secretKey = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
        
        Optional<Parameter> parameter = parameterService.findByCod(JWT_SECRET_KEY);
        if(parameter!=null){
            secretKey = parameter.get().getValue();
        }
        
        
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public UserDto getUser(HttpHeaders headers) throws LoginResquestException {
        UserDto userDto = null;
        String token = getToken(headers);
        String usr = getUsernameFromToken(token);


        if ((usr != null && !usr.equals(""))) {
            userDto = userServicePort.findByUsername(usr);
        } else {
            throw new LoginResquestException("FORBIDDEN, Failed to get user from token");
        }
        return userDto;
    }
    
    public UserDto getUser(Map<String,String> headers) throws LoginResquestException {
        UserDto userDto = null;
        String token = getToken(headers);
        String usr = getUsernameFromToken(token);

        if ((usr != null && !usr.equals(""))) {
            userDto = userServicePort.findByUsername(usr);
        } else {
            throw new LoginResquestException("FORBIDDEN, Failed to get user from token");
        }
        return userDto;
    }
    
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public String getToken(Map headers) {
        String s_token = "";

        if (headers != null && headers.get("Authorization") != null && headers.get("Authorization").toString().length() > 0) {
            s_token = (String) headers.get("Authorization");
        }

        if (headers != null && headers.get("authorization") != null && headers.get("authorization").toString().length() > 0) {
            s_token = (String) headers.get("authorization");
        }
        
        if (s_token != null && !s_token.equals("")) {
            s_token = s_token.substring(s_token.indexOf(" ") + 1, s_token.length());
        }

        return s_token;

    }
    
    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
        {
            return authHeader.substring(7);
        }
        return null;
    }
    
    public void havePermisionWs(Map<String, String> headers, String methodName, String serviceName) throws PermisionResquestException {
        boolean havePermision = false;
        UserDto userDto = null;

        try {
            userDto = getUser(headers);
        } catch (LoginResquestException ex) {
            
        }

        if (userDto != null) {
            EndpointMethodDto endpointMethodDto = endpointMethodServicePort.getEndpointMethod(methodName, serviceName);

            havePermision = methodSecurityServicePort.checkAccess(endpointMethodDto.getId(), userDto.getId());

            if (!havePermision) {
                                
                if (userDto != null && userDto.getId() != null && userDto.getUsername() != null) {
                    throw new PermisionResquestException("UNAUTHORIZED, The user " + userDto.getId() + "  " + userDto.getUsername() + " do not have permissions for that method");
                } else {
                    throw new PermisionResquestException("UNAUTHORIZED, You do not have permissions for that method");
                }
            }
        } else {
            throw new PermisionResquestException("UNAUTHORIZED, You do not have permissions for that method");
        }

    }
    
        
    public UserDto refreshToken(String refreshToken) throws LoginResquestException {

        UserDto userDto = null;
        
        if (!isTokenExpired(refreshToken)){
            userDto = new UserDto();
            String userName = getUsernameFromToken(refreshToken);
               
            if ((userName != null && !userName.equals(""))) {
                userDto = userServicePort.findByUsername(userName);
            } else {
                throw new LoginResquestException("FORBIDDEN, Failed to get user from token");
            }
        }
        return userDto;
    }
    
    public UserDto findByUsername(String username) {
        return userServicePort.findByUsername(username);
    }
    
    /*
    public User getUser(Map<String,String> headers) throws LoginResquestException {
        User user = null;
        String token = getToken(headers);
        String usr = getUsernameFromToken(token);
        
        if (usr != null && !usr.equals("")) {
            user = userService.findByUsername(usr);
        } else {
            throw new LoginResquestException("FORBIDDEN, Failed to get user from token");
        }
        return usr;
    }
    */
}
