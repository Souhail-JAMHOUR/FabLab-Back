package ma.odc.fablabback.Exceptions;

public class ReservationNotFoundExcetion extends Exception {
  @Override
  public String toString() {
    return "This reservation does not exist";
  }
}
