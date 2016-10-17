package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.entities.Bestelbon;
import be.vdab.enums.Bestelwijze;
import be.vdab.services.BestelbonService;
import be.vdab.services.LandenService;
import be.vdab.valueobjects.Mandje;

@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final transient LandenService landenService = new LandenService();
	private final transient BestelbonService bestelBonService = new BestelbonService();

	private final static Logger logger = Logger.getLogger(LandenService.class.getName());

	private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";
	private static final String REDIRECT_NAAR_INDEX_URL = "%s/index.htm";
	private static final String REDIRECT_NAAR_BEVESTIGEN_URL = "%s/bevestigen.htm?bestelbonId=";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		//Als er geen mandje in de sessie zit, terugsturen naar index
		if (session == null || session.getAttribute("mandje") == null) {
			response.sendRedirect(String.format(REDIRECT_NAAR_INDEX_URL, request.getContextPath()));
		} else {
			//Er is een mandje, dus landen meegeven en bestelBon alvast aanmaken
			request.setAttribute("landen", landenService.findLanden());

			Mandje mandje = (Mandje) session.getAttribute("mandje");
			Bestelbon bestelBon = mandje.vanMandjeNaarBestelBonZonderGegevens();
			request.setAttribute("bestelBon", bestelBon);

			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("bevestigingsKnop") != null) {
			HttpSession session = request.getSession(false);

			//Als er geen mandje in de sessie zit, terugsturen naar index
			if (session == null || session.getAttribute("mandje") == null) {
				response.sendRedirect(String.format(REDIRECT_NAAR_INDEX_URL, request.getContextPath()));
			}

			Map<String, String> fouten = new HashMap<>();

			//Nagaan of alle ingevulde informatie effectief informatie bevat
			String naam = request.getParameter("naam");
			if (naam == null || naam.isEmpty()) {
				fouten.put("naam", "Gelieve een naam in te vullen");
			}
			String straat = request.getParameter("straat");
			if (straat == null || straat.isEmpty()) {
				fouten.put("naam", "Gelieve een straat in te vullen");
			}
			String huisnummer = request.getParameter("huisnummer");
			if (huisnummer == null || huisnummer.isEmpty()) {
				fouten.put("naam", "Gelieve een huisnummer in te vullen");
			}
			String postcode = request.getParameter("postcode");
			if (postcode == null || postcode.isEmpty()) {
				fouten.put("naam", "Gelieve een postcode in te vullen");
			}
			String gemeente = request.getParameter("gemeente");
			if (gemeente == null || gemeente.isEmpty()) {
				fouten.put("naam", "Gelieve een gemeente in te vullen");
			}

			//Nagaan welke bestelwijze is geselecteerd
			Bestelwijze bestelwijze = null;
			try {
				bestelwijze = Bestelwijze.valueOf(request.getParameter("bestelwijze"));
			} catch (Exception ex) {
				logger.log(Level.SEVERE, "parameters ongeldig", ex);
				fouten.put("bestelwijze", "Gelieve een (geldige) bestelwijze aan te duiden");
			}

			//Als er fouten zijn alle landen, bestelbon en de fouten meegeven
			if (!fouten.isEmpty()) {
				Mandje mandje = (Mandje) session.getAttribute("mandje");
				Bestelbon bestelBon = mandje.vanMandjeNaarBestelBonZonderGegevens();

				request.setAttribute("landen", landenService.findLanden());
				request.setAttribute("bestelBon", bestelBon);
				request.setAttribute("fouten", fouten);

				request.getRequestDispatcher(VIEW).forward(request, response);
			} else {
				//Er zijn geen fouten dus alles verwerken en doorsturen naar bevestigen pagina
				Mandje mandje = (Mandje) session.getAttribute("mandje");
				Bestelbon bestelBon = mandje.vanMandjeNaarBestelbonMetGegevens(naam, straat, huisnummer, postcode,
						gemeente, bestelwijze);

				bestelBonService.maakNieuweBestelbonVanMandje(bestelBon, mandje);

				session.removeAttribute("mandje");
				response.sendRedirect(
						String.format(REDIRECT_NAAR_BEVESTIGEN_URL + bestelBon.getId(), request.getContextPath()));
			}
		}
	}
}