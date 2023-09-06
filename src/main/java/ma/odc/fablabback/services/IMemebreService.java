package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;

public interface IMemebreService {

    MemberDTO getConnectedMember() throws AppUsersNotFoundException;

    List<MemberDTO> getAllMembers();
    MemberDTO getMemberById(Long id);
    MemberDTO getMemberByUsername(String username) throws AppUsersNotFoundException;
    MemberDTO updateMember(MemberDTO memberDTO);
    void deleteMember(Long id);
    
}
