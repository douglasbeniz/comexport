/*
 *  ComexportBackendChallenge project - March of 2018
 * 
 *  author:   Douglas Bezerra Beniz (douglasbeniz@gmail.com)
 *  creation: 29-Mar-2018
 */
package com.comexport;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.json.Json;
import javax.json.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
@Path("/")

/*
 * http://localhost:8080/ComexportBackendChallenge/rest/lancamentos-contabeis?conta=1111001&data=20170130&valor=25000.15
 * curl -X GET http://localhost:8080/ComexportBackendChallenge/rest/lancamentos-contabeis/todos
 * curl -X POST -H "content-type: application/x-www-form-urlencoded" -d "conta=1111001&data=20170130&valor=25000.15" http://localhost:8080/ComexportBackendChallenge/rest/lancamentos-contabeis
 */
public class ContaService {
	// Instancia um objeto do tipo 'UserDAO' que representa nossa estrutura de dados
	ContaDAO contaDAO = new ContaDAO();

	@POST
	@Path("/lancamentos-contabeis")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response adicionaConta(
			@NotNull @FormParam("contaContabil") long contaContabil, 
			@NotNull @FormParam("data") long data, 
			@NotNull @FormParam("valor") double valor,
			@Context HttpServletResponse servletResponse) throws IOException {
		try {
			String novoId = contaDAO.adicionaConta(contaContabil, data, valor);
			JsonObject jsonReturn = Json.createObjectBuilder().add("id", novoId).build();
			// Retorna a resposta
			return Response.status(201).entity(jsonReturn.toString()).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/lancamentos-contabeis/{contaid}")
	@Produces(MediaType.APPLICATION_JSON)
	public ContaContabil getContaById(@PathParam("contaid") String contaid) {
		return contaDAO.getContaPorId(contaid);
	}

	@GET
	@Path("/lancamentos-contabeis")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContaContabil> getValoresPorConta(@NotNull @QueryParam("contaContabil") long contaContabil) {
		return contaContabil != 0 ? contaDAO.getValoresPorConta((long)contaContabil) : contaDAO.getTodasContas();
	}

	@GET
	@Path("/lancamentos-contabeis/contas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ContaContabil> getTodasContas() {
		return contaDAO.getTodasContas();
	}

	@GET
	@Path("/lancamentos-contabeis/_stats")
	@Produces(MediaType.APPLICATION_JSON)
	public StatsContabil getStatsPorConta(@NotNull @QueryParam("contaContabil") long contaContabil) {
		return contaContabil != 0 ? contaDAO.getStatsPorConta((long)contaContabil) : contaDAO.getTodasStats();
	}
}
