package br.com.projeto.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;


@ManagedBean
@RequestScoped
public class CustomizedDocumentsView implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private List<LivroBean> pedido;
	
	
	private ExcelOptions excelOpt;
	
	private PDFOptions pdfOpt;
	
	@PostConstruct
	public void init() {
		pedido = getPedido();
		customizationOptions();
	}
	       /* ------- Getters e Setters */
	
	public List<LivroBean> getPedido() {
        return pedido;
    }

    public ExcelOptions getExcelOpt() {
        return excelOpt;
    }

    public PDFOptions getPdfOpt() {
        return pdfOpt;
    }



                /* -------  Métodos ------- */

public void customizationOptions() {
	excelOpt = new ExcelOptions();
	excelOpt.setFacetBgColor("F88017");
	excelOpt.setFacetFontSize("10");
    excelOpt.setFacetFontColor("#0000ff");
    excelOpt.setFacetFontStyle("BOLD");
    excelOpt.setCellFontColor("#145c14");
    excelOpt.setCellFontSize("8");
    excelOpt.setFontName("Verdana");


   pdfOpt = new PDFOptions();
   pdfOpt.setFacetBgColor("#F88017");
   pdfOpt.setFacetFontColor("#0000ff");
   pdfOpt.setFacetFontStyle("BOLD");
   pdfOpt.setCellFontSize("12");
   pdfOpt.setFontName("Courier");
   pdfOpt.setOrientation(PDFOrientationType.LANDSCAPE);
}

public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
    Document pdf = (Document) document;
    pdf.open();
    pdf.setPageSize(PageSize.A4);

    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    String logo = externalContext.getRealPath("") + "/resources/images/logo.png";

    pdf.add(Image.getInstance(logo));
}
}


