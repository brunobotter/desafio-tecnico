package br.com.bruno.desafio.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.desafio.model.Pessoa;
import br.com.bruno.desafio.service.PessoaService;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private PagedResourcesAssembler<Pessoa> assembler;
	
	@GetMapping("{id}")
	public ResponseEntity<Pessoa> buscaPorId(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.buscaPorId(id);
		return new ResponseEntity<>(pessoa, HttpStatus.OK);
	}
	
	@GetMapping("/busca/")
	public ResponseEntity<?> buscaTodosPorNomeCliente(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limite", defaultValue = "20") int limit) {

		Pageable pageable = PageRequest.of(page, limit);

		Page<Pessoa> pessoa  = pessoaService.buscaPorNomeCliente(pageable, nome);
		
		PagedModel<?> resources = assembler.toModel(pessoa);

		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleta(@PathVariable Long id) {
		pessoaService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping()
	public ResponseEntity<Pessoa> adiciona(@Valid @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaService.adicionar(pessoa);
		return new ResponseEntity<>(pessoaSalva, HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Pessoa> atualiza(@Valid @RequestBody Pessoa pessoa, @PathVariable Long id) {
		Pessoa pessoaSalva = pessoaService.atualiza(pessoa, id);
		return new ResponseEntity<>(pessoaSalva, HttpStatus.OK);
	}
}
