package br.com.mvcHospital.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mvcHospital.model.Medico;
import br.com.mvcHospital.util.ConnectionFactory;

public class MedicoDAOImpl implements GenericDAO {

	private Connection conn;

	public MedicoDAOImpl() throws Exception {
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
		String sql = "SELECT * FROM medico ORDER BY id";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Medico medico = new Medico();
				medico.setId(rs.getInt("id"));
				medico.setNome(rs.getString("nome"));
				medico.setEspecialidade(rs.getString("especialidade"));
				medico.setCrm(rs.getString("crm"));
				medico.setPlantao(rs.getBoolean("is_plantao"));
				lista.add(medico);
			}
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao listar Medico! " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			this.fecharConexao(conn, stmt, rs);
		}
		return lista;
	}

	@Override
	public Object listarPorId(int id) {
		Medico medico = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM medico WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				medico = new Medico();
				medico.setId(rs.getInt("id"));
				medico.setNome(rs.getString("nome"));
				medico.setEspecialidade(rs.getString("especialidade"));
				medico.setCrm(rs.getString("crm"));
				medico.setPlantao(rs.getBoolean("is_plantao"));
			}

		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao listar Medico por id! " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			this.fecharConexao(conn, stmt, rs);
		}

		return medico;
	}

	@Override
	public boolean cadastrar(Object object) {
		Medico medico = (Medico) object;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO medico "
				+ "(nome, especialidade, crm, is_plantao) "
				+ "VALUES (?,?,?,?)";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, medico.getNome());
			stmt.setString(2, medico.getEspecialidade());
			stmt.setString(3, medico.getCrm());
			stmt.setBoolean(4, medico.isPlantao());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao cadastrar Médico " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			this.fecharConexao(conn, stmt, null);		
		}
	}

	@Override
	public boolean alterar(Object object) {
		Medico medico = (Medico) object;
		PreparedStatement stmt = null;
		String sql = "UPDATE medico SET "
				+ "nome = ?, "
				+ "especialidade = ?, "
				+ "crm = ?, "
				+ "is_plantao = ? "
				+ "WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, medico.getNome());
			stmt.setString(2, medico.getEspecialidade());
			stmt.setString(3, medico.getCrm());
			stmt.setBoolean(4, medico.isPlantao());
			stmt.setInt(5, medico.getId());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			System.out.println("Erros na DAO ao alterar Medico! " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} finally {
			this.fecharConexao(conn, stmt, null);	
		}
	}

	@Override
	public void excluir(int id) {
		PreparedStatement stmt = null;
		String sql = "DELETE FROM medico WHERE id = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException ex) {
			System.out.println("Problemas na DAO ao excluir Medico! " + ex.getMessage());
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
