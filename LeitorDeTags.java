package Trabalho1;

import java.io.File;
import java.io.IOException;

public class LeitorDeTags {

	private PilhaLista<String> pilhaTags = new PilhaLista<String>();
	private String stringErros = "";
	private ImportadorDados id = new ImportadorDados();
	private ListaEncadeada<TagEFrequencia> listaFrequenciaTags = new ListaEncadeada<>();
	private ListaEncadeada<String> listaSingletonTags = new ListaEncadeada<>();

	public ListaEncadeada<TagEFrequencia> getListaFrequenciaTags() {
		return listaFrequenciaTags;
	}

	public String getStringErros() {
		return stringErros;
	}

	public void setStringErros(String stringErros) {
		this.stringErros = stringErros;
	}

	public void inserirTagLista(String tag) {
		boolean achouTag = false;
		for (int i = 0; i < listaFrequenciaTags.obterComprimento(); i++) {
			if (listaFrequenciaTags.obterNo(i).getInfo().getNome().equals(tag)) {
				TagEFrequencia t = new TagEFrequencia((listaFrequenciaTags.obterNo(i).getInfo().getFrequencia() + 1),
						tag);
				listaFrequenciaTags.retirar(listaFrequenciaTags.obterNo(i).getInfo());
				listaFrequenciaTags.inserir(t);
				achouTag = true;
			}
		}
		if (achouTag == false) {
			listaFrequenciaTags.inserir(new TagEFrequencia(1, tag));
		}
	}

	public void insereSilgletonTags() {
		listaSingletonTags.inserir("meta");
		listaSingletonTags.inserir("base");
		listaSingletonTags.inserir("br");
		listaSingletonTags.inserir("col");
		listaSingletonTags.inserir("command");
		listaSingletonTags.inserir("embed");
		listaSingletonTags.inserir("hr");
		listaSingletonTags.inserir("img");
		listaSingletonTags.inserir("input");
		listaSingletonTags.inserir("link");
		listaSingletonTags.inserir("param");
		listaSingletonTags.inserir("source");
		listaSingletonTags.inserir("!DOCTYPE");
	}

	public boolean verificaExistenciaTagSingleton(String tag) {
		for (int i = 0; i < listaSingletonTags.obterComprimento(); i++) {
			if (listaSingletonTags.obterNo(i).getInfo().equals(tag)) {
				return true;
			}
		}
		return false;
	}

	public String retornaTagFim(int abre, int fecha, String texto) {
		String tag = "";
		for (int i = abre + 2; i < fecha; i++) {
			tag += texto.charAt(i);
		}
		String[] vetor = tag.split(" ");
		return vetor[0];
	}

	public String retornaTagInicio(int abre, int fecha, String texto) {
		String tag = "";
		for (int i = abre + 1; i < fecha; i++) {
			tag += texto.charAt(i);
		}
		String[] vetor = tag.split(" ");
		return vetor[0];
	}

	public void registraErro(String tag) {
		if (stringErros == "") {
			stringErros += "Se esperava o fechamento de uma tag " + pilhaTags.peek()
					+ ", mas foi encontrado o fechamento como " + tag + ".";
		} else {
			stringErros += "\nSe esperava o fechamento de uma tag " + pilhaTags.peek()
					+ ", mas foi encontrado o fechamento como " + tag + ".";
		}
	}

	public void registraErroFinal() {
		try {
			while (true) {
				if (stringErros == "") {
					stringErros += "\nUma tag " + pilhaTags.pop() + " foi aberta, mas não foi fechada.";
				} else {
					stringErros += "\nUma tag " + pilhaTags.pop() + " foi aberta, mas não foi fechada.";
				}
			}
		} catch (PilhaVaziaException e) {
		}
	}

	public void verificaExistenciaParTag(String tag) {
		try {
			if (pilhaTags.peek().equals(tag)) {
				pilhaTags.pop();
				inserirTagLista(tag);
			} else {
				registraErro(tag);
			}
		} catch (PilhaVaziaException e) {
		}
	}

	public void leitorEInterpretadorDeTags(String caminho) throws IOException {
		File f = new File(caminho);
		String str = id.importarDados(f);
		insereSilgletonTags();
		int inicioTag = -1;
		int finalTag = -1;
		boolean achouTagFechamento = false;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '<') {
				if (str.charAt(i + 1) == '/') {
					inicioTag = i;
					achouTagFechamento = true;
				}
				inicioTag = i;
			}

			if (str.charAt(i) == '>') {
				finalTag = i;
				if (inicioTag != -1) {
					String tag = "";
					if (achouTagFechamento == true) {
						tag = retornaTagFim(inicioTag, finalTag, str);
						if (verificaExistenciaTagSingleton(tag)) {
							achouTagFechamento = false;
						} else {
							verificaExistenciaParTag(tag);
							achouTagFechamento = false;
						}
					} else {
						tag = retornaTagInicio(inicioTag, finalTag, str);
						if (verificaExistenciaTagSingleton(tag)) {
							inserirTagLista(tag);
						} else {
							pilhaTags.push(tag);
						}
					}
				}
			}
		}
		registraErroFinal();
	}
}
