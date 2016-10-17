package be.vdab.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.services.LandenService;
import be.vdab.services.SoortenService;

@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private final transient LandenService landenService = new LandenService();
	private final transient SoortenService SoortenService = new SoortenService();

	private final static Logger logger = Logger.getLogger(LandenService.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Alle landen opzoeken
		request.setAttribute("landen", landenService.findLanden());

		//Meegeven of er een mandje aanwezig is
		HttpSession session = request.getSession(false);
		request.setAttribute("mandjeAanwezig", (session != null && session.getAttribute("mandje") != null));

		//Als er een landId is opgegeven, opzoeken welke
		if (request.getParameter("landId") != null) {
			try {
				request.setAttribute("gekozenLand",
						landenService.findLandById(Long.parseLong(request.getParameter("landId"))));
				
				//proberen het soortId op te zoeken en mee te geven
				if (request.getParameter("soortId") != null) {
					try {
						request.setAttribute("gekozenSoort",
								SoortenService.findSoortById(Long.parseLong(request.getParameter("soortId"))));
					} catch (NumberFormatException ex) {
						logger.log(Level.SEVERE, "parameters ongeldig", ex);
					}
				}
			} catch (NumberFormatException ex) {
				logger.log(Level.SEVERE, "parameters ongeldig", ex);
			}
		}
	request.getRequestDispatcher(VIEW).forward(request,response);
	}
}