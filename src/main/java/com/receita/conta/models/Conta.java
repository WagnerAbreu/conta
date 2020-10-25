package com.receita.conta.models;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;

public class Conta implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CsvBindByName
	private String agencia;
	
	@CsvBindByName
	private String conta;

	@CsvBindByName
	private String saldo;
	
	@CsvBindByName
	private String status;
	
	private boolean aprovada;
	
	public Conta() {}
	
	public Conta(String agencia, String conta, String saldo, String status) {
		this.agencia = agencia;
		this.conta = conta;
		this.saldo = saldo;
		this.status = status;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAprovada() {
		return aprovada;
	}

	public void setStatusReceita(boolean aprovada) {
		this.aprovada = aprovada;
	}
	
}
