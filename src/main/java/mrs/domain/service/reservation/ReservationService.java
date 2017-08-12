package mrs.domain.service.reservation;

import mrs.domain.model.*;
import mrs.domain.repository.reservation.ReservationRepository;
import mrs.domain.repository.room.ReservableRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
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
            throw new UnavailableReservationException("入力の日付・部屋の問い合わせは予約できません");
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

    @PreAuthorize("hasRole('ADMIN') or #reservation.user.userId == principal.user.userId")
    public void cancel(@P("reservation") Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    public List<Reservation> findReservations(ReservableRoomId reservableRoomId) {
        return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
    }

    public Reservation findOne(Integer reservationId) {
        return reservationRepository.findOne(reservationId);
    }
}
