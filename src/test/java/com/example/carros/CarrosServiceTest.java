package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import com.example.carros.api.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarrosServiceTest {

	@Autowired
	private CarroService service;

	@Test
	void TestSave() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("Esportivo");

		CarroDTO c = service.save(carro);

		assertNotNull(c);

		Long id = c.getId();
		assertNotNull(id);

		c = service.getCarroById(id);
		assertNotNull(c);

		assertEquals("Ferrari", c.getNome());
		assertEquals("Esportivo", c.getTipo());

		service.delete(id);

		try{
			assertNull(service.getCarroById(id));
			fail("Carro não excluido");
		}catch (ObjectNotFoundException e){
			//Ok
		}
	}

	@Test
	void TestLista() {
		List<CarroDTO> carros = service.getCarros();
		assertEquals(30, carros.size());
	}

	@Test
	void TestListaPorTipo() {
		assertEquals(10, service.getCarroByTipo("esportivos").size());
		assertEquals(10, service.getCarroByTipo("luxo").size());
		assertEquals(10, service.getCarroByTipo("classicos").size());
		assertEquals(0, service.getCarroByTipo("abcd").size());
	}

	@Test
	void TestGet() {
		CarroDTO c = service.getCarroById(11L);
		assertNotNull(c);
		assertEquals("Ferrari FF", c.getNome());
	}

}
