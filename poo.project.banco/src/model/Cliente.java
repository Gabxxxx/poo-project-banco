package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import persistencia.PersistenciaEmArquivo;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cpf;
	private String nome;
	
	private List<Conta> contas;
	
	
	public Cliente (String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		this.contas = new ArrayList<>();
	}
	
	public void adicionarConta(Conta c) {
		if (contas.contains(c)) {
			System.out.println("A conta já está ligada a esse cliente.");
		} else {
			this.contas.add(c);
			PersistenciaEmArquivo.getInstance().salvarDadosEmArquivo();
			System.out.println("\n Conta criada com sucesso. \n");
		}
	}
	
	public void removerConta(Conta c) {

    	if (contas.contains(c)) {
    		this.contas.remove(c);
    		PersistenciaEmArquivo.getInstance().salvarDadosEmArquivo();
    		System.out.print("Conta removida com sucesso. \n");
    	} else {
    		System.err.print("A conta não esta associada a este cliente.");
    	}
    }
	
	 public Conta localizarContaNumero(int numero) {

			for (int i = 0; i < contas.size(); i++) {
				Conta c = contas.get(i);

				if (c.getNumero() == numero) {
					return c;
				}
			} System.out.print("Conta nao encontrada."); 
			  return null;
	    }
	 
	 public BigDecimal balancoEntreConta() {
		 BigDecimal contasSomadas = BigDecimal.ZERO;
		 for(Conta conta : contas) {
				contasSomadas = contasSomadas.add(conta.getSaldo());
			}
			System.out.println("Esse é o resultado das contas somadas: " + contasSomadas);
			return null;
	 } 

	public String toString() {
		return "Cliente [Cpf: " + cpf + " | Nome: " + nome + " | Contas: " + contas + "] \n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}
	
	
}















