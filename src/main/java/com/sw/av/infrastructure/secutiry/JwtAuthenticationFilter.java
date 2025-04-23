package com.sw.av.infrastructure.secutiry;


import com.sw.av.Constants;
import com.sw.av.infrastructure.base.RestUtil;
import com.sw.av.infrastructure.base.exceptions.RequestException;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtService jwtService;
        
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        final String token = jwtService.getTokenFromRequest(request);
        final String username;

        if (token == null || token.equalsIgnoreCase("null")) {
            filterChain.doFilter(request, response);
            return;
        }

        //is valid token???
        try {
            username = jwtService.getUsernameFromToken(token);
        } catch (Exception e) {

            generateResponse(response, HttpStatus.UNAUTHORIZED.value(), "ERROR", e.getMessage());
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.findByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            
            String methodStr = request.getServletPath();
            Map<String, String> headers = new HashMap<>();
            String uriInfo = "";
            try {
                RestUtil.getInstance().printHeaders(request);
                headers = RestUtil.getInstance().headerToMap(request);
                uriInfo = RestUtil.getInstance().getFullURL(request);
            } catch (Exception e) {
                log.error("🔥 🔥 🚒 ERROR IN JwtAuthenticacionFilter.doFilterInternal()", e);
                
                generateResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR", e.getMessage());
                return;
            }
            
            try {
                jwtService.havePermisionWs(headers, methodStr, Constants.SERVICE_NAME);
                
            } catch (RequestException ex) {
                generateResponse(response, ex.getStatus(), "ERROR", ex.getMessage());
                return;
            } catch (Exception ex) {
                generateResponse(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), "ERROR", ex.getMessage());
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    private HttpServletResponse generateResponse(HttpServletResponse response, int statusCode, String menssage, String responseBody) throws IOException {

        response.reset();
        response.setHeader("Content-Type", "text/plain");
        response.setStatus(statusCode);
        response.getWriter().write(responseBody);
        return response;
    }

}
