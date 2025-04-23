package com.sw.av.application.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements UserDetails{
    
    @Setter(onMethod = @__(@JsonSetter(value = "id"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "id")))
    private Long id;

    @Setter(onMethod = @__(@JsonSetter(value = "username"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "username")))
    private String username;
    
    @Setter(onMethod = @__(@JsonSetter(value = "password"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "password")))
    private String password;

    @Setter(onMethod = @__(@JsonSetter(value = "name"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "name")))
    private String name;
    
    @Setter(onMethod = @__(@JsonSetter(value = "surname"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "surname")))
    private String surname;
    
    @Setter(onMethod = @__(@JsonSetter(value = "roles"))) 
    @Getter(onMethod = @__(@JsonGetter(value = "roles")))
    private List<String> roles;

   
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        List rolList = new ArrayList();
        for(String rol : roles){
            rolList.add(new SimpleGrantedAuthority(rol));
        }
        
        return rolList;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    
}
