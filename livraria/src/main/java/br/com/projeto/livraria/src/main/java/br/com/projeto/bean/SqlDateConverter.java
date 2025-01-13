package br.com.projeto.bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "sqlDateConverter", forClass = java.sql.Date.class)
public class SqlDateConverter implements Converter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            // Converte String para LocalDate
            LocalDate localDate = LocalDate.parse(value, formatter);
            // Converte LocalDate para java.sql.Date
            return java.sql.Date.valueOf(localDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Erro de conversão ao definir o valor '" + value + "' para java.sql.Date.", e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof java.sql.Date) {
            // Converte java.sql.Date para LocalDate
            LocalDate localDate = ((java.sql.Date) value).toLocalDate();
            // Formata LocalDate para String
            return localDate.format(formatter);
        }
        return "";
    }
}
