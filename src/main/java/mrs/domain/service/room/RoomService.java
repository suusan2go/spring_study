package mrs.domain.service.room;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    List<ReservableRoom> findReservableRooms(LocalDate date);
    MeetingRoom findMeetingRoom(Integer roomId);
}
