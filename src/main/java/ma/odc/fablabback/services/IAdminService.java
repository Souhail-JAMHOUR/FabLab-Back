package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.requests.AuthenticationRequest;

public interface IAdminService {
    boolean checkAdmin(AuthenticationRequest authenticationRequest);
    List<AdminDTO> getAllAdmins();
}
