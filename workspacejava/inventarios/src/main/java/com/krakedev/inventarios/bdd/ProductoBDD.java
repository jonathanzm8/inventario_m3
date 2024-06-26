package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.Proveedores;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakeException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProductoBDD {
	
	public ArrayList<Producto>buscar(String subcadena) throws KrakeException{
		ArrayList<Producto> productos = new ArrayList<Producto>();
		Connection con= null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		Producto producto = null;
		
		try {
			con= ConexionBDD.obtenerConexion();
			ps= con.prepareStatement("select pro.codigo_pro, pro.nombre as nombre_producto, udm.codigo_udm,udm.descripcion as descripcion_udm,"
					+ " cast(pro.precio_venta as decimal(6,2)),pro.tiene_iva,cast(pro.coste as decimal(5,4)), pro.categoria,cat.nombre as nombre_categoria, pro.stock"
					+ " from producto pro,  unidades_de_medida udm,  categorias cat "
					+ " where pro.udm = udm.codigo_udm "
					+ " and pro.categoria = cat.codigo_cat "
					+ " and upper (pro.nombre) like ?");
			
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			System.out.println(ps);
			rs=ps.executeQuery();
			
			while(rs.next()) {
				int codigoProducto= rs.getInt("codigo_pro");
				String nombreProducto= rs.getString("nombre_producto");
				String codigoUdm= rs.getString("codigo_udm");
				String descripcionUdm= rs.getString("descripcion_udm");
				BigDecimal precioVenta= rs.getBigDecimal("precio_venta");
				boolean tieneIva= rs.getBoolean("tiene_iva");
				BigDecimal coste= rs.getBigDecimal("coste");
				int  categoria= rs.getInt("categoria");
				String nombreCategoria= rs.getString("nombre_categoria");
				int  stock= rs.getInt("stock");
				
				UnidadDeMedida udm= new UnidadDeMedida();
				udm.setCodigo(codigoUdm);
				udm.setDescripcion(descripcionUdm);
				
				Categoria ca = new Categoria();
				ca.setCodigo(categoria);
				ca.setNombre(nombreCategoria);
				
				producto = new Producto();
				producto.setCodigo(codigoProducto);
				producto.setNombre(nombreProducto);
				producto.setUnidadMedida(udm);
				producto.setPrecio(precioVenta);
				producto.setTieneIva(tieneIva);
				producto.setCoste(coste);
				producto.setCategoria(ca);
				producto.setStock(stock);
				
				productos.add(producto);
				
				
				
				
				
			}
			
		}catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("error al consutar. detalle" + e.getMessage());
		}
		
		return productos;
	}
	
public void crear(Producto producto) throws KrakeException{
		
		Connection con= null;
		PreparedStatement ps= null;
	
		
		try {
			con= ConexionBDD.obtenerConexion();
			ps= con.prepareStatement("insert into producto(nombre, udm, precio_venta, tiene_iva, coste, categoria, stock) "
					
					+ "values(?,?,?,?,?,?,?); ");
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadMedida().getCodigo());
			ps.setBigDecimal(3, producto.getPrecio());
			ps.setBoolean(4, producto.isTieneIva());
			ps.setBigDecimal(5,producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());
		
			
			
		
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
