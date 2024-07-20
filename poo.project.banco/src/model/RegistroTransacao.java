package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

import enumerator.TipoTransacao;

public class RegistroTransacao implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private BigDecimal valor;
	private TipoTransacao tipo;
	private LocalDateTime data;
	
	public RegistroTransacao (BigDecimal valor, TipoTransacao tipo, LocalDateTime data) {
		this.id = new Random().nextInt(99999999);
		this.valor = valor;
		this.tipo = tipo;
		this.data = data;
	}

	@Override
	public String toString() {
		return "RegistroTransacao [Id=" + id + " | Valor=" + valor + " | Tipo=" + tipo + " | Data=" + data + "] \br";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(data, id, tipo, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroTransacao other = (RegistroTransacao) obj;
		return Objects.equals(data, other.data) && Objects.equals(id, other.id) && tipo == other.tipo
				&& Objects.equals(valor, other.valor);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoTransacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransacao tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
}













