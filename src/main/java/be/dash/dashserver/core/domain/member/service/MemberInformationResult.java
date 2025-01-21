package be.dash.dashserver.core.domain.member.service;

public record MemberInformationResult(String nickname,
                                      String profileImageUrl,
                                      int reservationCount,
                                      int favoriteCount,
                                      int lessonCount) {
}
