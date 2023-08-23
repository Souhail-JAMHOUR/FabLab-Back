package ma.odc.fablabback.services;

import ma.odc.fablabback.security.models.AuthenticationRequest;

public interface IAdminService {
    boolean checkAdmin(AuthenticationRequest authenticationRequest);
}
