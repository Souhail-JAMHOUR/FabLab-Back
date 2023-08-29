package ma.odc.fablabback.services;

import ma.odc.fablabback.requests.AuthenticationRequest;

public interface IAdminService {
    boolean checkAdmin(AuthenticationRequest authenticationRequest);
}
