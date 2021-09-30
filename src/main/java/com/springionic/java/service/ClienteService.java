package com.springionic.java.service;

import com.springionic.java.domain.Categoria;
import com.springionic.java.domain.Cidade;
import com.springionic.java.domain.Cliente;
import com.springionic.java.domain.Endereco;
import com.springionic.java.domain.enums.Perfil;
import com.springionic.java.domain.enums.TipoCliente;
import com.springionic.java.dto.ClienteDTO;
import com.springionic.java.dto.ClienteNewDTO;
import com.springionic.java.repository.CidadeRepository;
import com.springionic.java.repository.ClienteRepository;
import com.springionic.java.repository.EnderecoRepository;
import com.springionic.java.security.UserSS;
import com.springionic.java.service.exception.AuthorizationExcetion;
import com.springionic.java.service.exception.DataIntegrityException;
import com.springionic.java.service.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private BCryptPasswordEncoder be;

    @Autowired
    private S3Service s3Service;

    public Cliente find(Integer id) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationExcetion("Acesso negado");
        }

        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto Não Encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();

    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateDate(newObj, obj);
        return clienteRepository.save(newObj);
    }


    public void delete(Integer id) {
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Cliente que possui pedidos");
        }

    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    //Criado para converter uma Cliente para DTO e usar @Valid
    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), be.encode(objDto.getSenha()));
        Cidade cid = cidadeRepository.findById(objDto.getCidadeId()).orElseThrow(() -> new EntityNotFoundException());
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.getTelefone1());
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;

    }

    private void updateDate(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        return s3Service.uploadFile(multipartFile);
    }
}
