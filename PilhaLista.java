package Trabalho1;

public class PilhaLista<T> implements Pilha<T> {

	private ListaEncadeada<T> lista = new ListaEncadeada<T>();

	@Override
	public void push(T v) {
		lista.inserir(v);
	}

	@Override
	public T pop() {
		T valor = peek();
		lista.retirar(valor);

		return valor;
	}

	@Override
	public T peek() {
		if (estaVazia()) {
			throw new PilhaVaziaException();
		} else {
			return lista.getPrimeiro().getInfo();
		}
	}

	@Override
	public boolean estaVazia() {
		return lista.estaVazia();
	}

	@Override
	public void liberar() {
		lista.liberar();
	}

	public String toString() {
		String str = "";
		NoLista<T> n = new NoLista<T>();
		n = lista.getPrimeiro();

		while (n != null) {
			if (n.getProximo() == null) {
				str += n.getInfo().toString();
			} else {
				str += n.getInfo() + ",";
			}
		}
		return str;
	}
}
