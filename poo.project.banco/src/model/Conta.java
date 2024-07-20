package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import enumerator.TipoTransacao;

public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer numero;
	private BigDecimal saldo;
	private boolean status;
	private LocalDateTime dataAbertura;
	
	private List<RegistroTransacao> transacoes;

	public Conta () {
		this.numero = new Random().nextInt(99999999);
		this.saldo = BigDecimal.ZERO;
		saldo.setScale(4, RoundingMode.HALF_UP);
		this.status = true;
		this.dataAbertura = LocalDateTime.now();
		transacoes = new ArrayList<>();
	}
	
	public void sacar (BigDecimal quantia) {
		if (quantia.compareTo(this.saldo) <= 0) {
			this.saldo = this.saldo.subtract(quantia);
			transacoes.add(new RegistroTransacao(quantia, TipoTransacao.DEBITO, LocalDateTime.now()));
			System.out.println("Saque bem sucedido. ");
		} else {
			System.err.println("Saldo insuficiente.");
		}
	}
	
	public void depositar (BigDecimal quantia) {
		if (quantia.compareTo(new BigDecimal("0")) > 0) {
			this.saldo = this.saldo.add(quantia);
			transacoes.add(new RegistroTransacao(quantia, TipoTransacao.CREDITO, LocalDateTime.now()));
			System.out.println("Deposito bem sucedido. ");
		} else {
			System.err.println("Operação inválida.");
		}
	}

	public void transferir (BigDecimal quantia, Conta c) {
		if (quantia.compareTo(new BigDecimal("0")) < 0) {
			System.err.println("Valor inválido.");
		} else if (quantia.compareTo(this.saldo) <= 0) {
			this.saldo = this.saldo.subtract(quantia);
			c.setSaldo(c.getSaldo().add(quantia));
			c.transacoes.add(new RegistroTransacao(quantia, TipoTransacao.TRANSACAO_CREDITO, LocalDateTime.now()));
			transacoes.add(new RegistroTransacao(quantia, TipoTransacao.TRANSACAO_DEBITO, LocalDateTime.now()));
		}
	}
	
	public void emitirExtrato (Month mes, int ano) {
		List<RegistroTransacao> extrato = new ArrayList<>();

		for (RegistroTransacao transacao : transacoes) {
			LocalDateTime dataTransacao = transacao.getData();
			if (dataTransacao.getMonth() == mes && dataTransacao.getYear() == ano) {
				extrato.add(transacao);
			}
		}
		
		System.out.println(extrato.toString());
	}
	
	@Override
	public String toString() {
		return "Conta [numero:" + numero + " | saldo:" + saldo + " | status:" + status + " | dataAbertura:" + dataAbertura
				+ "] \n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(numero, other.numero);
	}

	
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public void setTransacoes(List<RegistroTransacao> transacoes) {
		this.transacoes = transacoes;
	}
	
	public List<RegistroTransacao> getTransacoes() {
		return transacoes;
	}
}







