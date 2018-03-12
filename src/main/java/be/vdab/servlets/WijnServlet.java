package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.entities.Wijn;
import be.vdab.services.WijnService;
import be.vdab.util.StringUtils;
import be.vdab.valueobjects.Bestelbonlijn;
import be.vdab.valueobjects.Mandje;

@WebServlet("/wijn.htm")
public class WijnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/wijn.jsp";
	private static final String MANDJE = "mandje";
	private static final String REDIRECT_URL = "/index.htm";
	private final transient WijnService wijnService = new WijnService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getQueryString() != null) {
			String wijnIdString = request.getParameter("wijnid");
			if (StringUtils.isLong(wijnIdString)) {
				long wijnId = Long.parseLong(wijnIdString);
				wijnService.find(wijnId).ifPresent(wijn -> request.setAttribute("wijn", wijn));
				;
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> fouten = new LinkedHashMap<>();
		String wijnIdString = request.getParameter("wijnid");
		String aantalFlessenString = request.getParameter("aantalflessen");
		if (StringUtils.isInt(aantalFlessenString)) {
			int aantalFlessen = Integer.parseInt(aantalFlessenString);
			if (aantalFlessen > 0) {
				if (StringUtils.isLong(wijnIdString)) {
					long wijnId = Long.parseLong(wijnIdString);
					wijnService.find(wijnId).ifPresent(wijn -> request.setAttribute("wijn", wijn));
					;
					HttpSession session = request.getSession();
					Mandje mandje = (Mandje) session.getAttribute(MANDJE);
					if (mandje == null) {
						mandje = new Mandje();
					}
					Optional<Wijn> optionalWijn = wijnService.find(wijnId);
					if (optionalWijn.isPresent()) {
						mandje.add(new Bestelbonlijn(optionalWijn.get(), aantalFlessen));
					}
					session.setAttribute("mandje", mandje);
				}
			} else {
				fouten.put("aantalflessen", "Moet groter dan 1 zijn");
			}
		} else {
			fouten.put("aantalflessen", "Moet een geheel getal zijn");
		}

		if (!fouten.isEmpty()) {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		} else {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + REDIRECT_URL));
		}
	}

}