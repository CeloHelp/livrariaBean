package br.com.projeto.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import br.com.projeto.model.ItemPedido;
import br.com.projeto.services.AssociacaoService;
import br.com.projeto.services.ClienteService;
import br.com.projeto.services.LivroService;
import br.com.projeto.services.PedidoService;
import br.com.projeto.services.VendedorService;

@ManagedBean
@SessionScoped
public class AcoesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isbn; // Armazena o código ISBN do Livro
    private String titulo; // Armazena o titulo do livro
    private LivroBean livroSelecionado; // Livro atualmente selecionado
    private LivroDAO livroDAO = new LivroDAO(); // DAO para manipulação do banco de dados
    private List<LivroBean> livros; // Lista de livros carregada a partir do banco de dados
    private LivroBean selectedLivro; // Livro selecionado na interface
    private int progressoLeitura; // Progresso de leitura em porcentagem
    private String tituloSelecionado;  // Título selecionado no autocomplete
    private LivroBean livroDetalhes; // Detalhes do livro selecionado
    private boolean livroCompleteSelecionado; // Flag para controle da exibição
    private List <LivroBean> pedido = new ArrayList<>();
    private String tituloFiltro;
    private PedidoBean pedidobean;
    private List<LivroBean> livrosFiltrados;
    private LivroService livroService;
    private List<ItemPedido> itensPedido = new ArrayList<>();
    private ClienteService clienteService;
    private VendedorService vendedorService;
    private List<ClienteBean> listaClientes;
    private List<VendedorBean> listaVendedores;

    	
    
    @ManagedProperty(value = "#{pedidoService}")
    private PedidoService pedidoService;
    
    private PedidoBean pedidoAtual;
    
    
    
 

    @ManagedProperty(value = "#{livroBean}") // Injeção do LivroBean
    private LivroBean livroBean;

    
    @SuppressWarnings("unused")
    private List<LivroBean> filteredLivros;// Lista de livros filtrados para pesquisa

	private int clienteIdSelecionado;

	private int vendedorIdSelecionado;

	private AssociacaoService associacaoService;
    
    

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
    System.out.println("AcoesBean inicializado");
    livros = livroDAO.listarLivros();
    if (livros == null || livros.isEmpty()) {
        System.out.println("Nenhum livro encontrado no banco de dados.");
    } else {
        System.out.println("Livros carregados: " + livros.size());
         } if(livros != null) {
        	 filteredLivros = new ArrayList<>(livros);
         } else {
        	 filteredLivros = new ArrayList<>();
         }
         
         System.out.println("Livros carregados: " + (livros != null ? livros.size() : "NULO"));
         
        }
    
    
    public void initBancoDados() {
  	  try {
  		  Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + "livrariadb", "postgres", "root");
  		 this.associacaoService = new AssociacaoService(connection);
  	  } catch (SQLException e) {
  		  e.printStackTrace();
  	  }
    }
    
    @PostConstruct
    public void initPedido() {
        if (pedidoService == null) {
            pedidoService = new PedidoService();
            System.out.println("PedidoService inicializado corretamente.");
        }

        if (pedidobean == null) {
            pedidobean = new PedidoBean();
            System.out.println("PedidoBean inicializado corretamente.");
        }
    }

    
    @PostConstruct
    public void initLivros() {
    	System.out.println("Inicializando os livros");
    	
    	if(livroService == null) {
    		System.out.println("Livro Service Nulo");
    		this.livroService = new LivroService();
    	}
    	
    	livros = livroService.listarTodos();
    	
    	if (livros == null || livros.isEmpty()) {
    		System.out.println("Nenhum livro encontrado no banco de dados.");
    		
    	} else {
    		System.out.println("Livros carregados: " + livros.size());
    		for (LivroBean livro : livros) {
    			System.out.println("Titulo: " + livro.getTitulo());
    		}
    	}
    	
    	filteredLivros = (livros != null) ? new ArrayList<>(livros) : new ArrayList<>();
    	
    	System.out.println("Filtered inicializado: " + filteredLivros.size());
    }
    
    @PostConstruct
    public void initVendedoresEClientes() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/livrariadb", "postgres", "root");
            this.clienteService = new ClienteService(connection);
            this.vendedorService = new VendedorService(connection);

            carregarClientes();
            carregarVendedores();
            
            System.out.println("Clientes carregados no AcoesBean: " + listaClientes.size());
            System.out.println("Vendedores carregados no AcoesBean: " + listaVendedores.size());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    // ---------- Getters e Setters ----------  
    
    
    public LivroBean getLivroBean() {
    	return livroBean;
    }
    
    public void setLivroBean(LivroBean livroBean) {
    	System.out.println("Injetando LivroBean");
        this.livroBean = livroBean;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public String getTitulo() {
		return titulo;
	}
    
    public void setTitulo(String titulo) {
    	this.titulo = titulo;
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
    
    public String getTituloSelecionado() {
        return tituloSelecionado;
    }

    public void setTituloSelecionado(String tituloSelecionado) {
        this.tituloSelecionado = tituloSelecionado;
    }
    
    public LivroBean getLivroDetalhes() {
        return livroDetalhes;
    }

    public boolean isLivroCompleteSelecionado() {
		return livroCompleteSelecionado;
	}

	public void setLivroCompleteSelecionado(boolean livroCompleteSelecionado) {
		this.livroCompleteSelecionado = livroCompleteSelecionado;
	}
    
	 public List<LivroBean> getPedido() {
	        return pedido;
	}
	
     public String getTituloFiltro() {
		    return tituloFiltro;
    }

	public void setTituloFiltro(String tituloFiltro) {
		    this.tituloFiltro = tituloFiltro;
	}
	
	public List<LivroBean> getFilteredLivros() {
		System.out.println("chamando getFilteredLivros() - tamanho da lista: "
				+ (filteredLivros != null ? filteredLivros.size() : "NULO"));
        return filteredLivros;
    }
	
	 public int getClienteIdSelecionado() {
	        return getClienteIdSelecionado();
	    }

	    public void setClienteIdSelecionado(int clienteIdSelecionado) {
	        this.clienteIdSelecionado = clienteIdSelecionado;
	    }

	    public int getVendedorIdSelecionado() {
	        return getVendedorIdSelecionado();
	    }

	    public void setVendedorIdSelecionado(int vendedorIdSelecionado) {
	        this.vendedorIdSelecionado = vendedorIdSelecionado;
	    }
	    
	    public void setPedidoService(PedidoService pedidoService) {
	        this.pedidoService = pedidoService;
	    }
	    
	    public PedidoBean getPedidoAtual() {
	  	  if (pedidoAtual == null) {
	  	        pedidoAtual = new PedidoBean();
	  	    }
	  	    return pedidoAtual;
	    }
	    
	    public List<ItemPedido> getItensPedido() {
	       
			if (itensPedido == null) {
	            itensPedido = new ArrayList<>();
	        }
	        return itensPedido;
	    }
	    
	    public List<ClienteBean> getListaClientes() {
	        
	    	if(listaClientes == null || listaClientes.isEmpty()) {
	    	     try {
	    	    	 Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/livrariadb", "postgres", "root");
	    	    	 ClienteService clienteService = new ClienteService(conn);
	    	    	 listaClientes = clienteService.listarTodos();
	    	    	 conn.close();
	    	     } catch(SQLException e) {
	    	    	 e.printStackTrace();
	    	    	 
	    	    	 System.out.println("Vendedores carregados: " + (listaVendedores != null ? listaVendedores.size() : "Lista nula"));
	    	     }
	    	}
	    	
	    	return listaClientes;
	    }

	    public List<VendedorBean> getListaVendedores() {
	       if(listaVendedores == null || listaVendedores.isEmpty()) {
	    	   try {
	    		   Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/livrariadb", "postgres", "root");
	               VendedorService vendedorService = new VendedorService(conn);
	               listaVendedores = vendedorService.listarTodos();
	               conn.close();
	    	   } catch(SQLException e) {
	    		   e.printStackTrace();
	    		   
	    		   System.out.println("Vendedores carregados: " + (listaVendedores != null ? listaVendedores.size() : "Lista nula"));

	    	   }
	       }
	         return listaVendedores; 
	        
	    }
	


    // ---------- Métodos ----------
    
    public String acaoSalvar() {
    	System.out.println("Chamando acaoSalvar");
    	if(livroBean != null) {
    		System.out.println("Livro sendo salvo: " + livroBean.getTitulo());
    	}else {
    		System.out.println("LivroBean Nulo ");
    	}
        try {
            livroDAO.salvar(livroBean);
            livroBean = new LivroBean();
            return "meusLivros?faces-redirect=true";
        } catch (Exception e) {
        	System.out.println("Erro ao salvar Livro:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    } 
    
    
    public String acaoProximaPagina() {
        return "meusLivros?faces-redirect=true";
    }
    
    public String acaoLoja(String isbn) {
    	return "vendaLivro?faces-redirect=true&amp;isbn=" + isbn;
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
    
    
    public List<String> completeTitulo(String query){
    	
    	String filterText = (query == null) ? "" : query.trim().toLowerCase(Locale.getDefault());
    	
    	return livroDAO.listarLivros().stream()
    			.filter(livro -> livro.getTitulo() != null && livro.getTitulo().toLowerCase().contains(filterText))
    			.map(LivroBean :: getTitulo) // Extrair apenas os títulos
    			.collect(Collectors.toList());
    	
    }
    
    public void buscarDetalhesLivro() /*{
    	if (tituloSelecionado != null && !tituloSelecionado.isEmpty()) {
    		livroDetalhes = livroDAO.listarLivros().stream()
    				.filter(livro -> livro.getTitulo().equals(tituloSelecionado))
    				.findFirst()
    				.orElse(null);
    		livroCompleteSelecionado = (livroDetalhes != null);
    	}else {
    		livroCompleteSelecionado = false;
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum título selecionado!", null));
    	    System.out.println("Livro selecionado: " + livroDetalhes);
    	}
    }*/
 {
    	// Log para verificar o título selecionado
        System.out.println("Título selecionado no autocomplete: " + tituloSelecionado);

        // Verifica se o título selecionado não está vazio
        if (tituloSelecionado != null && !tituloSelecionado.isEmpty()) {
            // Busca o livro com o título selecionado na lista retornada pelo DAO
            livroDetalhes = livroDAO.listarLivros().stream()
                    .filter(livro -> livro.getTitulo().equals(tituloSelecionado))
                    .findFirst()
                    .orElse(null);

            // Verifica se o livro foi encontrado
            if (livroDetalhes != null) {
                System.out.println("Livro encontrado: ");
                System.out.println("Título: " + livroDetalhes.getTitulo());
                System.out.println("Autor: " + livroDetalhes.getAutor());
                System.out.println("ISBN: " + livroDetalhes.getIsbn());
                System.out.println("Preço: " + livroDetalhes.getPreco());
                System.out.println("Imagem: " + livroDetalhes.getImagem());
                livroCompleteSelecionado = true; // Atualiza a flag para exibir o painel
            } else {
                System.out.println("Livro não encontrado para o título: " + tituloSelecionado);
                livroCompleteSelecionado = false; // Não exibe o painel
            }
        } else {
            System.out.println("Nenhum título selecionado ou título está vazio.");
            livroCompleteSelecionado = false; // Não exibe o painel
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum título selecionado!", null));
        }

        // Log final para verificar a flag de exibição do painel
        System.out.println("Flag livroCompleteSelecionado: " + livroCompleteSelecionado);
    }

   
    public void adicionarLivroAoPedido(LivroBean livro) {
        pedidoAtual.adicionarLivro(livro);
        System.out.println("Livro adicionado ao pedido: " + livro.getTitulo());
        System.out.println("Total de livros no pedido: " + pedidoAtual.getLivros().size());
    }

    public void removerLivroDoPedido(LivroBean livro) {
        pedidoAtual.removerLivro(livro);
        System.out.println("Livro removido do pedido: " + livro.getTitulo());
    }

  
  public void finalizarPedido() {
	  pedidoAtual.setDataPedido(new Date(System.currentTimeMillis()));
	    try {
	        pedidoService.salvar(pedidoAtual);
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_INFO, "Pedido finalizado com sucesso!", null));
	        
	        // Reinicializa o pedido atual para um novo pedido
	        pedidoAtual = new PedidoBean();
	    } catch (SQLException e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao finalizar pedido: " + e.getMessage(), null));
	        e.printStackTrace(); // Exibe o erro no console para depuração
	    }
	}

  

    
  public boolean tituloFiltro(LivroBean livro, Object filter, Locale locale) {
	  if (filter == null || filter.toString().trim().isEmpty()) {
		   return true;
	  }
	  
	  
	  return(livro.getTitulo() != null && livro.getTitulo().toLowerCase().contains(tituloFiltro.toLowerCase()));
	  
  }
    
  public void atualizarFiltro() {
	  System.out.println("Filtro digitado: " + tituloFiltro);
	  
	  if (tituloFiltro == null || tituloFiltro.trim().isEmpty()) {
		  filteredLivros = new ArrayList<>(livros);
		  System.out.println("Nenhum filtro. Exibindo todos os livros");
	  } else {
		  String filtroLower = tituloFiltro.toLowerCase();
		  filteredLivros = livros.stream()
				  .filter(livro -> livro.getTitulo() != null &&
				                   livro.getTitulo().toLowerCase().contains(filtroLower))
				  .collect(Collectors.toList());
		  
		  System.out.println("Livros filtrados: " + filteredLivros.size());
	  }
  }
  
  
  
  public void associarClienteAVendedor() {
      try {
          associacaoService.associarClienteAVendedor(clienteIdSelecionado, vendedorIdSelecionado);
          FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente associado ao vendedor com sucesso!", null));
      } catch (SQLException e) {
          FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao associar cliente ao vendedor", null));
          e.printStackTrace();
      }
  }
  
  public void carregarClientes() {
      try {
          listaClientes = clienteService.listarTodos();
      } catch (SQLException e) {
          e.printStackTrace();
          listaClientes = new ArrayList<>();
      }
  }
  
  public void carregarVendedores() {
      try {
          listaVendedores = vendedorService.listarTodos();
      } catch (SQLException e) {
          e.printStackTrace();
          listaVendedores = new ArrayList<>();
      }
  }
                                         
}