package application;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Month;
import java.util.Scanner;

import model.Cliente;
import model.Conta;
import persistencia.PersistenciaEmArquivo;

public class Programa implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.println("Olá, qual operação gostaria de realizar? \n");
			System.out.println("1. Cadastre um novo cliente.");
			System.out.println("2. Listar todos os clientes cadastrados");
			System.out.println("3. Já é cliente? Recupere todas as suas informações.");
			System.out.println("4. Sair.");

			int alternativas = scanner.nextInt();
			scanner.nextLine();

			switch (alternativas) {
			case 1:

				System.out.println("Digite o seu nome: ");
				String nome = scanner.nextLine();
				System.out.println("Digite o seu CPF:");
				String cpf = scanner.nextLine();

				Cliente cli = new Cliente(cpf, nome);
				PersistenciaEmArquivo.getInstance().salvarCliente(cli);
				break;

			case 2:

				PersistenciaEmArquivo.getInstance().listarClientes();

				break;

			case 3:

				System.out.println("Digite o seu CPF:");
				cpf = scanner.next();

				cli = PersistenciaEmArquivo.getInstance().localizarCpfCliente(cpf);

				if (cli == null) {
					System.err.println("CLiente inexistente.");
					break;
				}

				System.out.println("Cliente escolhido: " + cli.getNome() + "\n");
				System.out.println("O que você deseja?");
				System.out.println("1. Criar outra conta.");
				System.out.println("2. Listar todas as contas.");
				System.out.println("3. Fazer deposito.");
				System.out.println("4. Fazer Saque.");
				System.out.println("5. Fazer transferência.");
				System.out.println("6. Fazer transferência entre clientes.");
				System.out.println("7. Remover cliente.");
				System.out.println("8. Remover conta de um cliente.");
				System.out.println("9. Imprimir extrato da conta.");
				System.out.println("10. Consultar saldo.");
				System.out.println("11. Balanço entre contas.");
				System.out.println("12. Sair.");

				alternativas = scanner.nextInt();
				scanner.nextLine();

				switch (alternativas) {

				case 1:

					Conta c = new Conta();
					cli.adicionarConta(c);

					PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cli);

					break;

				case 2:

					if (cli.getContas().size() == 0) {
						System.err.println("Esse cliente não possui nenhuma conta.");
					} else {
						for (Conta conta : cli.getContas()) {
							System.out.println(conta);
						}
					}
					break;

				case 3:

					for (Conta conta : cli.getContas()) {
						System.out.println(conta);
					}

					System.out.println("Deseja realizar deposito em qual conta?");
					int opcaoContaParaDeposito = 0;
					opcaoContaParaDeposito = scanner.nextInt();
					scanner.nextLine();

					System.out.println("Digite a quantia que deseja deposiar: R$");
					double valorDeposito = 0.0;
					valorDeposito = scanner.nextDouble();

					Conta localizarContaDeposito = cli.localizarContaNumero(opcaoContaParaDeposito);

					if (localizarContaDeposito != null) {
						localizarContaDeposito.depositar(new BigDecimal(valorDeposito));
						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cli);
					}
					break;

				case 4:

					if (cli.getContas().size() == 0) {
						System.err.println("Esse cliente não possui nenhuma conta.");
					} else {
						for (Conta conta : cli.getContas()) {
							System.out.println(conta);
						}
					}

					System.out.println("Deseja realizar saque de qual conta?");
					int opcaoContaParaSaque = 0;
					opcaoContaParaSaque = scanner.nextInt();
					scanner.nextLine();

					System.out.println("Digite o valor do saque: R$");
					double valorSaque = 0.0;
					valorSaque = scanner.nextDouble();

					Conta localizarContaSaque = cli.localizarContaNumero(opcaoContaParaSaque);

					if (localizarContaSaque != null) {
						localizarContaSaque.sacar(new BigDecimal(valorSaque));
						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cli);
					}
					break;

				case 5:

					if (cli.getContas().size() == 0) {
						System.err.println("Esse cliente não possui nenhuma conta.");
						return;
					} else {
						for (Conta conta : cli.getContas()) {
							System.out.println(conta);
						}
					}

					System.out.println("Qual a conta de origem?");
					int opcaoContaOrigemTranferencia = scanner.nextInt();
					scanner.nextLine();

					System.out.println("Qual a conta de destino?");
					int opcaoContaDestinoTranferencia = scanner.nextInt();
					scanner.nextLine();

					System.out.println("Digite o valor da transferência: R$");
					double valorTransferencia = scanner.nextDouble();

					Conta localizarContaOrigemTransferencia = cli.localizarContaNumero(opcaoContaOrigemTranferencia);
					Conta localizarContaDestinoTransferencia = cli.localizarContaNumero(opcaoContaDestinoTranferencia);

					if (localizarContaOrigemTransferencia != null && localizarContaDestinoTransferencia != null) {

						localizarContaOrigemTransferencia.transferir(new BigDecimal(valorTransferencia),
								localizarContaDestinoTransferencia);
						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cli);
						System.out.println("Transferência realizada com sucesso!");
					}
					break;

				case 6:

					System.out.println("Insira o cpf do cliente de origem: ");
					String cliCpfOrigem = scanner.next();
					Cliente cliOrigem = PersistenciaEmArquivo.getInstance().localizarCpfCliente(cliCpfOrigem);

					if (cliOrigem == null) {
						System.err.println("Cliente de origem não existe.");
						return;
					} else {
						for (Conta conta : cliOrigem.getContas()) {
							System.out.println(conta);
						}
					}

					System.out.println("Insira o número da conta de origem: ");
					int numContaOrigem = scanner.nextInt();

					Conta contaOrigem = cliOrigem.localizarContaNumero(numContaOrigem);

					if (contaOrigem == null) {
						System.err.println("Conta não existe.");
						return;
					}

					System.out.println("Insira o cpf do cliente de destino");
					String cliCPfDestino = scanner.next();
					Cliente cliDestino = PersistenciaEmArquivo.getInstance().localizarCpfCliente(cliCPfDestino);

					if (cliCPfDestino == null) {
						System.err.println("Cliente de destino não existe");
						return;
					} else {
						for (Conta conta : cliDestino.getContas()) {
							System.out.println(conta);
						}

					}

					System.out.println("Insira o número da conta de destino: ");
					int numContaDestino = scanner.nextInt();
					Conta contaDestino = cliDestino.localizarContaNumero(numContaDestino);

					if (contaDestino == null) {
						System.err.println("Conta de destino não existe.");
						return;
					}

					System.out.println("Insira um valor a ser transferido: R$");
					double valorDeTransferencia = scanner.nextDouble();

					contaOrigem.transferir(new BigDecimal(valorDeTransferencia), contaDestino);

					PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliOrigem);
					PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliDestino);

					System.out.println("Transferência entre clientes bem sucedida.");

					break;

				case 7:

					System.out.println("Insira o cpf do cliente que deseja remover: ");
					String cliCpfRemover = scanner.next();
					PersistenciaEmArquivo.getInstance().removerCliente(cliCpfRemover);

					break;

				case 8:

					if (cli.getContas() == null) {
						System.err.println("Cliente não encontrado.");
						return;
					} else {
						for (Conta conta : cli.getContas()) {
							System.out.println(conta);
						}
					}

					System.out.println("Insira o número da conta que deseja remover");
					int numConta = scanner.nextInt();
					Conta contaRemocao = cli.localizarContaNumero(numConta);

					if (contaRemocao != null) {
						cli.removerConta(contaRemocao);
						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cli);
					}

					break;

				case 9:

					if (cli.getContas() == null) {
						System.err.println("Conta(s) não encontrada");
						return;
					} else {
						for (Conta conta : cli.getContas())
							System.out.println(conta);
					}

					System.out.println("Insira o número do mês que deseja filtrar no extrato: ");
					int numMesExtrato = scanner.nextInt();
					Month mesExtrato = Month.of(numMesExtrato);

					System.out.println("Insira o número do ano: ");
					int numAnoExtrato = scanner.nextInt();
					scanner.nextLine();

					for (Conta conta : cli.getContas()) {
						conta.emitirExtrato(mesExtrato, numAnoExtrato);
					}

					break;

				case 10:

					for (Conta conta : cli.getContas()) {
						System.out.println("Cliente: " + cli.getNome() + " | Saldo: R$ " + conta.getSaldo());
					}

					break;

				case 11:

					BigDecimal balancoConta = cli.balancoEntreConta();

					if (balancoConta != null) {
						System.out.println(balancoConta);
					}

					break;

				case 12:

					System.out.println("Saindo...");
					System.exit(alternativas);
					scanner.close();

					break;

				default:
					System.out.println("Opção inválida");
					break;
				}
				break;
			case 4:
				System.out.println("Saindo...");
				System.exit(alternativas);
				scanner.close();

			}
		}
	}
}
