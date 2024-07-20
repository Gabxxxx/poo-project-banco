package persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;

public class PersistenciaEmArquivo implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private List<Cliente> cadastroClientes = new ArrayList<>();
	private static PersistenciaEmArquivo instance;
	
	private PersistenciaEmArquivo() {
		carregarDadosDoArquivo();
	}
	
	public static PersistenciaEmArquivo getInstance() {
		if (instance != null)
			return instance;
		else
			return new PersistenciaEmArquivo();
	}
	
	public void salvarCliente(Cliente c) {
		if (!cadastroClientes.contains(c)) {
			cadastroClientes.add(c);
			salvarDadosEmArquivo();
			System.out.println("O cliente foi cadastrado com sucesso.");
		} else {
			System.err.println("Esse cliente já está cadastrado no sistema.");
		}

	}
	
	public void removerCliente (String cpf) {
		Cliente cli = localizarCpfCliente(cpf);
		
		if (cli != null) {
			cadastroClientes.remove(cli);
			salvarDadosEmArquivo();
			System.out.println("O cliente foi removido com sucesso.");
		} else {
			System.err.println("O cliente não foi encontrado.");
		}
	}
	
	public void listarClientes() {
		if(cadastroClientes == null) {
			System.err.println("Não existe clientes cadastrados.");
		} else {
			System.out.println("\n" + cadastroClientes + "\n");
		}
	}
	
	public void atualizarClienteCadastro(Cliente c) {
		if (cadastroClientes.contains(c)) {
			int index = cadastroClientes.indexOf(c);
			cadastroClientes.set(index, c);
			salvarDadosEmArquivo();
		} else {
			System.err.println("Esse cliente não foi encontrado.");
		}
	}
	
	public void salvarDadosEmArquivo() {
		File file = new File("dados");
		
		if (file.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream("dados");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(cadastroClientes);
				oos.close();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void carregarDadosDoArquivo() {
		try {
			FileInputStream fis = new FileInputStream("dados");
			ObjectInputStream ois = new ObjectInputStream(fis);
			cadastroClientes = (List<Cliente>)ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Cliente localizarCpfCliente(String cpf) {
	    for (Cliente c : cadastroClientes) {
	        if (c.getCpf().equals(cpf)) {
	            return c;
	        }
	    }
	    return null;
	}
}














