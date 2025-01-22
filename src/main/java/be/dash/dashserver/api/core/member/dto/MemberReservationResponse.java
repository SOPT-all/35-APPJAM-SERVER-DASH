package be.dash.dashserver.api.core.member.dto;

import be.dash.dashserver.core.domain.member.Member;

public record MemberReservationResponse(String name,
                                        String phoneNumber) {
    public static MemberReservationResponse from(Member member) {
        return new MemberReservationResponse(member.getName(), member.getPhoneNumber());
    }
}
