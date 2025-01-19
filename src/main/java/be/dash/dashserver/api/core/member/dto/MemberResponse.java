package be.dash.dashserver.api.core.member.dto;

import be.dash.dashserver.core.domain.member.service.MemberInformationResult;

public record MemberResponse(String nickname,
                             String profileImageUrl,
                             int reservationCount,
                             int favoriteCount,
                             int lessonCount) {
    public static MemberResponse from(MemberInformationResult memberInformationResult) {
        return new MemberResponse(
                memberInformationResult.nickname(),
                memberInformationResult.profileImageUrl(),
                memberInformationResult.reservationCount(),
                memberInformationResult.favoriteCount(),
                memberInformationResult.lessonCount()
        );
    }
}
