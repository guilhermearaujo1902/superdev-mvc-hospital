package br.com.mvcHospital.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvcHospital.model.Paciente;
import br.com.mvcHospital.util.ConnectionFactory;

public class PacienteDAOImpl implements GenericDAO {

	private Connection conn;

	public PacienteDAOImpl() throws Exception {
		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Object> listarTodos() {
		List<Object> lista = new ArrayList<Object>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM paciente ORDER BY id";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Paciente paciente = new Paciente();
				paciente.setId(rs.getInt("id"));
				paciente.setNome(rs.getString("nome"));
				paciente.setCpf(rs.getString("cpf"));
				paciente.setInternado(rs.getBoolean("is_internado"));
				paciente.setIdade(rs.getInt("idade"));
				lista.add(paciente);
			}
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao listar Paciente! " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			this.fecharConexao(conn, stmt, rs);
		}
		return lista;
	}

	@Override
	public Object listarPorId(int id) {
		Paciente paciente = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM paciente WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				paciente = new Paciente();
				paciente.setId(rs.getInt("id"));
				paciente.setNome(rs.getString("nome"));
				paciente.setCpf(rs.getString("cpf"));
				paciente.setInternado(rs.getBoolean("is_internado"));
				paciente.setIdade(rs.getInt("idade"));
			}

		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao listar Paciente por id! " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			this.fecharConexao(conn, stmt, rs);
		}

		return paciente;
	}

	@Override
	public boolean cadastrar(Object object) {
		Paciente paciente = (Paciente) object;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO paciente "
				+ "(nome, cpf, idade, is_plantao) "
				+ "VALUES (?,?,?,?)";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paciente.getNome());
			stmt.setString(2, paciente.getCpf());
			stmt.setInt(3, paciente.getIdade());
			stmt.setBoolean(4, paciente.isInternado());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao cadastrar Paciente " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			this.fecharConexao(conn, stmt, null);		
		}
	}

	@Override
	public boolean alterar(Object object) {
		Paciente paciente = (Paciente) object;
		PreparedStatement stmt = null;
		String sql = "UPDATE paciente SET "
				+ "nome = ?, "
				+ "cpf = ?, "
				+ "idade = ?, "
				+ "is_plantao = ? "
				+ "WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paciente.getNome());
			stmt.setString(2, paciente.getCpf());
			stmt.setInt(3, paciente.getIdade());
			stmt.setBoolean(4, paciente.isInternado());
			stmt.setInt(5, paciente.getId());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Erros na DAO ao alterar Paciente! " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			this.fecharConexao(conn, stmt, null);	
		}
	}

	@Override
	public void excluir(int id) {
		PreparedStatement stmt = null;
		String sql = "DELETE FROM paciente WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao excluir Paciente! " + ex.getMessage());
		} finally {
			this.fecharConexao(conn, stmt, null);
		}
	}

	private void fecharConexao(Connection conn, PreparedStatement stmt, ResultSet rs) {
		try {
			ConnectionFactory.closeConnection(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("Problemas na DAO ao fechar conexão! " + e.getMessage());
		}
	}

}
