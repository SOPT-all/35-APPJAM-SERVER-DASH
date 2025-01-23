package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.member.Member;

public record MemberReservationResponse(String name,
                                        String phoneNumber,
                                        LocalDateTime createdAt) {
    public static MemberReservationResponse from(Member member, LocalDateTime createdAt) {
        return new MemberReservationResponse(member.getName(), member.getPhoneNumber(), createdAt);
    }
}
