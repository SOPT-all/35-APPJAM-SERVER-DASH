package be.dash.dashserver.core.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import be.dash.dashserver.ServiceSliceTest;
import be.dash.dashserver.core.domain.member.Role;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ReissueServiceTest extends ServiceSliceTest {

    @Autowired
    private ReissueService reissueService;
    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;
    @MockitoSpyBean
    private RefreshTokenRepository refreshTokenRepository;

    @DisplayName("리프레시 토큰을 재발급한다.")
    @Test
    void reissue() {
        // Given
        String refreshToken = jwtTokenGenerator.createRefreshToken("1", Role.MEMBER);
        refreshTokenRepository.save(refreshToken, 1L);

        // When
        Token reissue = reissueService.reissue("Bearer " + refreshToken);

        // Then
        Assertions.assertThat(refreshTokenRepository.findByRefreshToken(reissue.refreshToken()).get().getRefreshToken())
                .isEqualTo(reissue.refreshToken());
    }

    @DisplayName("동시에 reissue시 마지막 리프레시 토큰으로 업데이트한다.")
    @Test
    void reissue_concurrent() throws InterruptedException, ExecutionException {
        // Given
        String refreshToken = jwtTokenGenerator.createRefreshToken("1", Role.MEMBER);
        refreshTokenRepository.save(refreshToken, 1L);

        // When
        int THREAD_COUNT = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            futures.add(executorService.submit(() -> {
                try {
                    return reissueService.reissue("Bearer " + refreshToken);
                } finally {
                    countDownLatch.countDown();
                }
            }));
        }
        countDownLatch.await();

        // Then
        Token lastToken = (Token)futures.get(1).get();
        Assertions.assertThat(refreshTokenRepository.findByRefreshToken(lastToken.refreshToken()).get().getRefreshToken())
                        .isEqualTo(lastToken.refreshToken());
        verify(refreshTokenRepository, times(2)).update(anyString(), anyLong());
    }
}
