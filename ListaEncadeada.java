package Trabalho1;

public class ListaEncadeada<T> {

	private NoLista<T> primeiro = new NoLista<>();

	public ListaEncadeada() {
		primeiro = null;
	}

	public NoLista<T> getPrimeiro() {
		return this.primeiro;
	}

	// No pior caso vai ser constante, nao tem laço de repetição(ordem de
	// complexidade)
	// Com um laco é linear
	public void inserir(T info) {
		NoLista<T> novo = new NoLista<>();
		novo.setInfo(info);
		novo.setProximo(primeiro);
		this.primeiro = novo;
	}

	public void exibir() {
		NoLista<T> p = this.primeiro;
		while (p != null) {
			System.out.println(p.getInfo());
			p = p.getProximo();
		}
	}

	public boolean estaVazia() {
		if (this.primeiro == null) {
			return true;
		} else {
			return false;
		}
	}

	public NoLista<T> buscar(T info) {
		NoLista<T> p = this.primeiro;
		while (p != null) {
			if (p.getInfo().equals(info)) {
				return p;
			}
			p = p.getProximo();
		}
		return null;
	}

	public void retirar(T info) {
		NoLista<T> anterior = null;
		NoLista<T> p = this.primeiro;

		while (p != null && !p.getInfo().equals(info)) {
			anterior = p;
			p = p.getProximo();
		}

		if (p != null) {
			if (p == primeiro)
				this.primeiro = primeiro.getProximo();
			else
				anterior.setProximo(p.getProximo());
		}
	}

	public int obterComprimento() {
		int qtdNos = 0;
		NoLista<T> p = this.primeiro;
		while (p != null) {
			p = p.getProximo();
			qtdNos++;
		}
		return qtdNos;
	}

	// Ao usar o obterComprimento com um laco dentro dele, a aplicação fica mais custosa.
	public NoLista<T> obterNo(int posicao) {
		NoLista<T> p = getPrimeiro();
		if (posicao < 0)
			throw new IndexOutOfBoundsException();

		while ((p != null) && posicao > 0) {
			posicao--;
			p = p.getProximo();
		}

		if (p == null)
			throw new IndexOutOfBoundsException();

		return p;
	}

	public void liberar() {
		primeiro = null;
	}

	public String toString() {
		String str = "";
		NoLista<T> p = this.primeiro;
		while (p != null) {
			if (p == this.primeiro) {
				str += "" + p.getInfo();
				p = p.getProximo();
			}
			str += "," + p.getInfo();
			p = p.getProximo();
		}
		return str;
	}
}
