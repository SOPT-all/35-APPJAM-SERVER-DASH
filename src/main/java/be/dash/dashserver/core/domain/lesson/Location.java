package be.dash.dashserver.core.domain.lesson;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Location {
    private String title;
    /**
    * 도로명 주소
    **/
    private String roadAddress;
    /**
     * 지번 주소
     **/
    private String address;
    private String detailedAddress;
}
