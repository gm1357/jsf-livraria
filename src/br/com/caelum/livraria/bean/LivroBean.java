package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Transactional;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LivroDao livroDao;
	
	@Inject
	private AutorDao autorDao;
	
	@Inject
	private FacesContext context;

	private Livro livro = new Livro();
	private Integer autorId;
	private List<Livro> livros;

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	public void carregar(Livro livro) {
	    System.out.println("Carregando livro");
	    this.livro = this.livroDao.buscaPorId(livro.getId());
	}

	public List<Livro> getLivros() {

	    if(this.livros == null) {
	        this.livros = this.livroDao.listaTodos();            
	    }

	    return livros;
	}

	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void gravarAutor() {
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	@Transactional
	public void gravar() {
	    System.out.println("Gravando livro " + this.livro.getTitulo());

	    if (livro.getAutores().isEmpty()) {
	        context.addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
	        return;
	    }

	    if(this.livro.getId() == null) {
	    	this.livroDao.adiciona(this.livro);
	        this.livros = this.livroDao.listaTodos();
	    } else {
	    	this.livroDao.atualiza(this.livro);
	    }

	    this.livro = new Livro();
	}
	
	@Transactional
	public void remover(Livro livro) {
	    System.out.println("Removendo livro " + livro.getTitulo());
	    livroDao.remove(livro);
	    this.livros = this.livroDao.listaTodos();
	}
	
	public void removerAutorDoLivro(Autor autor) {
	    this.livro.removeAutor(autor);
	}
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

	    String valor = value.toString();
	    if (!valor.startsWith("1")) {
	        throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
	    }
	}
	
	public String formAutor() {
        System.out.println("Chamanda o formulario do Autor");
        return "autor?faces-redirect=true";
    }
}
