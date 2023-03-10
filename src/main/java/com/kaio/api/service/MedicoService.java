package com.kaio.api.service;

import com.kaio.api.model.Medico;
import com.kaio.api.model.dto.MedicoPutDTO;
import com.kaio.api.model.dto.shallow.MedicoShallowDTO;
import com.kaio.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    @Transactional
    public Medico salvar(Medico medico) {
        return repository.save(medico);
    }

    public Page<MedicoShallowDTO> buscarMedicos(Pageable paginacao) {
//        sem paginacao poderiamos retornar esse método que devolve uma lista de medicoshallowdto a partir do retorno
//        do bd
//        return repository.findAll().stream().map(MedicoShallowDTO::new).toList();
        return repository.findAllByAtivoTrue(paginacao).map(MedicoShallowDTO::new);
    }
    public MedicoShallowDTO buscarMedico(Long id){
        return  new MedicoShallowDTO(repository.findById(id).get());
    }

    public Medico atualizar(MedicoPutDTO medico) {
        var entity = repository.getReferenceById(medico.id());
        entity.atualizarInformacoes(medico);
        return entity;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Medico inativarMedico(Long id) {
        var entity = repository.getReferenceById(id);
        entity.inativar();
        return entity;
    }
}
