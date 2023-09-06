package ma.odc.fablabback.services;

import java.util.List;
import ma.odc.fablabback.dto.equipmentsdto.EquipmentDTO;
import ma.odc.fablabback.entities.equipments.EquipmentReservation;
import ma.odc.fablabback.entities.equipments.Reservation;
import ma.odc.fablabback.exceptions.CatergoryNotFoundException;
import ma.odc.fablabback.exceptions.EquipmentNotFoundException;
import ma.odc.fablabback.requests.EquipmentAvailabilityResponse;
import ma.odc.fablabback.requests.NewEquipmentRequest;

public interface IEquipmentService {

  boolean checkEquipmentAvailabiltiy(
      EquipmentReservation equipmentReservation, Reservation reservation)
      throws EquipmentNotFoundException;

  EquipmentDTO addNewEquipment(NewEquipmentRequest newEquipment) throws CatergoryNotFoundException;

  EquipmentDTO getEquipment(Long id) throws EquipmentNotFoundException;

  void deleteEquipment(Long id) throws EquipmentNotFoundException;

  EquipmentDTO updateEquipment(EquipmentDTO equipmentDTO);

  List<EquipmentDTO> getAllEquipments();

  EquipmentAvailabilityResponse checkEquipmentAvailabilityForAdmin(Long equipmentId)
      throws EquipmentNotFoundException;

  int getEquipmentQuantity(long equipmentId) throws EquipmentNotFoundException;

  List<EquipmentDTO> searchEquipment(String kw);
}
