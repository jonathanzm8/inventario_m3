package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.excepciones.KrakeException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class TipoDocumentosBDD {
	
	public ArrayList<TipoDocumentos>buscar() throws KrakeException{
		ArrayList<TipoDocumentos> tipoDocumentos = new ArrayList<TipoDocumentos>();
		Connection con= null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		TipoDocumentos documentos = null;
		
		try {
			con= ConexionBDD.obtenerConexion();
			ps= con.prepareStatement("select codigo, descripcion from tipo_de_documentos");
			
			
			rs=ps.executeQuery();
			
			while(rs.next()) {
				String codigo= rs.getString("codigo");
				String descripcion= rs.getString("descripcion");
				
				documentos= new TipoDocumentos(codigo,descripcion );
				
				tipoDocumentos.add(documentos);
			}
			
		}catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("error al consutar. detalle" + e.getMessage());
		}
		
		return tipoDocumentos;
	}

}
