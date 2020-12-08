package br.com.bruno.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.bruno.desafio.controller.exception.IdNaoEncontradoException;
import br.com.bruno.desafio.model.Pessoa;
import br.com.bruno.desafio.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa buscaPorId(Long id) {
		return pessoaRepository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Id não encontrado!"));
	}

	public Page<Pessoa> buscaPorNomeCliente(Pageable pageable, String nome) {
		Page<Pessoa> page = pessoaRepository.findByNome(nome, pageable);
		return page;
	}

	public void delete(Long id) {
		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow(() -> new IdNaoEncontradoException("Id não encontrado!"));
		pessoaRepository.delete(pessoa);
	}

	public Pessoa adicionar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public Pessoa atualiza(Pessoa pessoa, Long id) {
		Pessoa pessoaBD = pessoaRepository.findById(id)
				.orElseThrow(() -> new IdNaoEncontradoException("Id não encontrado!"));
		pessoaBD.setNome(pessoa.getNome());
		return pessoaRepository.save(pessoaBD);
	}

}
