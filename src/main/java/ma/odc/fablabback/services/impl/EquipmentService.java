package ma.odc.fablabback.services.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import ma.odc.fablabback.dto.equipmentsdto.CategoryDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentReservationDTO;
import ma.odc.fablabback.entities.equipments.Equipment;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.exceptions.CatergoryNotFoundException;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.mappers.EquipmentMapper;
import ma.odc.fablabback.repositories.equipments.EquipmentRepository;
import ma.odc.fablabback.requests.EquipmentAvailabilityRequest;
import ma.odc.fablabback.requests.EquipmentAvailabilityResponse;
import ma.odc.fablabback.requests.NewEquipmentRequest;
import ma.odc.fablabback.services.IEquipmentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EquipmentService implements IEquipmentService {
  private final EquipmentMapper equipmentMapper;
  private final EquipmentRepository equipmentRepository;
  private final EquipmentReservationService equipmentReservationService;
  private final CategoryService categoryService;

  public EquipmentService(
      EquipmentMapper equipmentMapper,
      EquipmentRepository equipmentRepository,
      @Lazy EquipmentReservationService equipmentReservationService,
      CategoryService categoryService) {
    this.equipmentMapper = equipmentMapper;
    this.equipmentRepository = equipmentRepository;
    this.equipmentReservationService = equipmentReservationService;
    this.categoryService = categoryService;
  }

  @Override
  public boolean checkEquipmentAvailabiltiy(
      EquipmentReservation equipmentReservation, Reservation reservation)
      throws EquipmentNotFoundException {
    Equipment equipment =
        equipmentRepository
            .findById(equipmentReservation.getEquipment().getId())
            .orElseThrow(() -> new EquipmentNotFoundException("No Equipment found"));

    // ! Verify according to the global quantity first

    int globalQuantity = equipment.getQuantity();
    int requestedQuantity = equipmentReservation.getRequestedQuantity();

    int reservedQuantity = 0;

    // ! Verifiy according to the equipment reservations

    // ? need to inject an availability request and quest by date
    EquipmentAvailabilityRequest equipmentAvailabilityRequest =
        EquipmentAvailabilityRequest.builder()
            .equipmentId(equipment.getId())
            .startDate(reservation.getStartDate())
            .endDate(reservation.getEndDate())
            .build();

    List<EquipmentReservationDTO> equipmentReservationByEquipmentAndDates =
        equipmentReservationService.getEquipmentReservationByEquipmentAndDatesAndState(
            equipmentAvailabilityRequest);

    for (EquipmentReservationDTO e : equipmentReservationByEquipmentAndDates) {
      reservedQuantity += e.getRequestedQuantity();
    }

    return (globalQuantity >= requestedQuantity
        && requestedQuantity + reservedQuantity <= globalQuantity);
  }

  @Override
  public EquipmentDTO addNewEquipment(NewEquipmentRequest newEquipment)
      throws CatergoryNotFoundException {
    CategoryDTO categoryByName = categoryService.getCategoryByName(newEquipment.getCategory());
    Equipment equipment =
        Equipment.builder()
            .name(newEquipment.getName())
            .quantity(newEquipment.getQuantity())
            .category(equipmentMapper.dtoToCategory(categoryByName))
            .build();
    Equipment saved = equipmentRepository.save(equipment);
    return equipmentMapper.equipmentToDTO(saved);
  }

  @Override
  public EquipmentDTO getEquipment(Long id) throws EquipmentNotFoundException {
    Equipment equipment =
        equipmentRepository
            .findById(id)
            .orElseThrow(() -> new EquipmentNotFoundException("No Equipment found"));
    return equipmentMapper.equipmentToDTO(equipment);
  }

  @Override
  public void deleteEquipment(Long id) throws EquipmentNotFoundException {
    Equipment equipment =
        equipmentRepository
            .findById(id)
            .orElseThrow(() -> new EquipmentNotFoundException("No Equipment found"));
    equipmentRepository.delete(equipment);
  }

  @Override
  public EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO) {

    Equipment equipment = equipmentMapper.dtoToEquipment(equipmentDTO);
    Equipment saved = equipmentRepository.save(equipment);
    return equipmentMapper.equipmentToDTO(saved);
  }

  @Override
  public List<EquipmentDTO> getAllEquipments() {
    List<EquipmentDTO> equipmentDTOList =
        equipmentRepository.findAll().stream()
            .map(equipmentMapper::equipmentToDTO)
            .collect(Collectors.toList());
    return equipmentDTOList;
  }

  @Override
  public EquipmentAvailabilityResponse checkEquipmentAvailabilityForAdmin(Long equipmentId)
      throws EquipmentNotFoundException {

    EquipmentAvailabilityRequest request = new EquipmentAvailabilityRequest();
    request.setEquipmentId(equipmentId);
    request.setStartDate(LocalDate.now());

    EquipmentDTO equipment = getEquipment(equipmentId);

    EquipmentAvailabilityResponse response = new EquipmentAvailabilityResponse();
    response.setReservedQuantity(new HashMap<>());
    response.setEquipmentDTO(equipment);
    LocalDate actualDay = LocalDate.now();
    // ! loop till 14 and every time
    for (int i = 0; i < 14; i++) {

      int reservedQuantityOfTheDay = 0;
      request.setEndDate(actualDay.plusDays(1));
      List<EquipmentReservationDTO> equipmentReservations =
          equipmentReservationService.getEquipmentAvailabilityForAdmin(request);
      for (EquipmentReservationDTO e : equipmentReservations) {
        reservedQuantityOfTheDay += e.getRequestedQuantity();
      }
      response.getReservedQuantity().put(actualDay, reservedQuantityOfTheDay);
      actualDay = actualDay.plusDays(1);
    }

    return response;
  }

  @Override
  public int getEquipmentQuantity(long equipmentId) throws EquipmentNotFoundException {
    return getEquipment(equipmentId).getQuantity();
  }

  @Override
  public List<EquipmentDTO> searchEquipment(String kw) {
    List<EquipmentDTO> collected =
        equipmentRepository.searchEquipment(kw).stream()
            .map(equipmentMapper::equipmentToDTO)
            .collect(Collectors.toList());
    return collected;
  }

  public void decreaseQuantityByOne(Equipment equipment) throws EquipmentNotFoundException {
    EquipmentDTO equipment1 = getEquipment(equipment.getId());
    Equipment retrievedEquipment = equipmentMapper.dtoToEquipment(equipment1);
    retrievedEquipment.setQuantity(retrievedEquipment.getQuantity() - 1);
    equipmentRepository.save(retrievedEquipment);
  }

  public void increaseQuantityByOne(Equipment equipment) throws EquipmentNotFoundException {
    EquipmentDTO equipment1 = getEquipment(equipment.getId());
    Equipment retrievedEquipment = equipmentMapper.dtoToEquipment(equipment1);
    retrievedEquipment.setQuantity(retrievedEquipment.getQuantity() + 1);
    equipmentRepository.save(retrievedEquipment);
  }
}
