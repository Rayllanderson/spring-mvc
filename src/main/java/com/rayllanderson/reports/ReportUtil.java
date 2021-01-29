package com.rayllanderson.reports;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * retorna o PDF para download no navegador
     * @throws JRException 
     */
    public byte[] generateReport(List <?> data, String reportName, ServletContext context) throws JRException {
	//cria a lista de dados para o relatório com nossa lista de objetos
	JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(data);
	
	//carregar o caminho do arquivo jasper compilado
	String path = context.getRealPath("reports") + File.separator + reportName + ".jasper";
	
	//gera o arquivo jasper passando os dados
	JasperPrint jasperPrint = JasperFillManager.fillReport(path, new HashedMap<>(), jrBean);
	
	//exporta para array de bytes para realizar o download do pdf
	return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
