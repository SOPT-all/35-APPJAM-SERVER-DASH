package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;
import java.util.List;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.reservation.Reservation;

import static java.util.stream.Collectors.toList;

public record ReservationDetailedResponse(String lessonName,
                                          String nickname,
                                          List<RoundResponse> rounds,
                                          String location,
                                          String detailedAddress,
                                          Level level,
                                          String name,
                                          String phoneNumber,
                                          LocalDateTime reservationTime,
                                          int price) {
    public static ReservationDetailedResponse from(Member member, Reservation reservation, Lesson lesson) {
        return new ReservationDetailedResponse(
                lesson.getName(),
                lesson.getTeacherNickName(),
                lesson.getRounds().getRounds().stream().map(RoundResponse::from).collect(toList()),
                lesson.getLocationName(),
                lesson.getDetailedAddress(),
                lesson.getLevel(),
                member.getName(),
                member.getPhoneNumber(),
                reservation.getCreatedAt(),
                lesson.getPrice()

        );
    }

}
