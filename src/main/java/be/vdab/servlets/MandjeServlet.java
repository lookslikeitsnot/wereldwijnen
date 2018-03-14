package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.entities.Bestelbon;
import be.vdab.exceptions.RecordAangepastException;
import be.vdab.services.BestelbonService;
import be.vdab.services.WijnService;
import be.vdab.util.StringUtils;
import be.vdab.valueobjects.Adres;
import be.vdab.valueobjects.Mandje;

@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";
	private static final String MANDJE = "mandje";
	private final transient BestelbonService bestelbonService = new BestelbonService();
	private final transient WijnService wijnService = new WijnService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setMandjeAttributes(request);
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Optional<Mandje> optionalMandje = getMandje(request);
		if (optionalMandje.isPresent()) {
			HttpSession session = request.getSession(false);
			Mandje mandje = optionalMandje.get();
			Optional<Map<String, String>> optionalGegevens = checkGegevens(request);
			if (optionalGegevens.isPresent()) {
				Map<String, String> gegevens = optionalGegevens.get();
				Adres adres = new Adres(gegevens.get("straat"), gegevens.get("huisnummer"), gegevens.get("postcode"),
						gegevens.get("gemeente"));
				Bestelbon bestelbon = new Bestelbon(gegevens.get("naam"), adres,
						(short) (gegevens.get("bestelwijze").equals("afhalen") ? 0 : 1),
						mandje.toBestelbonlijnen(wijnService));
				try {
					session.removeAttribute(MANDJE);
					bestelbonService.create(bestelbon);
					session.setAttribute("bestelbonnummer", bestelbon.getId());
					response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
				} catch (PersistenceException | RecordAangepastException ex) {}

			} else {
				setMandjeAttributes(request);
				request.getRequestDispatcher(VIEW).forward(request, response);
			}
		} else {
			setMandjeAttributes(request);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

	private Optional<Map<String, String>> checkGegevens(HttpServletRequest request) {
		Map<String, String> fouten = new LinkedHashMap<>();
		List<String> parameters = List.of("naam", "straat", "huisnummer", "postcode", "gemeente", "bestelwijze");
		Map<String, String> gegevens = new LinkedHashMap<>();
		for (String parameter : parameters) {
			String requestParameter = request.getParameter(parameter);
			if (StringUtils.isStringValid(requestParameter)) {
				gegevens.put(parameter, requestParameter);
			} else {
				fouten.put(parameter, "verplicht");
			}
		}
		if (!fouten.isEmpty()) {
			request.setAttribute("fouten", fouten);
			return Optional.empty();
		} else {
			return Optional.of(gegevens);
		}
	}

	private Optional<Mandje> getMandje(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return Optional.empty();
		Mandje mandje = (Mandje) session.getAttribute(MANDJE);
		if (mandje == null || mandje.isEmpty())
			return Optional.empty();
		return Optional.of(mandje);
	}

	private void setMandjeAttributes(HttpServletRequest request) {
		Optional<Mandje> optionalMandje = getMandje(request);
		if (optionalMandje.isPresent()) {
			request.setAttribute(MANDJE, optionalMandje.get().toBestelbonlijnen(wijnService));
			request.setAttribute("mandjePrijs", optionalMandje.get().getPrijs(wijnService));
		}
	}
}
