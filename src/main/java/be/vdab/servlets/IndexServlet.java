package be.vdab.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Soort;
import be.vdab.entities.Wijn;
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
		if(request.getQueryString() != null) {
			String landIdString = request.getParameter("landid");
			if(StringUtils.isLong(landIdString)) {
				long landId = Long.parseLong(landIdString);
				landService.find(landId).ifPresent(land -> request.setAttribute("land",land));
				List<Soort> soorten = soortService.findByLand(landId);
				request.setAttribute("soorten", soorten);
				String soortIdString = request.getParameter("soortid");
				if(StringUtils.isLong(soortIdString)) {
					long soortId = Long.parseLong(soortIdString);
					soortService.find(soortId).ifPresent(soort -> request.setAttribute("soort", soort));
					List<Wijn> wijnen = wijnService.findBySoort(soortId);
					request.setAttribute("wijnen", wijnen);
				}
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}