package br.com.projeto.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;

@ManagedBean
@SessionScoped
public class PdfProcess {
	
	
	    private PDFOptions pdfOpt;

	    
	    @PostConstruct
	    public void init() {
	        customizePDF();
	    }

	    private void customizePDF() {
	        PDFOptions pdfOpt = new PDFOptions();
	        pdfOpt.setFacetBgColor("#F88017");
	        pdfOpt.setFacetFontColor("#0000ff");
	        pdfOpt.setFacetFontStyle("BOLD");
	        pdfOpt.setCellFontSize("12");
	        pdfOpt.setFontName("Courier");
	        pdfOpt.setOrientation(PDFOrientationType.LANDSCAPE);
	    }

	    public PDFOptions getPdfOpt() {
	        return getPdfOpt();
	        

	    
	}
	    
	  public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		  
		  Document pdf = (Document) document;
		  pdf.open();
		  pdf.setPageSize(PageSize.A4);
		  
	  }


}
