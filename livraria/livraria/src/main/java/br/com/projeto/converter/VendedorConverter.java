package br.com.projeto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.com.projeto.bean.VendedorBean;
import br.com.projeto.bean.AcoesBean;

@FacesConverter(value = "vendedorConverter")
public class VendedorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            int id = Integer.parseInt(value);

            // Obtém a lista de vendedores do AcoesBean
            AcoesBean acoesBean = context.getApplication()
                                         .evaluateExpressionGet(context, "#{acoesBean}", AcoesBean.class);

            return acoesBean.getListaVendedores()
                            .stream()
                            .filter(vendedor -> vendedor.getId() == id)
                            .findFirst()
                            .orElse(null);

        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter Vendedor: valor inválido " + value);
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof VendedorBean) {
            return String.valueOf(((VendedorBean) obj).getId());
        } else {
            System.err.println("Erro: objeto não é um VendedorBean. Tipo recebido: " + obj.getClass().getName());
            return "";
        }
    }
}
