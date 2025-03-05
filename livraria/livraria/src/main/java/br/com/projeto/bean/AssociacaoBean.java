package br.com.projeto.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.projeto.dao.DbFunctions;
import br.com.projeto.services.AssociacaoService;
import br.com.projeto.services.ClienteService;
import br.com.projeto.services.VendedorService;


@ManagedBean
@SessionScoped
public class AssociacaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private AssociacaoService associacaoService;
    private ClienteService clienteService;
    private VendedorService vendedorService;

    private List<ClienteBean> clientes;
    private List<VendedorBean> vendedores;

    private Integer clienteIdSelecionado;
    private Integer vendedorIdSelecionado;
    DbFunctions db = new DbFunctions();

    @PostConstruct
    public void init() {
        try {
            Connection connection = db.connect_to_db("livrariadb", "postgres", "root"); 
            this.associacaoService = new AssociacaoService(connection);
            this.clienteService = new ClienteService(connection);
            this.vendedorService = new VendedorService(connection);

            this.clientes = clienteService.listarTodos();
            this.vendedores = vendedorService.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao conectar ao banco de dados", null));
        }
    }

    public void associar() {
        if (clienteIdSelecionado != null && vendedorIdSelecionado != null) {
            try {
                associacaoService.associarClienteAVendedor(clienteIdSelecionado, vendedorIdSelecionado);
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente associado ao vendedor com sucesso!", null));
            } catch (SQLException e) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao associar cliente ao vendedor", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Selecione um cliente e um vendedor", null));
        }
    }

    // Getters e Setters

    public List<ClienteBean> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteBean> clientes) {
        this.clientes = clientes;
    }

    public List<VendedorBean> getVendedores() {
        return vendedores;
    }

    public void setVendedores(List<VendedorBean> vendedores) {
        this.vendedores = vendedores;
    }

    public Integer getClienteIdSelecionado() {
        return clienteIdSelecionado;
    }

    public void setClienteIdSelecionado(Integer clienteIdSelecionado) {
        this.clienteIdSelecionado = clienteIdSelecionado;
    }

    public Integer getVendedorIdSelecionado() {
        return vendedorIdSelecionado;
    }

    public void setVendedorIdSelecionado(Integer vendedorIdSelecionado) {
        this.vendedorIdSelecionado = vendedorIdSelecionado;
    }
}
