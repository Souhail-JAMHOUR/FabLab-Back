package ma.odc.fablabback.services;

import ma.odc.fablabback.dto.usersdto.AdminDTO;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.requests.AdminRegisterRequest;

public interface ISuperAdminService {

    AdminDTO addNewAdmin(AdminRegisterRequest request);

    void deleteAdmin(Long id) throws AppUsersNotFoundException;
}
