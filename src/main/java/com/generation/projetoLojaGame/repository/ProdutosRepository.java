package com.generation.projetoLojaGame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


import com.generation.projetoLojaGame.model.Produtos;

public interface ProdutosRepository  extends JpaRepository<Produtos , Long>{

	
	public List <Produtos> findAllByNomeContainingIgnoreCase(@Param("nome")String nome);
	
	//findAllByTitulo: vai fazer um select buscando por nome
	// Containing : é uma busca NÃO exata , como se fosse o LIKE , que busca parcialmente o que vc quer e trás os resultados
	//IgnoreCase: Mesmo que eu escreva com maiuscula ou minuscula ele ignorar e respeitar somente o texto
	//@Param ("titulo") String titulo): @Param está confirmando que vai receber um parametro titulo que é uma String Titulo
}
