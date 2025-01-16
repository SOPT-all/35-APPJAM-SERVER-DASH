package be.dash.dashserver.core.domain.lesson;

import lombok.Getter;

@Getter
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
}
