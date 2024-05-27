package com.generation.projetoLojaGame.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.generation.projetoLojaGame.model.Produtos;
import com.generation.projetoLojaGame.repository.CategoriaRepository;
import com.generation.projetoLojaGame.repository.ProdutosRepository;

import jakarta.validation.Valid;

@RestController  //anotação indicando para a Spring que essa classe é uma controller
@RequestMapping ("/produtos") //rota para chegar nessa classe no insomnia
@CrossOrigin(origins = "*", allowedHeaders = "*") //libera o acesso a outras maquinas, o * indica que qualquer máquina pode acessar / allowedHeaders = liberar passagem de parametros no header
public class ProdutosController {

	
	@Autowired //injeção de dependencia
	private ProdutosRepository produtosRepository;
	
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//LISTA todas os produtos 
	
	
	@GetMapping //Verbo HTTP
	public ResponseEntity<List<Produtos>> getAll(){
		return ResponseEntity.ok(produtosRepository.findAll()); // ele vai dar um ok caso de certo a requisição , retornando um numero 200 ok , que é a indicação que deu certo

		//ResponseEntity : é um formato de retorno de dados em HTTP , que retorna se deu certo ou não
	
		}

	
	
	//BUSCAR a postagem por ID
	
	@GetMapping("/{id}") //busca por id
	public ResponseEntity<Produtos> getById(@PathVariable Long id){
		
		
		return produtosRepository.findById(id)
		.map(resposta -> ResponseEntity.ok(resposta))//vai mapear as respotas e vai dar uma mensagem de retorno caso dê ok 
		.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		
		// @PathVariable Long id: Esta anotação insere o valor enviado no endereço do endpoint e Long porque é o tipo do dado Id
		// getById : Vai buscar a postagem  por id mencionado
		//finByid: é a mesma coisa que :  SELECT * FROM tb_postagem WHERE id = 1 
		//.orElse : Caso o usuario coloque um id que não existe , ele vai aparecer a mensagem do HTTPStatus "not found" não encontrado
		
	}
	
	//SELECIONAR por nome
	
	@GetMapping ("/nome/{nome}") 
	public  ResponseEntity<List<Produtos>> getByNome(@PathVariable String nome){
		return  ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
		// retorna o nome , com o metodo personalizado que que criamos na repository
		
		
	}
	
	//CADASTRAR produtos
	
	@PostMapping //cria uma requisição
	public ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produto){
	
		if(categoriaRepository.existsById(produto.getCategoria().getId()))
			//status(HttpStatus.CREATED: Vai informar em formato Http que foi adicionado um jogo
			return ResponseEntity.status(HttpStatus.CREATED)//retorna que foi criado no botão
					.body(produtosRepository.save(produto));//save : metodo da repository , que vai fazer um INSERT INTO , e mostrar , ou seja , ele salva o produto e retorna a mesmo , então nesse caso o ResponseEntity tem um corpo
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST," A Categoria não existe!", null);//caso eu cadastrar um tema que não existe
	}
	
	
	@PutMapping //altera e atualiza
	public ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produto){
		
		if(produtosRepository.existsById(produto.getId())) {//verifica se o jogo existe 
			if(categoriaRepository.existsById(produto.getCategoria().getId()))//verifica se a categoria  também existe
				return ResponseEntity.status(HttpStatus.OK)
						.body(produtosRepository.save(produto));//salva
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A Categoria não existe!", null);// caso eu queira cadastrar um tema que não existe
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//caso não encontre a categoria
		
	}
	
	
	//DELETAR jogo
	
	//optional : não quebra a aplicação e retorna algo sem quebrar o código mesmo que o dado esteja vazio
	
	@DeleteMapping("/{id}") //aqui ele vai garantir que vai pedir um numero de id para deletar(SEGURANÇA)
	public void delete(@PathVariable Long id) {
		Optional<Produtos> produtos = produtosRepository.findById(id); //vai trazer a linha que eu mencionei
		if(produtos.isEmpty())//objeto  postagem está vazio?
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);//retorna um status de exception , caso não encontre o id , com a mensagem NOT FOUND
		
		produtosRepository.deleteById(id); //caso encontre vai pegar o objeto postagem com o id mencionado 
		
	}
	
	



}


