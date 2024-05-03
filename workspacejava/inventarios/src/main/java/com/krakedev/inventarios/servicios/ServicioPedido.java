package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.PedidoBDD;
import com.krakedev.inventarios.bdd.ProductoBDD;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.excepciones.KrakeException;

@Path("pedidos")
public class ServicioPedido {
	
	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response crear ( Pedido pedido){
		PedidoBDD proBDD = new PedidoBDD();
	
		try {
			proBDD.insertar(pedido);
			return Response.ok().build();
			
		} catch (KrakeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
