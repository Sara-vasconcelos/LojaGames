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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.projetoLojaGame.model.Categoria;
import com.generation.projetoLojaGame.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController ////anotação indicando para a Spring que essa classe é uma controller
@RequestMapping("/categorias") //rota para chegar nessa classe no insomnia 
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class CategoriaController {

	
	@Autowired  //injeção de dependencia
	 private CategoriaRepository categoriaRepository;
	
	
	
    
    @GetMapping // verbo http que recupera os dados
    public ResponseEntity<List<Categoria>> getAll(){//pega tudo
        return ResponseEntity.ok(categoriaRepository.findAll()); // faz a busca , e vai  dar um ok caso de certo a requisição , retornando um numero 200 ok , que é a indicação que deu certo
   
        
        
}
    
  //BUSCAR tema  por ID
    
    @GetMapping("/{id}")//dessa forma {id} , informa que o  dado é esperado uma variavel, nesse caso vai mostrar  o numero do id , ele sabe que não vai receber um texto e sim um número
    public ResponseEntity<Categoria> getById(@PathVariable Long id){
    	
        return categoriaRepository.findById(id)//  está sendo retornado no findById TUDO (findById:ele retorna tudo)
            .map(resposta -> ResponseEntity.ok(resposta))//vai mapear as respotas e vai dar uma mensagem de retorno caso dê ok 
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}
    
    
    //SELECIONAR por categoria
    
    @GetMapping("/categoriajogo/{categoriajogo}")
    public ResponseEntity<List<Categoria>> getByTitle(@PathVariable 
    String categoriajogo){
        return ResponseEntity.ok(categoriaRepository
            .findAllByCategoriajogoContainingIgnoreCase(categoriajogo));
      //esse retorna a descricao , com o metodo personalizado que que criamos na repository
    }
    
    
    //CADASTRAR
    
    @PostMapping
    public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaRepository.save(categoria));
    }
    
    
    //ATUALIZAR 
    @PutMapping
    public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
        return categoriaRepository.findById(categoria.getId())//faz a busca
            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)//atualiza 
            .body(categoriaRepository.save(categoria)))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());//retorna Not found caso não encontre
    }
    
    //DELETAR
    
    @ResponseStatus(HttpStatus.NO_CONTENT)//mensagem em formato HTTP , que aparece no botão do insomnia 
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);//verifica se existe
        
        if(categoria.isEmpty())//verifica se existe
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);//caso não encontre
        
        categoriaRepository.deleteById(id); //deleta com o id solicitado             
    }
}