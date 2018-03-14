package be.vdab.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Land;
import be.vdab.entities.Soort;
import be.vdab.services.LandService;
import be.vdab.services.SoortService;
import be.vdab.services.WijnService;
import be.vdab.util.StringUtils;

@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private final transient LandService landService = new LandService();
	private final transient SoortService soortService = new SoortService();
	private final transient WijnService wijnService = new WijnService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("landen", landService.findAll());

		if (request.getQueryString() != null) {
			Optional<Land> optionalLand = getLand(request);
			if(optionalLand.isPresent()) {
				request.setAttribute("land", optionalLand.get());
				request.setAttribute("soorten", soortService.findByLand(optionalLand.get().getId()));
				Optional<Soort> optionalSoort = getSoort(request);
				if(optionalSoort.isPresent()) {
					request.setAttribute("soort", optionalSoort.get());
					request.setAttribute("wijnen", wijnService.findBySoort(optionalSoort.get().getId()));
				}
			}
		}
		
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	private Optional<Land> getLand(HttpServletRequest request) {
		String landIdString = request.getParameter("landid");
		if (StringUtils.isLong(landIdString)) {
			long landId = Long.parseLong(landIdString);
			return landService.find(landId);
		}
		return Optional.empty();
	}

	private Optional<Soort> getSoort(HttpServletRequest request) {
		String soortIdString = request.getParameter("soortid");
		if (StringUtils.isLong(soortIdString)) {
			long soortId = Long.parseLong(soortIdString);
			return soortService.find(soortId);
		}
		return Optional.empty();
	}
}