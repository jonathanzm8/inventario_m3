package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedores;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.excepciones.KrakeException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProveedoresBDD {
	public ArrayList<Proveedores>buscar(String subcadena) throws KrakeException{
		ArrayList<Proveedores> proveedor = new ArrayList<Proveedores>();
		Connection con= null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		Proveedores proveedores = null;
		
		try {
			con= ConexionBDD.obtenerConexion();
			ps= con.prepareStatement("select pro.identificador, pro.tipo_de_documento,td.descripcion, pro.nombre, pro.telefono, pro.correo, pro.direccion"
					+ " from proveedores pro , tipo_de_documentos td"
					+ " where pro.tipo_de_documento = td.codigo "
					+ " and upper(nombre) like ?");
			
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			System.out.println(ps);
			rs=ps.executeQuery();
			
			while(rs.next()) {
				String identificador= rs.getString("Identificador");
				String tipo_de_documento= rs.getString("tipo_de_documento");
				String descripcion= rs.getString("descripcion");
				String nombre= rs.getString("nombre");
				String telefono= rs.getString("telefono");
				String correo= rs.getString("correo");
				String direccion= rs.getString("direccion");
				TipoDocumentos td = new TipoDocumentos(tipo_de_documento,descripcion);
				proveedores= new Proveedores(identificador,td,nombre,telefono,correo,direccion );
				
				proveedor.add(proveedores);
			}
			
		}catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("error al consutar. detalle" + e.getMessage());
		}
		
		return proveedor;
	}
	
	public void insertar(Proveedores proveedor) throws KrakeException{
		
		Connection con= null;
		PreparedStatement ps= null;
	
		
		try {
			con= ConexionBDD.obtenerConexion();
			ps= con.prepareStatement("insert into proveedores(identificador, tipo_de_documento, nombre, telefono, correo, direccion) "
					
					+ "values(?,?,?,?,?,?); ");
			
			ps.setString(1, proveedor.getIdentificador());
			ps.setString(2, proveedor.getTipoDocumento().getCodigo());
			ps.setString(3, proveedor.getNombre());
			ps.setString(4, proveedor.getTelefono());
			ps.setString(5, proveedor.getCorreo());
			ps.setString(6, proveedor.getDireccion());
			
			
		
			ps.executeUpdate();
			
	
			
		}catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("error al ingresar un proveedor" + e.getMessage());
		}
		
		
	}

}
