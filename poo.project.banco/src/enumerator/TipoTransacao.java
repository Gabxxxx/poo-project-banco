package enumerator;

import java.io.Serializable;

public enum TipoTransacao implements Serializable {

	CREDITO(1),
	DEBITO(2),
	TRANSACAO_DEBITO(3),
	TRANSACAO_CREDITO(4);
	
	private final int valor;
	
	private TipoTransacao(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static TipoTransacao transacaoDeValor(int valor) {
		for (TipoTransacao t : values()) {
			if(t.getValor() == valor)
				return t;
		}
		return null;
	}
}
