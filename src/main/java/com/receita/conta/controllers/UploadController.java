package com.receita.conta.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.receita.conta.models.Conta;
import com.receita.conta.models.ContaResponse;
import com.receita.conta.services.ReceitaService;

@Controller
public class UploadController {

	@GetMapping("/")
    public String index() {
        return "index";
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/upload")
    public void uploadCSVFile(@RequestParam("file") MultipartFile file,
    		HttpServletResponse response) {
		
        if (!file.isEmpty()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                CsvToBean<Conta> csvToBean = new CsvToBeanBuilder(reader)
                		.withSeparator(';')
                        .withType(Conta.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List<Conta> contas = csvToBean.parse();

                List<ContaResponse> res = new ArrayList<>();
                if(!contas.isEmpty()) {
                	ReceitaService receitaService = new ReceitaService();
                	for (Conta conta : contas) {
                		ContaResponse cr = new ContaResponse();
                		cr.setAgencia(conta.getAgencia());
                		cr.setConta(conta.getConta());
                		cr.setSaldo(conta.getSaldo());
                		cr.setStatus(conta.getStatus());
                		cr.setStatusReceita(receitaService.atualizarConta(conta.getAgencia(), conta.getConta(), 
                				Double.parseDouble(conta.getSaldo().replace(",", ".")), conta.getStatus()));
                		res.add(cr);
					}
                }
                
                exportCSV(response, res);
            } catch (Exception ex) {
            }
        }
    }
	
	public void exportCSV(HttpServletResponse response, List<ContaResponse> contas) throws Exception {
        String filename = "contas.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<ContaResponse> writer = new StatefulBeanToCsvBuilder<ContaResponse>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(';')
                .withOrderedResults(false)
                .build();

        writer.write(contas);
    }
}
