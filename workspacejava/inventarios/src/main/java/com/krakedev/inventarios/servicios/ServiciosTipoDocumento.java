package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.TipoDocumentosBDD;
import com.krakedev.inventarios.entidades.TipoDocumentos;
import com.krakedev.inventarios.excepciones.KrakeException;



@Path("tiposDocumentos")

public class ServiciosTipoDocumento {
	
	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response obtenerTipoDocumento(){
		TipoDocumentosBDD doc = new TipoDocumentosBDD();
		ArrayList<TipoDocumentos> documentos= null;
		try {
			documentos = doc.buscar();
			return Response.ok(documentos).build();
			
		} catch (KrakeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
		

  }

}
