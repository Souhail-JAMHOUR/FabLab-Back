package ma.odc.fablabback.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import ma.odc.fablabback.dto.usersdto.MemberDTO;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.exceptions.AppUsersNotFoundException;
import ma.odc.fablabback.mappers.UsersMapperImpl;
import ma.odc.fablabback.repositories.Users.MemberRepository;
import ma.odc.fablabback.services.IMemebreService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService implements IMemebreService {
  private MemberRepository memberRepository;
  private UsersMapperImpl usersMapper;

  @Override
  public MemberDTO getConnectedMember() throws AppUsersNotFoundException {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return getMemberByUsername(username);
  }

  @Override
  public List<MemberDTO> getAllMembers() {
    List<Member> all = memberRepository.findAll();
    List<MemberDTO> memberDTOS = new ArrayList<>();
    for (Member m : all) {
      MemberDTO memberDTO = usersMapper.membreToDTO(m);
      memberDTOS.add(memberDTO);
    }
    return memberDTOS;
  }

  @Override
  public MemberDTO getMemberById(Long id) {
    Member member =
        memberRepository
            .findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("Member does not exists"));
    return usersMapper.membreToDTO(member);
  }

  @Override
  public MemberDTO getMemberByUsername(String username) throws AppUsersNotFoundException {
    Member member =
        memberRepository
            .findByAppUsersname(username)
            .orElseThrow(() -> new AppUsersNotFoundException("Member Not found"));
    return usersMapper.membreToDTO(member);
  }

  @Override
  public MemberDTO updateMember(MemberDTO memberDTO) {
    memberRepository.findById(memberDTO.getAppUsersId()).orElseThrow(() -> new UsernameNotFoundException("Member Not found"));
    Member member = usersMapper.dtoToMembre(memberDTO);
    Member saved = memberRepository.save(member);
    return usersMapper.membreToDTO(saved);
  }

  @Override
  public void deleteMember(Long id) {
    Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Member Not found"));
    memberRepository.delete(member);
  }

  public List<MemberDTO> searchMember(String keyword) {
    List<MemberDTO> collected = memberRepository.searchMember(keyword).stream().map(usersMapper::membreToDTO).collect(Collectors.toList());
    return collected;
  }
}
