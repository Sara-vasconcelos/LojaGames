package com.generation.projetoLojaGame.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity //vai ser uma tabela (entidade)
@Table(name="tb_produtos")  //nome da tabela
public class Produtos {
	
	@Id // torna o id uma chave primaria
	@GeneratedValue(strategy=GenerationType.IDENTITY) // faz com que a chave seja auto increment
	private Long id; //tipo long no Java  e no banco de dados será criado como BIGINT
	
	@NotBlank(message = "Este campo é obrigatorio")//validation , vai validar o atributo NOT NULL e não deixa ir vazio
	@Size(min=5,max=100, message  = "O nome do produto deve ter no minimo 5 caracteres e no maximo 100") // tamanho min e max de caracter, caso o usuario não cumpra essa regra aparece essa mensagem
	private String  nome;
	
	@NotBlank(message = "Este campo  é obrigatório")//o campo fica em branco se não passar nada 
	@Size(min=5,max=1000, message  = "A descricao deve ter no minimo 5 caracteres e no maximo 1000")
	private String descricao;
	
	@ManyToOne //Um pra muitos - n-1
	@JsonIgnoreProperties("produto")// indica que uma parte do JSON será ignorado, impede que um looping seja criado
	private Categoria categoria;//Será criado um Objeto da Classe Tema, que receberá os dados do Tema associado ao Objeto da Classe Postagem. Este Objeto representa a Chave Estrangeira da Tabela tb_postagens (tema_id).


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	
	
	

}
