package com.krakedev.inventarios.entidades;

import java.util.ArrayList;
import java.util.Date;

public class Pedido {
	private int numero;
	private Proveedores proveedor;
	private Date fecha;
	private EstadoPedido estado;
	
	private ArrayList<DetallePedido> detalle;
	
	
	
	
	public ArrayList<DetallePedido> getDetalle() {
		return detalle;
	}
	public void setDetalle(ArrayList<DetallePedido> detalle) {
		this.detalle = detalle;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Proveedores getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedores proveedor) {
		this.proveedor = proveedor;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public EstadoPedido getEstado() {
		return estado;
	}
	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	public Pedido(int numero, Proveedores proveedor, Date fecha, EstadoPedido estado) {
		super();
		this.numero = numero;
		this.proveedor = proveedor;
		this.fecha = fecha;
		this.estado = estado;
	}
	
	public Pedido() {
		
	}
	@Override
	public String toString() {
		return "Pedido [numero=" + numero + ", proveedor=" + proveedor + ", fecha=" + fecha + ", estado=" + estado
				+ "]";
	}
	

}
