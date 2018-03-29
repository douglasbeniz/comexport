/*
 *  ComexportBackendChallenge project - March of 2018
 * 
 *  author:   Douglas Bezerra Beniz (douglasbeniz@gmail.com)
 *  creation: 29-Mar-2018
 */
package com.comexport;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "stats")

public class StatsContabil implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private int lanctos = 0;
	private double soma = 0;
	private double min = 0;
	private double max = 0;
	private double media = 0;

	/*
	 * Construtor padrao
	 */
	public StatsContabil() {}

	/*
	 * Identity
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * Numero de lancamentos
	 */
	public void setLanctos(int lanctos) {
		this.lanctos = lanctos;
	}

	public int getLanctos() {
		return lanctos;
	}

	/*
	 * Soma
	 */
	public double getSoma() {
		return soma;
	}

	@XmlElement
	public void setSoma(double soma) {
		this.soma = soma;
	}

	/*
	 * Minimo
	 */
	public double getMin() {
		return min;
	}

	@XmlElement
	public void setMin(double min) {
		this.min = min;
	}

	/*
	 * Maximo
	 */
	public double getMax() {
		return max;
	}

	@XmlElement
	public void setMax(double max) {
		this.max = max;
	}

	/*
	 * Media
	 */
	public double getMedia() {
		return media;
	}

	@XmlElement
	public void setMedia(double media) {
		this.media = media;
	}

	public void incrementLanctos() {
		this.lanctos += 1;
	}

	private double formataCasaDecimal(double valor) {
		Locale currentLocale = Locale.getDefault();
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
		otherSymbols.setDecimalSeparator('.');
	    DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
	    df.setRoundingMode(RoundingMode.FLOOR);

	    double result = new Double(df.format(valor));
	    
	    return result;
	}

	public void atualizaSoma(double novoValor) {
		// Formatacao...
		double result = this.formataCasaDecimal(this.getSoma() + novoValor);
	    // Atualiza o valor da soma
		this.setSoma(result);
	}

	public void atualizaMin(double novoValor) {
		if ((this.getLanctos() == 0) || (this.getMin() > novoValor)) {
			this.setMin(novoValor);
		}
	}

	public void atualizaMax(double novoValor) {
		if ((this.getLanctos() == 0) || (this.getMax() < novoValor)) {
			this.setMax(novoValor);
		}
	}
	
	public void atualizaMedia() {
		// Formatacao...
		double result = this.formataCasaDecimal(this.getSoma() / this.getLanctos());
	    // Atualiza o valor da media
		this.setMedia(result);
	}

	public void atualiza(double novoValor) {
		this.atualizaMin(novoValor);
		this.atualizaMax(novoValor);
		this.atualizaSoma(novoValor);
		this.incrementLanctos();
		this.atualizaMedia();
	}
}
