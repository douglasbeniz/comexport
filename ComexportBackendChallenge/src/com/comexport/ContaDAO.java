package com.comexport;

import java.util.UUID;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {
	private static final String ArquivoContas = "ContaContabil.dat";

	public String adicionaConta(long conta, long data, double valor) {
		// Lista de objetos do tipo 'ContaContabil'
		List<ContaContabil> contasList = null;

		// Gera um ID unico
		String uniqueId = UUID.randomUUID().toString();

		try {
			File aquivo = new File(ArquivoContas);

            // Instancia de um objeto do tipo 'ContaContabil'
			ContaContabil contaObj = new ContaContabil(uniqueId, conta, data, valor);

			if (!aquivo.exists()) {
				// Arquivo nao existe ainda, cria um em branco e adiciona o primeiro registro
				contasList = new ArrayList<ContaContabil>();
				contasList.add(contaObj);
			} else {
				// Arquivo jah existe, le os registros, guarda numa lista, e adiciona o novo registro
				FileInputStream fis = new FileInputStream(aquivo);
				ObjectInputStream ois = new ObjectInputStream(fis);
				contasList = (List<ContaContabil>) ois.readObject();
				ois.close();
				// Adiciona o registro
				contasList.add(contaObj);
			}
			// Atualiza/Cria o arquivo de dados
			salvaListaContas(contasList);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return uniqueId;
	}

	public ContaContabil getContaPorId(String contaId) {
		List<ContaContabil> contasList = this.getTodasContas();
		// Encontra a conta desejada
		for (ContaContabil conta: contasList) {
			if (conta.getId().equals(contaId)) {
				return conta;
			}
		}
		return null;
	}

	public List<ContaContabil> getValoresPorConta(long contaContabil) {
		List<ContaContabil> contasList = this.getTodasContas();
		List<ContaContabil> contasListResult = new ArrayList<ContaContabil>();

		// Encontra a conta desejada
		for (ContaContabil conta: contasList) {
			if (conta.getContaContabil() == contaContabil) {
				contasListResult.add(conta);
			}
		}

		return contasListResult;
	}

	public List<ContaContabil> getTodasContas() {
		List<ContaContabil> contasList = null;
		try {
			File aquivo = new File(ArquivoContas);
			if (!aquivo.exists()) {
				// Retornara uma lista vazia
				contasList = new ArrayList<ContaContabil>();
			} else {
				FileInputStream fis = new FileInputStream(aquivo);
				ObjectInputStream ois = new ObjectInputStream(fis);
				contasList = (List<ContaContabil>) ois.readObject();
				ois.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return contasList;
	}

	private void salvaListaContas(List<ContaContabil> contasList) {
		try {
			File aquivo = new File(ArquivoContas);
			FileOutputStream fos;
			fos = new FileOutputStream(aquivo);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(contasList);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public StatsContabil getTodasStats() {
		List<ContaContabil> contasList = this.getTodasContas();
		StatsContabil statsResult = new StatsContabil();

		// Encontra a conta desejada
		for (ContaContabil conta: contasList) {
			statsResult.atualiza(conta.getValor());
		}

		return statsResult;
	}

	public StatsContabil getStatsPorConta(long contaContabil) {
		List<ContaContabil> contasList = this.getTodasContas();
		StatsContabil statsResult = new StatsContabil();

		// Encontra a conta desejada
		for (ContaContabil conta: contasList) {
			if (conta.getContaContabil() == contaContabil) {
				statsResult.atualiza(conta.getValor());
			}
		}

		return statsResult;
	}
}
