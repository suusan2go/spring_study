package mrs.domain.service.reservation;

import mrs.domain.model.*;
import mrs.domain.repository.reservation.ReservationRepository;
import mrs.domain.repository.room.ReservableRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservableRoomRepository reservableRoomRepository;

    public Reservation reserve(Reservation reservation) {
        ReservableRoomId reservableRoomId = reservation.getReservableRoom().getReservableRoomId();
        ReservableRoom reservable = reservableRoomRepository.findOne(reservableRoomId);
        if(reservable == null){
            throw new UnavalableReservationException("入力の日付・部屋の問い合わせは予約できません");
        }
        boolean overlap = reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)
                .stream()
                .anyMatch(x -> x.overlap(reservation));
        if(overlap) {
            throw new AlreadyReservedException("入力の時間帯は既に予約済みです。");
        }
        reservationRepository.save(reservation);
        return reservation;
    }

    public void cancel(Integer reservationId, User requestUser) {
        Reservation reservation = reservationRepository.findOne(reservationId);
        if (RoleName.ADMIN != requestUser.getRolename() && !Objects.equals(reservation.getUser().getUserID(), requestUser.getUserID())){
            throw new IllegalStateException("要求されたキャンセルは許可できません");
        }
        reservationRepository.delete(reservation);
    }

    public List<Reservation> findReservations(ReservableRoomId reservableRoomId) {
        return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
    }
}