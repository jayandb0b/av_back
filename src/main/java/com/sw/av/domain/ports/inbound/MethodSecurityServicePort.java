package com.sw.av.domain.ports.inbound;

public interface MethodSecurityServicePort  {

    boolean checkAccess(Long idEndpointMethod, Long idUser);

}
