package br.com.mvcHospital.DAO;

import java.util.List;

public interface GenericDAO {
	
public List<Object> listarTodos();
	
	public Object listarPorId(int id);
	
	public boolean cadastrar(Object object);
	
	public boolean alterar(Object object);
	
	public void excluir(int id);

}
