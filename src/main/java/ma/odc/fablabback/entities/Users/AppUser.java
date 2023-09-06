package ma.odc.fablabback.entities.Users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.odc.fablabback.enums.Role;
import ma.odc.fablabback.enums.Sex;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "APP_USERS")
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long appUsersId;

  @Column(unique = true, nullable = false)
  private String appUsersname;

  private String name;

  @Enumerated(EnumType.STRING)
  private Role role;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  //  @Column(unique = true,nullable = false)
  private String email;

  @Column(name = "image_url")
  private String imageUrl;

  //  @Column(unique = true,nullable = false)
  private String cin;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @Enumerated(EnumType.STRING)
  private Sex sex;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return this.getAppUsersname();
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
}
