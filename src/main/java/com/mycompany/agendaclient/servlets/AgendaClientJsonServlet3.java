/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.agendaclient.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author viter
 */
@WebServlet(name = "AgendaClientJsonServlet3", urlPatterns = {"/AgendaClientJsonServlet3"})
public class AgendaClientJsonServlet3 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        URI uri;
        int status = 0;
        int k;
        String res = "";
        String id = "";
        String statusMsg = "";
        Response.StatusType statusInfo = null;
        JsonObject jsonObj = null;

        response.setContentType("text/html;charset=UTF-8");

        Client client = ClientBuilder.newClient();
        String url = "http://localhost:8080/uff-bsi-dac-2020-1-04/";

        JsonReaderFactory factory = Json.createReaderFactory(null);

        id = request.getParameter("id");

        try {

            uri = new URI(url);
            WebTarget webTarget = client.target(uri);
            WebTarget agendaWebTarget = webTarget.path("resources/agenda/entrada/" + id);

            Invocation.Builder invocationBuilder = agendaWebTarget.request(MediaType.APPLICATION_JSON);

            Response resposta = invocationBuilder.get();

            status = resposta.getStatus();
            statusInfo = resposta.getStatusInfo();
            statusMsg = statusInfo.getReasonPhrase();
            res = resposta.readEntity(String.class);

            if (status == 200) {

                JsonReader jsonReader = factory.createReader(new StringReader(res));
                if (jsonReader != null) {
                    jsonObj = jsonReader.readObject();
                }

            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(AgendaClientJsonServlet3.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ClientServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClientServlet JSON</h1>");
            out.println("<p>Status da resposta: " + status + " \"" + statusMsg + "\"</p>");

            if (jsonObj != null) {
                out.println("<p>[" + jsonObj.getInt("id") + "] " + jsonObj.getString("nome") + " " + jsonObj.getString("sobrenome") + " - e-mail: " + jsonObj.getString("mail") + " - Whatsapp: " + jsonObj.getString("zap") + "</p>");
            } else {
                out.println("<p>Nenhum registro recuperado</p>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
