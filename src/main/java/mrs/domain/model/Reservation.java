package mrs.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "reserved_date"),
            @JoinColumn(name = "room_id") })
    private ReservableRoom reservableRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public ReservableRoom getReservableRoom() {
        return reservableRoom;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setReservableRoom(ReservableRoom reservableRoom) {
        this.reservableRoom = reservableRoom;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
