package be.dash.dashserver;

import java.util.TimeZone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerApplicationTests {

	@Test
	void contextLoads() {
	}

	@BeforeAll
	static void setup() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}
