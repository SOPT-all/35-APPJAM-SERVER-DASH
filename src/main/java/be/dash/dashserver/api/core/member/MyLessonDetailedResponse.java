package be.dash.dashserver.api.core.member.dto;

public record MyLessonDetailedResponse(long lessonId) {
    // "lessonId": 12345,
    //    "lessonName": "스트리트 댄스 기초",
    //    "lessonImageUrl": "https://example.com/lesson-image.jpg",
    //    "lessonGenre": "힙합",
    //    "lessonLevel": "입문자",
    //    "lessonLocation": "서울특별시 강남구",
    //    "lessonStartDateTime": "2023-12-15T10:00:00",
    //    "lessonEndDateTime": "2023-12-15T12:00:00",
    //    "applyStatus" : "APPLYING", "FINISHED"
}
