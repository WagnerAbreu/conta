package com.receita.conta.models;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;

public class ContaResponse extends Conta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@CsvBindByName
	private boolean statusReceita;

	public boolean isStatusReceita() {
		return statusReceita;
	}

	public void setStatusReceita(boolean statusReceita) {
		this.statusReceita = statusReceita;
	}

}
