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

@ManagedBean
@ViewScoped
public class AcoesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isbn; // Armazena o código ISBN do Livro
    private LivroBean livroSelecionado; // Livro atualmente selecionado
    private LivroDAO livroDAO = new LivroDAO(); // DAO para manipulação do banco de dados
    private List<LivroBean> livros; // Lista de livros carregada a partir do banco de dados
    private LivroBean selectedLivro; // Livro selecionado na interface
    private int progressoLeitura; // Progresso de leitura em porcentagem

    @ManagedProperty(value = "#{livroBean}") // Injeção do LivroBean
    private LivroBean livroBean;

    @SuppressWarnings("unused")
    private List<LivroBean> filteredLivros; // Lista de livros filtrados para pesquisa

    // ---------- Inicialização ----------

    @PostConstruct
    public void init() {
        System.out.println("Inicializando AcoesBean e carregando os livros...");
        livros = livroDAO.listarLivros(); // Chamada ao DAO para buscar os livros
        if (livros == null || livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados.");
        } else {
            System.out.println("Livros carregados: " + livros.size());
            livros.forEach(livro -> System.out.println("Título: " + livro.getTitulo()));
        }
    }

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

    public int getProgressoLeitura() {
        return progressoLeitura;
    }

    // ---------- Métodos ----------
    
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

    public String acaoProximaPagina() {
        return "meusLivros?faces-redirect=true";
    }

    public void acaoExcluirLivro(String isbn) {
        try {
            livroDAO.excluirLivro(isbn);
            livros = livroDAO.listarLivros(); // Atualiza a lista após exclusão
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Livro excluído com sucesso!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir livro", null));
        }
    }

    public void acaoAtualizarProgressoLeitura() {
        if (livroSelecionado != null) {
            try {
                livroDAO.atualizarProgressoLeitura(livroSelecionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Progresso atualizado com sucesso!", null));
                calcularProgresso();
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

    public void calcularProgresso() {
        if (livroSelecionado != null && livroSelecionado.getPaginaAtual() > 0
                && livroSelecionado.getPaginasTotais() > 0) {
            if (livroSelecionado.getPaginaAtual() > livroSelecionado.getPaginasTotais()) {
                livroSelecionado.setPaginaAtual(livroSelecionado.getPaginasTotais());
                this.progressoLeitura = 100; // Trava o progresso em 100%
                PrimeFaces.current().executeScript("mostrarDialogLimite()");
            } else {
                this.progressoLeitura = (livroSelecionado.getPaginaAtual() * 100) / livroSelecionado.getPaginasTotais();
            }
        } else {
            this.progressoLeitura = 0; // Nenhum progresso se as páginas lidas forem zero
        }
    }

    public String acaoMaisDetalhes(String isbn) {
        return "detalhesLivro?faces-redirect=true&amp;isbn=" + isbn;
    }

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
