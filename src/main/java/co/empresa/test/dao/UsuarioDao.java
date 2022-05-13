package co.empresa.test.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import co.empresa.test.modelo.Usuario;
import co.empresa.test.util.Conexion;

public class UsuarioDao {
	private Conexion conexion;
	
	private static final String INSERT_USUARIO_SQL = "INSERT INTO usuario (nombre, email, pais) VALUES (?, ?, ?);";
    private static final String DELETE_USUARIO_SQL = "DELETE FROM usuario WHERE id = ?;";
    private static final String UPDATE_USUARIO_SQL = "UPDATE usuario SET nombre = ?,email= ?, pais =? WHERE id = ?;";
    private static final String SELECT_USUARIO_BY_ID = "SELECT * FROM usuario WHERE id = ?";
    private static final String SELECT_ALL_USUARIOS = "SELECT * FROM usuario";
	
	public UsuarioDao() {
    	this.conexion = Conexion.getConexion();
    }
	
	public void insert(Usuario usuario) throws SQLException {
        try {
        	PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(INSERT_USUARIO_SQL);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getPais());
            conexion.execute();
        } catch (SQLException e) {
        	System.out.println("error");
        }
    }
	
	public void delete(int id) throws SQLException {
        boolean rowDeleted = false;
        try {
	    	PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(DELETE_USUARIO_SQL);
	    	preparedStatement.setInt(1, id);
	    	
	    	conexion.execute();
        } catch (SQLException e) {
        	
        }
    }
	
	public void update(Usuario user) throws SQLException {
		System.out.println(user.getNombre());
        try {
	    	PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(UPDATE_USUARIO_SQL);
	    	System.out.println(preparedStatement);
	    	preparedStatement.setString(1, user.getNombre());
	    	preparedStatement.setString(2, user.getEmail());
	    	preparedStatement.setString(3, user.getPais());
	    	preparedStatement.setInt(4, user.getId());
	    	
	    	conexion.execute();
        } catch (SQLException e) {
        	System.out.println("error update");
        }
        
    }
	
	public List < Usuario > selectAll() {

        List < Usuario > users = new ArrayList < > ();
        try {
        	
        	PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(SELECT_ALL_USUARIOS);
            System.out.println(preparedStatement);
            
            ResultSet rs = conexion.query();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String pais = rs.getString("pais");
                users.add(new Usuario(id, nombre, email, pais));
            }
        } catch (SQLException e) {
        	System.out.println("error 2");
        }
        return users;
    }
	
	
	 public Usuario select(int id) {
	        Usuario user = null;
	        // Step 1: Establishing a Connection
	        try {
	        	PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(SELECT_USUARIO_BY_ID);
	            preparedStatement.setInt(1, id);
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = conexion.query();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                String nombre = rs.getString("nombre");
	                String email = rs.getString("email");
	                String pais = rs.getString("pais");
	                user = new Usuario(id, nombre, email, pais);
	            }
	        } catch (SQLException e) {
	        	System.out.println("error select");
	        }
	        return user;
	    }
	
	
	
	
}
