package be.vdab.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.WijnService;
import be.vdab.util.StringUtils;

@WebServlet("/wijn.htm")
public class WijnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/wijn.jsp";
	private final transient WijnService wijnService = new WijnService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(request.getQueryString() != null) {
			String wijnIdString = request.getParameter("wijnid");
			if(StringUtils.isLong(wijnIdString)) {
				long wijnId = Long.parseLong(wijnIdString);
				wijnService.find(wijnId).ifPresent(wijn -> request.setAttribute("wijn", wijn));;
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
