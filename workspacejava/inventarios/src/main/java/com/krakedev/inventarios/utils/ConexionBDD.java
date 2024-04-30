package com.krakedev.inventarios.utils;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.krakedev.inventarios.excepciones.KrakeException;
import com.krakedev.inventarios.excepciones.*;

public class ConexionBDD {

	public static Connection obtenerConexion()  throws KrakeException{
		Context ctx = null;
		DataSource ds = null;
		Connection con = null;
		try {
			ctx = new InitialContext();
			// JNDI busca ellementos 
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ConexionInventario");
			con = ds.getConnection();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new KrakeException("error de conexion");
		} 
		return con;
	
		

	}
}