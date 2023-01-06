package core.service;

import core.domain.Stock;
import core.repository.StockRepository;
import core.service.PessimisticLockStockService;
import core.service.StockService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;
    @Autowired
    private PessimisticLockStockService pessimisticLockStockService;
    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void before() {
        Stock stock = new Stock(1L, 100L);
        stockRepository.save(stock);
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    void stock_decrease() {
        stockService.decrease(1L, 1L);
        Stock stock = stockRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException());

        assertEquals(99, stock.getQuantity());
    }

    @Test
    public void 동시에_100개의_요청() throws InterruptedException {
        // 실패 이유: 레이스 컨디션이 일어났기 떄문.
        // 둘 이상의 스레드가 공유 데이터에 엑세스 할 수 있고, 동시에 변경을 하려 할 때 발생하는 문제
        int threadCount = 100;
        // 비동기 실행 작업을 단순화하여 실행 가능하도록 해주는
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        // 다른 스레드 수행 작업이 완료될 때 까지 대기하도록 도와주는
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    //stockService.decrease(1L, 1L);
                    pessimisticLockStockService.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        Stock stock = stockRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException());
        assertEquals(0L, stock.getQuantity());
    }

}