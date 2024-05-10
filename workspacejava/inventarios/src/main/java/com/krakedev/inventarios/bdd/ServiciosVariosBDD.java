package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.CategoriaUDM;
import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.EstadoPedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.Proveedores;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.entidades.UnidadDeMedida;
import com.krakedev.inventarios.excepciones.KrakeException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ServiciosVariosBDD {
	
	public void actualizarProducto(Producto producto) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("Update producto set nombre = ?, udm = ?, precio_venta = ?, tiene_iva = ?, coste = ?, categoria = ? , stock = ? "
					+ "where codigo_pro = ?");

			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadMedida().getCodigo());
			ps.setBigDecimal(3, producto.getPrecio());
			ps.setBoolean(4, producto.isTieneIva());
			ps.setBigDecimal(5, producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());
			ps.setInt(8, producto.getCodigo());
			
			ps.executeUpdate();
			
			System.out.println();
		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Actualizar producto. Detalle: " + e.getMessage());
		}
	}
	
	
	public void crearCategoria(Categoria categoria) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into categorias(nombre, categoria_padre) "
					+ " values(?, ?);");

			ps.setString(1, categoria.getNombre());
			ps.setInt(2, categoria.getCategoriaPadre().getCodigo());

			ps.executeUpdate();

		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al insertar Categoria. Detalle: " + e.getMessage());
		}
	}
	
	// actualizar categoria
	
	public void actualizar(Categoria categoria) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("update categorias set nombre= ?, categoria_padre= ? "
					+ " where codigo_cat= ?");

			ps.setString(1, categoria.getNombre());
			ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
			ps.setInt(3, categoria.getCodigo());

			ps.executeUpdate();

		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Actualizar Categoria. Detalle: " + e.getMessage());
		}
	}
	
	// buscar categoria
	
	public ArrayList<Categoria> buscar() throws KrakeException {
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Categoria categoria;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(" select codigo_cat , nombre,categoria_padre from categorias");

			rs = ps.executeQuery();
			while (rs.next()) {
				int codigo = rs.getInt("codigo_cat");
				String nombre = rs.getString("nombre");
				Categoria categoriaPadre = new Categoria();
				categoria = new Categoria(codigo, nombre, categoriaPadre);
				categorias.add(categoria);
			}
		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Buscar Categoria. Detalle: " + e.getMessage());
		}
		return categorias;
	}
	
	// buscar pedido por proveedor
	
	public ArrayList<Pedido> buscarPorProveedor(String identificador) throws KrakeException {
		ArrayList<Pedido> listPedidos = new ArrayList<Pedido>();
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psAux = null;
		ResultSet rs = null;
		ResultSet rsAux = null;
		Pedido p = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(
					"select pro.identificador, pro.nombre, pro.telefono, pro.correo, pro.direccion, td.codigo, "
							+ "td.descripcion AS descipcion_doc, cp.numero, cp.fecha, cp.estado, ep.descripcion AS descripcion_estado "
							+ "from proveedores pro, cabecera_pedido cp, estados_pedido ep, tipo_de_documentos td "
							+ "Where cp.proveedor = pro.identificador " + "AND ep.codigo = cp.estado "
							+ "AND td.codigo = pro.tipo_de_documento " + "AND cp.proveedor = ?");
			ps.setString(1, identificador);
			rs = ps.executeQuery();
			while (rs.next()) {
				String codigoTd = rs.getString("codigo");
				String descripcionTd = rs.getString("descipcion_doc");
				TipoDocumentos td = new TipoDocumentos(codigoTd, descripcionTd);

				String nombre = rs.getString("nombre");
				String telefono = rs.getString("telefono");
				String correo = rs.getString("correo");
				String direccion = rs.getString("direccion");
				Proveedores pro = new Proveedores(identificador, td, nombre, telefono, correo, direccion);

				String codigoEstado = rs.getString("estado");
				String descripcionEstado = rs.getString("descripcion_estado");
				EstadoPedido ep = new EstadoPedido(codigoEstado, descripcionEstado);

				int codigo = rs.getInt("numero");
				Date fecha = rs.getDate("fecha");
				p = new Pedido(codigo, pro, fecha, ep);

				psAux = con.prepareStatement(
						"select dt.codigo AS codigo_detalle , dt.cabecera_pedido, pro.codigo_pro, pro.nombre, "
								+ "udm.codigo_udm, udm.descripcion AS descripcion_udm, udm.categoria_udm, cast(pro.precio_venta as decimal(6,2)), "
								+ "pro.tiene_iva, cast(pro.coste as decimal(5,4)), pro.categoria, c.nombre As nombre_categoria, pro.stock, "
								+ "dt.cantidad_solicitada, cast(dt.subtotal as decimal(10,4)), dt.cantidad_recibida "
								+ "from detalle_pedido dt, producto pro, categorias c, unidades_de_medida udm "
								+ "Where dt.producto = pro.codigo_pro " + "AND pro.categoria = c.codigo_cat "
								+ "AND pro.udm = udm.codigo_udm " + "AND dt.cabecera_pedido = ?");
				psAux.setInt(1, codigo);
				rsAux = psAux.executeQuery();
				ArrayList<DetallePedido> listDetalle = new ArrayList<DetallePedido>();
				while (rsAux.next()) {
					String codigoUdm = rsAux.getString("codigo_udm");
					String descripcionUdm = rsAux.getString("descripcion_udm");
					CategoriaUDM categoriaUdm = new CategoriaUDM(rsAux.getString("categoria_udm"), null);
					UnidadDeMedida udm = new UnidadDeMedida(codigoUdm, descripcionUdm, categoriaUdm);

					int codigoCat = rsAux.getInt("categoria");
					String nombreCategoria = rsAux.getString("nombre_categoria");
					Categoria cate = new Categoria(codigoCat, nombreCategoria, null);

					int codigoProducto = rsAux.getInt("codigo_pro");
					String nombreProducto = rsAux.getString("nombre");
					BigDecimal precioVenta = rsAux.getBigDecimal("precio_venta");
					boolean tieneIva = rsAux.getBoolean("tiene_iva");
					BigDecimal coste = rsAux.getBigDecimal("coste");
					int stock = rsAux.getInt("stock");
					Producto producto = new Producto(codigoProducto, nombreProducto, udm, precioVenta, tieneIva, coste,
							cate, stock);

					int codigoDetalle = rsAux.getInt("cabecera_pedido");
					int cantidadSolicitada = rsAux.getInt("cantidad_solicitada");
					BigDecimal Subtotal = rsAux.getBigDecimal("subtotal");
					int cantidadRecibida = rsAux.getInt("cantidad_recibida");
					DetallePedido detalle = new DetallePedido(codigoDetalle, null, producto, cantidadSolicitada,
							Subtotal, cantidadRecibida);
					listDetalle.add(detalle);

				}
				p.setDetalle(listDetalle);
			
				listPedidos.add(p);
			}
		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Buscar Proveedor: " + e.getMessage());
		}
		return listPedidos;
	}
	
	
	// buscar por identificador provvedor
	
	public Proveedores buscarPorIdentificador(String cadena) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Proveedores proveedor = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(
					"select pro.identificador, pro.tipo_de_documento, td.descripcion, pro.nombre, pro.telefono, pro.correo, pro.direccion from proveedores pro, tipo_de_documentos td "
							+ "Where pro.tipo_de_documento = td.codigo " + "AND identificador = ?");
			ps.setString(1, cadena);
			rs = ps.executeQuery();
			if (rs.next()) {
				String identificador = rs.getString("identificador");
				String codigoTipoDocumento = rs.getString("tipo_de_documento");
				String descripcionTd = rs.getString("descripcion");
				String nombre = rs.getString("nombre");
				String telefono = rs.getString("telefono");
				String correo = rs.getString("correo");
				String direccion = rs.getString("direccion");
				TipoDocumentos tp = new TipoDocumentos(codigoTipoDocumento, descripcionTd);
				proveedor = new Proveedores(identificador, tp, nombre, telefono, correo, direccion);
			}

		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Buscar Proveedor: " + e.getMessage());
		}
		return proveedor;
	}
	
	
	//buscar Producto por id 
	
	public Producto buscarPorId(int codigo) throws KrakeException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Producto p = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement(
					"select pro.codigo_pro, pro.nombre, udm.codigo_udm, udm.descripcion AS descripcion_udm, udm.categoria_udm, cast(pro.precio_venta as decimal(6,2)), "
							+ "pro.tiene_iva, cast(pro.coste as decimal(5,4)), pro.categoria, c.nombre As nombre_categoria, pro.stock "
							+ "from producto pro, categorias c, unidades_de_medida udm "
							+ "Where pro.categoria = c.codigo_cat " + "AND pro.udm = udm.codigo_udm "
							+ "AND pro.codigo_pro = ?;");
			ps.setInt(1, codigo);
			rs = ps.executeQuery();
			if (rs.next()) {
				String codigoUdm = rs.getString("codigo_udm");
				String descripcion = rs.getString("descripcion_udm");
				CategoriaUDM categoriaUdm = new CategoriaUDM(rs.getString("categoria_udm"), null);
				UnidadDeMedida udm = new UnidadDeMedida(codigoUdm, descripcion, categoriaUdm);

				int codigoCat = rs.getInt("categoria");
				String nombreCategoria = rs.getString("nombre");
				Categoria cate = new Categoria(codigoCat, nombreCategoria, null);

				int codigoProducto = rs.getInt("codigo_pro");
				String nombreProducto = rs.getString("nombre");
				BigDecimal precioVenta = rs.getBigDecimal("precio_venta");
				boolean tieneIva = rs.getBoolean("tiene_iva");
				BigDecimal coste = rs.getBigDecimal("coste");
				;
				int stock = rs.getInt("stock");
				p = new Producto(codigoProducto, nombreProducto, udm, precioVenta, tieneIva, coste, cate, stock);
			}
		} catch (KrakeException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeException("Error al Buscar Producto: " + e.getMessage());
		}

		return p;
	}

}
