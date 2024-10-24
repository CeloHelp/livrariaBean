package br.com.projeto.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

@ManagedBean //Anotação utilizada para definir uma classe como um bean gerenciado pelo JSF,
             //Logo podendo ser utilizada nas páginas JSF através da expression language ex: #{nomeDoBean}

@ViewScoped // Escopo utilizado para a persistência dos Beans nas páginas JSF
public class AcoesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 private String isbn;//Armazena o código ISBN do Livro
	    private LivroBean livroSelecionado;
	    private LivroDAO livroDAO = new LivroDAO(); //Cria um novo objeto livroDAO
	    private List<LivroBean> livros; // Lista de livros carregada a partir do banco de dados
	    private LivroBean selectedLivro;
	    private int progressoLeitura; // Armazena o progresso de leitura

	    @ManagedProperty(value = "#{livroBean}") //Injetando os beans do livroBean em outro Bean Para trabalharmos com ele                                                                                 
	    private LivroBean livroBean;
	   

	    @SuppressWarnings("unused")
		private List<LivroBean> filteredLivros; // Lista de livros filtrados para pesquisa

	    // ---------- Getters e Setters ----------
	    public String getIsbn() {
	        return isbn;
	    }

	    public void setIsbn(String isbn) {
	        this.isbn = isbn;
	        if (isbn != null && !isbn.isEmpty()) {
	            this.livroSelecionado = livroDAO.buscarLivroPorIsbn(isbn);
	            if (this.livroSelecionado == null) {
	                FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Livro não encontrado!", null));
	            } else {
	                calcularProgresso(); // Calcular o progresso automaticamente ao carregar o livro
	            }
	        } else {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "ISBN inválido!", null));
	        }
	    }

	    public LivroBean getLivroSelecionado() {
	        return livroSelecionado;
	    }

	    public void setLivroSelecionado(LivroBean livroSelecionado) {
	        this.livroSelecionado = livroSelecionado;
	    }

	    public void setLivroBean(LivroBean livroBean) {
	        this.livroBean = livroBean;
	    }

	    public List<LivroBean> getLivros() {
	        return livros;
	    }

	    public void setFilteredLivros(List<LivroBean> filteredLivros) {
	        this.filteredLivros = filteredLivros;
	    }

	    public LivroBean getSelectedLivro() {
	        return selectedLivro;
	    }

	    public void setSelectedLivro(LivroBean selectedLivro) {
	        this.selectedLivro = selectedLivro;
	    }

	    public boolean isHasSelectedBooks() {
	        return selectedLivro != null;
	    }

	    // --------- Métodos ----------
	    @PostConstruct
	    public void init() {
	        livros = livroDAO.listarLivros();
	    }

	    // Método para salvar o livro
	    public String acaoSalvar() {
	        try {
	            livroDAO.salvar(livroBean);
	            
	            livroBean = new LivroBean();
	            return "resultado";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    // Navegar para a página de livros cadastrados
	    public String acaoProximaPagina() {
	        return "meusLivros?faces-redirect=true";
	    }

	    // Método para excluir um livro
	    public void acaoExcluirLivro(String isbn) {
	        try {
	            livroDAO.excluirLivro(isbn);
	            livros = livroDAO.listarLivros();  // Atualizar a lista de livros
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Livro excluído com sucesso!", null));
	        } catch (Exception e) {
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir livro", null));
	        }
	    }

	    // Atualizar o progresso de leitura e recalcular o progresso
	    public void acaoAtualizarProgressoLeitura() {
	        if (livroSelecionado != null) {
	            try {
	                livroDAO.atualizarProgressoLeitura(livroSelecionado);
	                FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Progresso atualizado com sucesso!", null));
	                calcularProgresso(); // Recalcular o progresso após atualizar
	            } catch (Exception e) {
	                FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar progresso de leitura", null));
	                e.printStackTrace();
	            }
	        } else {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Livro não selecionado", null));
	        }
	    }

	    // Calcular o progresso de leitura em porcentagem
	    public void calcularProgresso() {
	        if (livroSelecionado != null && livroSelecionado.getPaginaAtual() > 0 && livroSelecionado.getPaginasTotais() > 0) {
	        	if (livroSelecionado.getPaginaAtual() > livroSelecionado.getPaginasTotais()) {
	        		livroSelecionado.setPaginaAtual(livroSelecionado.getPaginasTotais());
	        		this.progressoLeitura = 100;// trava o progresso em 100% se as páginas lidas forem maiores que o total
	        		
	        		// Chama o comando remoto para exibir o diálogo
	                PrimeFaces.current().executeScript("mostrarDialogLimite()");
	        		
	        	} else {
	        		this.progressoLeitura = (livroSelecionado.getPaginaAtual()* 100) / livroSelecionado.getPaginasTotais();
	        	}
	        }else {
	        		this.progressoLeitura = 0; // Se não houver páginas lidas ou o total de páginas for 0
	        	}
	        }
	         

	    // Getter para o progresso
	    public int getProgressoLeitura() {
	        return progressoLeitura;
	    }

	    // Navegar para mais detalhes do livro
	    public String acaoMaisDetalhes(String isbn) {
	        return "detalhesLivro?faces-redirect=true&amp;isbn=" + isbn;
	    }

	    // Filtro global nos livros
	    public boolean globalFilterFunction(LivroBean livro, Object filter, Locale locale) {
	        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase(locale);
	        if (filterText == null || filterText.isEmpty()) {
	            return true;
	        }
	        return (livro.getTitulo() != null && livro.getTitulo().toLowerCase(locale).contains(filterText))
	                || (livro.getAutor() != null && livro.getAutor().toLowerCase(locale).contains(filterText))
	                || (livro.getIsbn() != null && livro.getIsbn().toLowerCase(locale).contains(filterText))
	                || (livro.getPreco() != null && String.valueOf(livro.getPreco()).toLowerCase(locale).contains(filterText));
	    }
	




}

