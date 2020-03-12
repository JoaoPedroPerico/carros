package com.example.carros.domain;

import com.example.carros.api.exception.ObjectNotFoundException;
import com.example.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros(){
        List<Carro> carros = rep.findAll();

        List<CarroDTO> carrosDTO = new ArrayList<>();
        for(Carro c : carros){
            carrosDTO.add(CarroDTO.create(c));
        }

        return carrosDTO;
    }

    public CarroDTO getCarroById(Long id) {
        return rep.findById(id).map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {
        List<Carro> carros = rep.findByTipo(tipo);

        List<CarroDTO> carrosDTO = new ArrayList<>();
        for(Carro c : carros){
            carrosDTO.add(CarroDTO.create(c));
        }

        return carrosDTO;
    }

    public CarroDTO save(Carro carro) {
        Assert.isNull(carro.getId(), "Erro CarroDTO insert");
        return CarroDTO.create(rep.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Identificador nulo");

        Optional<Carro> optional = rep.findById(id);
        if(optional.isPresent()){
            Carro db = optional.get();
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id: " + db.getId());

            rep.save(db);
            return CarroDTO.create(db);
        }else{
            return null;
            // throw new RuntimeException("Não foi possivel atualizar");
        }
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}
