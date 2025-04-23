package com.sw.av.infrastructure.base;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

@Slf4j
public class RestUtil {

    private static RestUtil instance;

    private RestUtil() {
    }

    public static RestUtil getInstance() {

        if (instance == null) {
            instance = new RestUtil();
        }
        return instance;
    }

     public Map<String, String> headerToMap(HttpServletRequest request) {
        
        Map<String, String> map = new HashMap<>();
        
        if (request != null) {
            
            Enumeration headerNames = request.getHeaderNames();
            
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                
                map.put(key, value);
            }
            
        }

        return map;
    }
    
    public Map<String, String> headerToMap(HttpHeaders request) {
        
        Map<String, String> map = new HashMap<>();
        
        map = request.toSingleValueMap();
        
        return map;
    }

    public String getValueOfHeaderKey(HttpHeaders request, String key) {

        String value = null;

        if (request != null && key != null) {
            Map<String, String> map = headerToMap(request);

            value = map.get(key);
        }

        return value;
    }
    
    public String getValueOfHeaderKey(Map<String, String> request, String key) {

        String value = null;

        value = request.get(key);
        
        return value;
    }

    public void printHeaders(HttpHeaders request) {
        
        log.info("HEADERS : ");
        
        Map<String, String> map = new HashMap<String, String>();
        map = headerToMap(request);

        map.forEach((key,value) ->  log.info("       " + key + " = " + value));
        
    }

    public void printHeaders(HttpServletRequest request) {
        
        log.info("HEADERS : ");
        
        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            
            log.info("       " + key + " = " + value);
        }
    }
    
    public static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
    
    /*
    public String removePathParams(HttpServletRequest request, Map<String, Object> parameters) {

        
        
        List<PathSegment> segments = uriInfo.getPathSegments();
        int size = segments.size() - parameters.size();

        String url = "/";

        for (int i = 0; i < size; i++) {

            url += segments.get(i) + "/";
        }

        return url;
    }
    */
}
