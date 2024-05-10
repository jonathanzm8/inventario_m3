package com.krakedev.inventarios.entidades;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

public class CabeceraVentas {
	private int codigo;
	private Date fecha;
	private BigDecimal TotalSinIva;
	private BigDecimal Iva;
	private BigDecimal total;
	
	private ArrayList<DetalleVentas> detalle;
	
	public CabeceraVentas() {
		
	}

	public CabeceraVentas(int codigo, Date fecha, BigDecimal totalSinIva, BigDecimal iva, BigDecimal total,
			ArrayList<DetalleVentas> detalle) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		TotalSinIva = totalSinIva;
		Iva = iva;
		this.total = total;
		this.detalle = detalle;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTotalSinIva() {
		return TotalSinIva;
	}

	public void setTotalSinIva(BigDecimal totalSinIva) {
		TotalSinIva = totalSinIva;
	}

	public BigDecimal getIva() {
		return Iva;
	}

	public void setIva(BigDecimal iva) {
		Iva = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public ArrayList<DetalleVentas> getDetalle() {
		return detalle;
	}

	public void setDetalle(ArrayList<DetalleVentas> detalle) {
		this.detalle = detalle;
	}

	@Override
	public String toString() {
		return "CabeceraVentas [codigo=" + codigo + ", fecha=" + fecha + ", TotalSinIva=" + TotalSinIva + ", Iva=" + Iva
				+ ", total=" + total + ", detalle=" + detalle + "]";
	}
	
	
	

	

}
