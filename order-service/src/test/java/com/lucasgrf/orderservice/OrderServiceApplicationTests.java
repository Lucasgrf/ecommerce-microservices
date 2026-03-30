package com.lucasgrf.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.lucasgrf.orderservice.domain.repository.OrderRepository;

@SpringBootTest
class OrderServiceApplicationTests {

	@MockitoBean
	private OrderRepository orderRepository;

	@Test
	void contextLoads() {
	}

}
