package ma.odc.fablabback.Exceptions;

public class AppUsersNotFoundException extends Exception{
    @Override
    public String toString() {
        return "This AppUsers does not exist";
    }
}
