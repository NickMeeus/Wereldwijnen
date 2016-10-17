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
import be.vdab.services.WijnenService;
import be.vdab.valueobjects.Mandje;

@WebServlet("/toevoegen.htm")
public class ToevoegenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String VIEW = "/WEB-INF/JSP/toevoegen.jsp";
	private static final String REDIRECT_NAAR_INDEX_URL = "%s/index.htm";

	private final transient LandenService landenService = new LandenService();
	private final transient WijnenService wijnenService = new WijnenService();

	private final static Logger logger = Logger.getLogger(LandenService.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Alle landen meegeven en mandjeAanwezig alvast invullen
		request.setAttribute("landen", landenService.findLanden());
		HttpSession session = request.getSession(false);
		request.setAttribute("mandjeAanwezig", (session != null && session.getAttribute("mandje") != null));

		//wijnId opvragen en als het al in het mandje zit dit meegeven zodat het getoont kan worden in het "Aantal flessen" veld
		try {
			long wijnId = Long.parseLong(request.getParameter("wijnId"));
			if (wijnId > 0) {
				request.setAttribute("wijn", wijnenService.findWijnById(wijnId));

				if (session != null && session.getAttribute("mandje") != null) {
					Mandje mandje = (Mandje) session.getAttribute("mandje");

					int aantalFlessenInMandje = mandje.getAantalFlessenAlInMandje(wijnId);

					if (aantalFlessenInMandje > 0) {
						request.setAttribute("alInMandje", aantalFlessenInMandje);
					}
				}
				request.getRequestDispatcher(VIEW).forward(request, response);
			}
		} catch (NumberFormatException ex) {
			logger.log(Level.SEVERE, "parameters ongeldig", ex);
			response.sendRedirect(String.format(REDIRECT_NAAR_INDEX_URL, request.getContextPath()));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("toevoegen") != null) {
			if (request.getParameter("aantalFlessen") != null) {
				//Nagaan of aantal flessen en wijnId opgegeven is
				int aantalFlessen = 0;
				long wijnId = 0L;
				try {
					aantalFlessen = Integer.parseInt(request.getParameter("aantalFlessen"));
				} catch (NumberFormatException ex) {
					logger.log(Level.SEVERE, "parameters ongeldig", ex);
					request.setAttribute("fout", "gelieve een positieve getal in te voeren");
				}
				try {
					wijnId = Long.parseLong(request.getParameter("wijnId"));
				} catch (NumberFormatException ex) {
					logger.log(Level.SEVERE, "parameters ongeldig", ex);
					response.sendRedirect(String.format(REDIRECT_NAAR_INDEX_URL, request.getContextPath()));
				}

				//Als het een geldig wijnId is nagaan of er al een mandje is en anders 1 aanmaken
				HttpSession session = request.getSession();
				if (wijnId > 0) {
					Mandje mandje;
					if (session.getAttribute("mandje") != null) {
						mandje = (Mandje) session.getAttribute("mandje");
					} else {
						mandje = new Mandje();
					}

					//Als het opgegeven aantal flessen 0 is dit meegeven
					if (aantalFlessen == 0) {
						request.setAttribute("alInMandje", aantalFlessen);
						//Als de wijn al in het mandje zit deze lijn er uit verwijderen
						if (mandje.getAantalFlessenAlInMandje(wijnId) > 0) {
							mandje.removeLijn(wijnId);
							//Als het mandje leeg na de lijn te verwijderen leeg is het mandje verwijderen
							if (mandje.isLeeg()) {
								session.removeAttribute("mandje");
							}
						} else {
							session.setAttribute("mandje", mandje);
						}
					} else if (aantalFlessen > 0) {
						//Als er meer dan 0 flessen zijn opgegeven een lijn aanmaken in het mandje
						mandje.addBestelLijn(wijnId, aantalFlessen);
						session.setAttribute("mandje", mandje);
					} else {
						//Als het hier komt is het een negatief getal, dus foutmelding meegeven om te tonen
						request.setAttribute("fout", "gelieve een positieve getal in te voeren");
						request.getRequestDispatcher(VIEW).forward(request, response);
					}
				}
				response.sendRedirect(String.format(REDIRECT_NAAR_INDEX_URL, request.getContextPath()));

			} else {
				request.setAttribute("fout", "gelieve een positieve getal in te voeren");
				request.getRequestDispatcher(VIEW).forward(request, response);
			}
		}
	}
}