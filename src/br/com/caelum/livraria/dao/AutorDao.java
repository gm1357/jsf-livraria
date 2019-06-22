package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.livraria.modelo.Autor;

public class AutorDao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	private DAO<Autor> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Autor>(this.em, Autor.class);
	}

	public Autor buscaPorId(Integer autorId) {
		return dao.buscaPorId(autorId);
	}

	public List<Autor> listaTodos() {
		return dao.listaTodos();
	}

	public void remove(Autor autor) {
		dao.remove(autor);
	}

	public void adiciona(Autor autor) {
		dao.adiciona(autor);
	}

	public void atualiza(Autor autor) {
		dao.atualiza(autor);
	}

}
