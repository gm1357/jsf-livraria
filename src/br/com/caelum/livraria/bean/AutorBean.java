package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.tx.Transactional;

@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AutorDao dao;
	
	private Autor autor = new Autor();
	private Integer autorId;

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPelaId() {
	    this.autor = this.dao.buscaPorId(autorId);
	}

	public Autor getAutor() {
		return autor;
	}
	
	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}
	
	@Transactional
	public void remover(Autor autor) {
		this.dao.remove(autor);
	}

	@Transactional
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			this.dao.adiciona(this.autor);
		} else {
			this.dao.atualiza(this.autor);
		}

		this.autor = new Autor();
		
		return "autor?faces-redirect=true";
	}
}
