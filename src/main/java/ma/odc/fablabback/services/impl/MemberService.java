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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
  public Page<MemberDTO> getAllMembers(int page, int size) {
    Page<Member> all = memberRepository.findAll(PageRequest.of(page, size));
    List<MemberDTO> memberDTOS = new ArrayList<>();
    for (Member m : all) {
      MemberDTO memberDTO = usersMapper.membreToDTO(m);
      memberDTOS.add(memberDTO);
    }
    return new PageImpl<>(memberDTOS, PageRequest.of(page, size), all.getTotalElements());
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
    memberRepository
        .findById(memberDTO.getAppUsersId())
        .orElseThrow(() -> new UsernameNotFoundException("Member Not found"));
    Member member = usersMapper.dtoToMembre(memberDTO);
    Member saved = memberRepository.save(member);
    return usersMapper.membreToDTO(saved);
  }

  @Override
  public void deleteMember(Long id) {
    Member member =
        memberRepository
            .findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("Member Not found"));
    memberRepository.delete(member);
  }

  public Page<MemberDTO> searchMember(String keyword, int page, int size) {
    Page<Member> members = memberRepository.searchMember(keyword, PageRequest.of(page, size));
    List<MemberDTO> collected =
        memberRepository.searchMember(keyword, PageRequest.of(page, size)).stream()
            .map(usersMapper::membreToDTO)
            .collect(Collectors.toList());
    //    return collected;
    return new PageImpl<>(collected, PageRequest.of(page, size), members.getTotalElements());
  }
}
