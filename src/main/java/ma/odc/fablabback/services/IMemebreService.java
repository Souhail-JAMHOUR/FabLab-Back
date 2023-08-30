package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.usersdto.MemberDTO;

public interface IMemebreService {

    List<MemberDTO> getAllMembers();
    MemberDTO getMemberById(Long id);
    MemberDTO getMemberByUsername(String username);
    MemberDTO updateMember(MemberDTO memberDTO);
    void deleteMember(Long id);
    
}
