package ma.odc.fablabback.Exceptions;

public class UserNotFoundException extends Exception{
    @Override
    public String toString() {
        return "This User does not exist";
    }
}
