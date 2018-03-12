package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.entities.Bestelbon;
import be.vdab.services.BestelbonService;
import be.vdab.services.WijnService;
import be.vdab.util.StringUtils;
import be.vdab.valueobjects.Adres;
import be.vdab.valueobjects.Mandje;

@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";
	private final transient BestelbonService bestelbonService = new BestelbonService();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> fouten = new LinkedHashMap<>();
		HttpSession session = request.getSession(false);
		if(session != null) {
			Mandje mandje = (Mandje) session.getAttribute("mandje");
			if(mandje != null && !mandje.isEmpty()) {
				List<String> parameters = new ArrayList<>(Arrays.asList("naam", "straat", "huisnummer", "postcode", "gemeente", "bestelwijze"));
				Map<String, String> gegevens = new LinkedHashMap<>();
				for(String parameter:parameters) {
					String requestParameter = request.getParameter(parameter);
					if(StringUtils.isStringValid(requestParameter)) {
						gegevens.put(parameter, requestParameter);
					} else {
						fouten.put(parameter, "verplicht");
					}
				}
				if(fouten.isEmpty()) {
					Adres adres = new Adres(gegevens.get("straat"), gegevens.get("huisnummer"), gegevens.get("postcode"), gegevens.get("gemeente"));
					Bestelbon bestelbon = new Bestelbon(gegevens.get("naam"), adres, (short) (gegevens.get("bestelwijze").equals("afhalen") ? 0 : 1), mandje.getMandje());
					try {
						session.removeAttribute("mandje");
						bestelbonService.create(bestelbon);
						session.setAttribute("bestelbonnummer", bestelbon.getId());
					} catch (PersistenceException ex) {
						fouten.put("bestelbon", "niet gemaakt");
					}
				}
			}
		}
		if(fouten.isEmpty()) {
			response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
		} else {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}
}
