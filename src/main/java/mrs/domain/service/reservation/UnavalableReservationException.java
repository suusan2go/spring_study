package mrs.domain.service.reservation;

public class UnavalableReservationException extends RuntimeException {
    public UnavalableReservationException(String message) {
        super(message);
    }
}
