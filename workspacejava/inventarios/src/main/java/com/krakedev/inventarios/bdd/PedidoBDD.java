package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakeException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class PedidoBDD {
public void insertar(Pedido pedido) throws KrakeException{
		
		Connection con= null;
		PreparedStatement ps= null;
		PreparedStatement psDet= null;
		ResultSet clave = null;
		int codigoCabecera= 0;
		
		Date fechaActual= new Date();
		java.sql.Date fechaSQL= new java.sql.Date(fechaActual.getTime());
	
		
		try {
			con= ConexionBDD.obtenerConexion();
			ps= con.prepareStatement("insert into cabecera_pedido( proveedor, fecha, estado) "
					
					+ "values(?,?,?);",Statement.RETURN_GENERATED_KEYS); // para retornar una clave 
			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSQL);
			ps.setString(3, "S");
			
			ps.executeUpdate();
			clave=ps.getGeneratedKeys();
			
			if(clave.next()) {
				codigoCabecera=clave.getInt(1);
				
			}
			ArrayList<DetallePedido> detallesPedido = pedido.getDetalle();
			DetallePedido det;
			for(int i=0; i<detallesPedido.size();i++) {
				det= detallesPedido.get(i);
				psDet= con.prepareStatement("insert into detalle_pedido(cabecera_pedido, producto, cantidad_solicitada, subtotal, cantidad_recibida) "
						+ "values(?,?,?,?,?);");
				
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidadSolicitada());
				
				BigDecimal precioventa =det.getProducto().getPrecio();
				BigDecimal cantidad= new BigDecimal(det.getCantidadSolicitada());
				
				BigDecimal subtotal=precioventa.multiply(cantidad);
				
				psDet.setBigDecimal(4, subtotal);
				
				psDet.setInt(5, 0);
				
				psDet.executeUpdate();
				
			}
	
			
		}catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("error al ingresar un proveedor" + e.getMessage());
		}
		
		
	}


/////////////////////////// recibir o actualizar //////////////////////////////////////////////



public void recibir(Pedido pedido) throws KrakeException{
	
	Connection con= null;
	PreparedStatement ps= null;
	PreparedStatement psDet= null;


	
	try {
		con= ConexionBDD.obtenerConexion();
		ps= con.prepareStatement("update cabecera_pedido set estado = 'R' where numero= ? "); 
		
		
		ps.setInt(1, pedido.getNumero());
		
		ps.executeUpdate();
		
		
		
		ArrayList<DetallePedido> detallesPedido = pedido.getDetalle();
		DetallePedido det;
		for(int i=0; i<detallesPedido.size();i++) {
			det= detallesPedido.get(i);
			psDet= con.prepareStatement("update detalle_pedido set cantidad_recibida= ? ,subtotal= ? "
					+ "where codigo= ? ");
			
			psDet.setInt(1, det.getCantidadRecibida());
			
			BigDecimal precioventa =det.getProducto().getPrecio();
			BigDecimal cantidad= new BigDecimal(det.getCantidadRecibida());
			
			BigDecimal subtotal=precioventa.multiply(cantidad);
			
			psDet.setBigDecimal(2, subtotal);
			
			psDet.setInt(3, det.getCodigo());
			
			psDet.executeUpdate();
			
		}

		
	}catch (KrakeException e) {
		e.printStackTrace();
		throw e;
	}catch (SQLException e) {
		e.printStackTrace();
		throw new KrakeException("error al recibir un pedido" + e.getMessage());
	}
	
	
}


}
