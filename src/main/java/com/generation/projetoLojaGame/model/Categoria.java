package com.generation.projetoLojaGame.model;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity  //indica uma criação de tabela(entidade)
@Table(name = "tb_categorias")//nome da tabela 
public class Categoria {
	
	
	@Id  // torna o id uma chave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // faz com que a chave seja auto increment
	private Long id;
	
	
	@NotBlank(message = "Este campo é obrigatorio") //validation , vai validar o atributo NOT NULL e não deixa o campo vazio
	@Size(min=4,max=1000, message  = "Este campo deve ter no minimo 4 caracteres e no maximo 1000")
	private String categoriajogo;//tipo string no Java e no banco será VARCHAR(255)

	
	//fetch: Padrão de como eu quero trazer essas informações relacionadas, Lazy: preguiçoso , só trás se eu pedir. Eager : rápido , trás tudo de uma vez
	//fetchType.Lazy: Modo preguiçoso , trás somente se pedir , para não sobrecarregar
		
	//mappedBy: Ele determina por onde vai começar o mapeamento , no caso vai começar por tema 
	//cascade: qual o tipo de cascata , tem vários 
	//CascadeType.REMOVE: Se eu apagar o Categoria o produto é apagado também, por isso em cascata 
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy =  "categoria" , cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("produto")//evita criar um loop
	private List<Produtos> produto;// terá uma Collection List contendo Objetos da Classe Produtos

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCategoriajogo() {
		return categoriajogo;
	}


	public void setCategoriajogo(String categoriajogo) {
		this.categoriajogo = categoriajogo;
	}


	public List<Produtos> getProduto() {
		return produto;
	}


	public void setProduto(List<Produtos> produto) {
		this.produto = produto;
	}

	
	
	
}
