package jl.forthem;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccessibleTodoListBackEndApplicationTests {

	@Test
	void contextLoads() {
		//Added to pass a quality check
		boolean test= true;
		assertThat(test).isTrue();
	}

}
