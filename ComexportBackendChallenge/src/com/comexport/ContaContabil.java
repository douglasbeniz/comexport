package com.comexport;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "conta")

public class ContaContabil implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private long contaContabil;
	private long data;
	private double valor;

	/*
	 * Construtor padrao
	 */
	public ContaContabil() {}

	/*
	 * Construtor por copia
	 */
	public ContaContabil (String uniqueId, long conta, long data, double valor) {
		this.setId(uniqueId);
		this.setContaContabil(conta);
		this.setData(data);
		this.setValor(valor);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getContaContabil() {
		return contaContabil;
	}

	@XmlElement
	public void setContaContabil(long conta) {
		this.contaContabil = conta;
	}

	public long getData() {
		return data;
	}

	@XmlElement
	public void setData(long data) {
		this.data = data;
	}

	public double getValor() {
		return valor;
	}

	@XmlElement
	public void setValor(double valor) {
		this.valor = valor;
	}
}
