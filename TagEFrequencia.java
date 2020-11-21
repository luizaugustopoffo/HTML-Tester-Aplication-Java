package Trabalho1;

public class TagEFrequencia {

	private int frequencia;
	private String nome;
	
	public TagEFrequencia(int frequencia, String nome) {
		setFrequencia(frequencia);
		setNome(nome);
	}

	public int getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(int frequencia) {
		this.frequencia = frequencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "\nTagEFrequencia [frequencia=" + frequencia + ", nome=" + nome + "]";
	}
}
