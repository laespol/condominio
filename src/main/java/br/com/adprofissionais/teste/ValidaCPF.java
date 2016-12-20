package br.com.adprofissionais.teste;

import br.com.caelum.stella.boleto.Banco;
import br.com.caelum.stella.boleto.Beneficiario;
import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Endereco;
import br.com.caelum.stella.boleto.Pagador;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.format.Formatter;
import br.com.caelum.stella.format.NITFormatter;
import br.com.caelum.stella.inwords.FormatoDeInteiro;
import br.com.caelum.stella.inwords.FormatoDeReal;
import br.com.caelum.stella.inwords.InteiroSemFormato;
import br.com.caelum.stella.inwords.NumericToWordsConverter;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidaCPF {

	public static void main(String[] args) {

		// String cpf = "222.888.444-52";
		String cpf = "085.051.548-36";
		CPFValidator validator = new CPFValidator();
		try {
			// lógica de negócio ...
			validator.assertValid(cpf);
			System.out.println("CPF Valido");
			// continuação da lógica de negócio ...
		} catch (InvalidStateException e) { // exception lançada quando o
											// documento é inválido
			System.out.println(e.getInvalidMessages());

		}

		String cnpj = "26.713.379/0001-71";
		CNPJValidator cnpjvalidator = new CNPJValidator();
		try {
			// lógica de negócio ...
			cnpjvalidator.assertValid(cnpj);
			System.out.println("Cnpj Valido");
			// continuação da lógica de negócio ...
		} catch (InvalidStateException e) { // exception lançada quando o
											// documento é inválido
			System.out.println(e.getInvalidMessages());

		}

		Formatter formatter = new NITFormatter();
		String unfotmatedValue = "17033259504";
		String formatedValue = formatter.format(unfotmatedValue);

		System.out.println(formatedValue);

		String fotmatedValue = "170.33259.50-4";
		String unformatedValue = formatter.unformat(fotmatedValue);
		System.out.println(unformatedValue);

		Formatter cpfformatter = new CPFFormatter();
		String cpfvalue = "08505154835";

		System.out.println(cpfformatter.format(cpfvalue));

		NumericToWordsConverter converter;
		converter = new NumericToWordsConverter(new FormatoDeInteiro());
		double numero = 1000150.99;
		String extenso = converter.toWords(numero);
		System.out.println(extenso);

		converter = new NumericToWordsConverter(new InteiroSemFormato());
		numero = 1000150.99;
		extenso = converter.toWords(numero);
		System.out.println(extenso);

		converter = new NumericToWordsConverter(new FormatoDeReal());
		numero = 1000150.99;
		extenso = converter.toWords(numero);
		System.out.println(extenso);

		Datas datas = Datas.novasDatas().comDocumento(1, 5, 2008).comProcessamento(1, 5, 2008).comVencimento(2, 5,
				2008);

		Endereco enderecoBeneficiario = Endereco.novoEndereco().comLogradouro("Av das Empresas, 555")
				.comBairro("Bairro Grande").comCep("01234-555").comCidade("São Paulo").comUf("SP");

		// Quem emite o boleto
		Beneficiario beneficiario = Beneficiario.novoBeneficiario().comNomeBeneficiario("Fulano de Tal")
				.comAgencia("1824").comDigitoAgencia("4").comCodigoBeneficiario("76000")
				.comDigitoCodigoBeneficiario("5").comNumeroConvenio("1207113").comCarteira("18")
				.comEndereco(enderecoBeneficiario).comNossoNumero("9000206");

		Endereco enderecoPagador = Endereco.novoEndereco().comLogradouro("Av dos testes, 111 apto 333")
				.comBairro("Bairro Teste").comCep("01234-111").comCidade("São Paulo").comUf("SP");

		// Quem paga o boleto
		Pagador pagador = Pagador.novoPagador().comNome("Fulano da Silva").comDocumento("111.222.333-12")
				.comEndereco(enderecoPagador);

		Banco banco = new BancoDoBrasil();

		Boleto boleto = Boleto.novoBoleto().comBanco(banco).comDatas(datas).comBeneficiario(beneficiario)
				.comPagador(pagador).comValorBoleto("200.00").comNumeroDoDocumento("1234")
				.comInstrucoes("instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4", "instrucao 5")
				.comLocaisDePagamento("local 1", "local 2");

		GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);

		// Para gerar um boleto em PDF
		gerador.geraPDF("BancoDoBrasil.pdf");

		// Para gerar um boleto em PNG
		gerador.geraPNG("BancoDoBrasil.png");

		// Para gerar um array de bytes a partir de um PDF
		byte[] bPDF = gerador.geraPDF();

		// Para gerar um array de bytes a partir de um PNG
		byte[] bPNG = gerador.geraPNG();

	}
}
