package br.com.mvcHospital.model;

public class Paciente {

	private int id;
	private String nome;
	private String cpf;
	private boolean isInternado;
	private int idade;
	private Medico medico;

	public Paciente() {

	}

	public Paciente(int id, String nome, String cpf, boolean isInternado, int idade, Medico medico) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.isInternado = isInternado;
		this.idade = idade;
		this.medico = medico;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean isInternado() {
		return isInternado;
	}

	public void setInternado(boolean isInternado) {
		this.isInternado = isInternado;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

}
