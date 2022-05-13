package co.empresa.test.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.empresa.test.dao.UsuarioDao;
import co.empresa.test.modelo.Usuario;

/**
 * Servlet implementation class UsuarioServlet
 */
@WebServlet("/")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private UsuarioDao usuarioDao;
	
    public UsuarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.usuarioDao = new UsuarioDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();
				
				System.out.println(action);
				
				try {
					switch(action) {
						case "/new":
							showNewForm(request, response);
							break;
						case "/insert":
							insertUsuario(request, response);
							break;
						case "/delete":
							deleteUsuario(request, response);
		                    break;
		                case "/edit":
		                	showEditForm(request, response);
		                    break;
		                case "/update":
		                	updateUsuario(request, response);
		                    break;
						default: 
							listaUsuarios(request, response);
							break;
					}
				} catch (SQLException ex) {
		            throw new ServletException(ex);
        }
				
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario.jsp");
		        dispatcher.forward(request, response);
		    }
	
	private void insertUsuario(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
		        String nombre = request.getParameter("nombre");
		        String email = request.getParameter("email");
		        String pais = request.getParameter("pais");
		        Usuario newUser = new Usuario(nombre, email, pais);
		        usuarioDao.insert(newUser);
		        response.sendRedirect("list");
		    }
	
	private void deleteUsuario(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        usuarioDao.delete(id);
		        response.sendRedirect("list");
		    }
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, ServletException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        Usuario usuarioActual = usuarioDao.select(id);
		        request.setAttribute("usuario", usuarioActual);

		        RequestDispatcher dispatcher = request.getRequestDispatcher("usuario.jsp");
		        dispatcher.forward(request, response);

		    }
	
	private void updateUsuario(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        
		        String nombre = request.getParameter("nombre");
		        String email = request.getParameter("email");
		        String pais = request.getParameter("pais");

		        Usuario usuario = new Usuario(id, nombre, email, pais);
		        System.out.println(usuario.getId());
		        usuarioDao.update(usuario);
		        response.sendRedirect("list");
		    }
	
	private void listaUsuarios(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException, ServletException {
		        List < Usuario > listUsuarios = usuarioDao.selectAll();
		        request.setAttribute("listUsuarios", listUsuarios);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("usuariolist.jsp");
		        dispatcher.forward(request, response);
		    }

}
