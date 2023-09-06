package ma.odc.fablabback;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;
import ma.odc.fablabback.entities.Users.Admin;
import ma.odc.fablabback.entities.Users.Member;
import ma.odc.fablabback.entities.Users.SuperAdmin;
import ma.odc.fablabback.entities.equipments.Category;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.enums.Role;
import ma.odc.fablabback.enums.Sex;
import ma.odc.fablabback.repositories.Users.AdminRepository;
import ma.odc.fablabback.repositories.Users.AppUsersRepository;
import ma.odc.fablabback.repositories.Users.MemberRepository;
import ma.odc.fablabback.repositories.equipments.CategoryRepository;
import ma.odc.fablabback.repositories.equipments.EquipmentRepository;
import ma.odc.fablabback.services.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FabLabBackApplication {

  public static void main(String[] args) {
    SpringApplication.run(FabLabBackApplication.class, args);
  }

//  @Bean
  CommandLineRunner start(
      AdminRepository adminRepository,
      MemberRepository memberRepository,
      CategoryRepository categoryRepository,
      EquipmentRepository equipmentRepository,
      UserServiceImpl userService,
      AppUsersRepository appUsersRepository,
      PasswordEncoder passwordEncoder) {
    return (args) -> {
      SuperAdmin superAdmin =
          SuperAdmin.builder()
              .name("super admin")
              .cin("F283828")
              .email("admin@odc.com")
              .password(passwordEncoder.encode("superadmin"))
              .appUsersname("superadmin")
              .birthDate(LocalDate.now())
              .role(Role.SUPER_ADMIN)
              .poste("Super Admin")
              .sex(Sex.HOMME)
              .build();
      appUsersRepository.save(superAdmin);

      Stream.of("souhail", "mohammed", "salma", "nihad", "nouha")
          .forEach(
              n -> {
                Admin admin =
                    Admin.builder()
                        .appUsersname("A-" + n)
                        .cin("F34334")
                        .password(passwordEncoder.encode("admin"))
                        .birthDate(LocalDate.now())
                        .sex(Sex.HOMME)
                        .email(n + "@gmail.com")
                        .role(Role.ADMIN)
                        .name(n)
                        .poste("MANAGER")
                        .build();
                adminRepository.save(admin);

                Member member =
                    Member.builder()
                        .appUsersname("M-" + n)
                        .cin("F34334")
                        .password(passwordEncoder.encode("member"))
                        .birthDate(LocalDate.now())
                        .sex(Sex.HOMME)
                        .email(n + "@gmail.com")
                        .role(Role.MEMBER)
                        .name(n)
                        .etablissment("UIR")
                        .status("student")
                        .build();
                memberRepository.save(member);
              });

      Stream.of("actionneur", "capteur", "modules", "imprimentes")
          .forEach(
              n -> {
                Category category =
                    Category.builder().description("this is a description for " + n).id(n).build();
                categoryRepository.save(category);
              });
      categoryRepository
          .findAll()
          .forEach(
              c -> {
                Equipment equipment =
                    Equipment.builder()
                        .category(c)
                        .quantity(50)
                        .name("Name " + c.getId())
                        .build();
                equipmentRepository.save(equipment);
              });

      //  create Equipment reservations
      // find a way to validate the reservation in the script

      // List<EquipmentReservation> equipmentReservationsToAttach = new ArrayList<>();

//      List<Equipment> equipmentList = equipmentRepository.findAll();
//      equipmentList.forEach(
//          e -> {
//            memberRepository
//                .findAll()
//                .forEach(
//                    member -> {
//                      Reservation reservation =
//                          Reservation.builder()
//                              .startDate(LocalDate.now())
//                              .endDate(LocalDate.now())
//                              .member(member).reservationState(EReservationState.ONHOLD)
//                              .equipmentReservationList(new ArrayList<>())
//                              .build();
//                      reservationRepository.save(reservation);
//
//                      List<EquipmentReservation> equipmentReservations = new ArrayList<>();
//
//                      for (Equipment equipment : equipmentList) {
//                        EquipmentReservation equipmentReservation =
//                            EquipmentReservation.builder()
//                                .requestedQuantity(10)
//                                .equipment(equipment)
//                                .reservation(reservation)
//                                .build();
//                        equipmentReservations.add(equipmentReservation);
//                        equipmentReservationRepository.save(equipmentReservation);
//                      }
//                      reservationRepository.save(reservation);
//                    });
//          });
    };
  }
}
