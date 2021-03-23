/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.agendaclient.servlets;

import com.mycompany.agendaclient.model.Entrada;
import com.mycompany.agendaclient.model.Entradas;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author viter
 */
@WebServlet(name = "AgendaClientXMLServlet2", urlPatterns = {"/AgendaClientXMLServlet2"})
public class AgendaClientXMLServlet2 extends HttpServlet {

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
        String res = "";
        Entradas e = null;

        response.setContentType("text/html;charset=UTF-8");

        Client client = ClientBuilder.newClient();

        String url = "http://localhost:8080/uff-bsi-dac-2020-1-04/";

        try {

            uri = new URI(url);
            WebTarget webTarget = client.target(uri);
            WebTarget agendaWebTarget = webTarget.path("resources/agenda/todos");

            Invocation.Builder invocationBuilder = agendaWebTarget.request(MediaType.APPLICATION_XML);

            Response resposta = invocationBuilder.get();

            status = resposta.getStatus();
            res = resposta.readEntity(String.class);

            try {
                JAXBContext context;
                context = JAXBContext.newInstance(Entradas.class);
                Unmarshaller um = context.createUnmarshaller();
                e = (Entradas) um.unmarshal(new StringReader(res));
            } catch (JAXBException ex) {
                Logger.getLogger(AgendaClientXMLServlet2.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(AgendaClientXMLServlet2.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ClientServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClientServlet XML</h1>");
            out.println("<p>Status da resposta: " + status + "</p>");

            if (e != null) {
                out.println("<table>");
                out.println("<tr><th>Id</th><th>Nome</th><th>e-mail</th><th>Whatsapp</th></tr>");
                Iterator<Entrada> entradasAsIterator = e.iterator();
                while (entradasAsIterator.hasNext()) {
                    Entrada ent = entradasAsIterator.next();
                    out.println("<tr>");
                    out.println("<td>" + ent.getId() + "</td><td>" +  ent.getNome() + " " + ent.getSobrenome() + "</td><td>" + ent.getMail() + "</td><td>" + ent.getZap() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
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
