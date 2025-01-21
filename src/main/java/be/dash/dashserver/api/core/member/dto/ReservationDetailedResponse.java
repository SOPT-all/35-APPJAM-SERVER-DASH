package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;
import java.util.List;
import be.dash.dashserver.core.domain.common.Level;

public record ReservationDetailedResponse(String name,
                                          String nickname,
                                          List<RoundResponse> rounds,
                                          String location,
                                          String detailedAddress,
                                          Level level,
                                          String instructorName,
                                          String phoneNumber,
                                          LocalDateTime reservationTime,
                                          int price) {

    //{
    //    "name": "스트리트 댄스 기초",
    //    "nickname": "김태훈",
    //    "rounds": [
    //	    {
    //	    "startDateTime": "2023-12-10T10:00:00",
    //	    "endDateTime": "2023-12-10T12:00:00",
    //	    },
    //	    {
    //	    "startDateTime": "2023-12-10T10:00:00",
    //	    "eEndDateTime": "2023-12-10T12:00:00",
    //	    },
    //	    {
    //	    "startDateTime": "2023-12-10T10:00:00",
    //	    "endDateTime": "2023-12-10T12:00:00",
    //	    }
    //	   ],
    //    "location": "서울특별시 강남구 테헤란로 123",
    //    "detailedAddress": "3층"
    //    "level": "NOVICE",
    //    "name": "홍길동",
    //    "phoneNumber": "010-1234-5678",
    //    "reservationTime": "2023-12-01T15:30:00",
    //    "price": 15000,
    //  }
    //}
}
