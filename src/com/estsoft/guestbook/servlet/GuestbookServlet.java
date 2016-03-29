package com.estsoft.guestbook.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estsoft.db.MySQLWebDBConnection;
import com.estsoft.guestbook.dao.GuestbookDao;
import com.estsoft.guestbook.vo.GuestbookVo;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//post 방식의 한글(UTF-8) 데이터 처리
		request.setCharacterEncoding( "UTF-8" );
		
		// 요청분석
		String actionName = request.getParameter( "a" );
		
		if( "add".equals( actionName )  ) {
			String name = request.getParameter( "name" );
			String password = request.getParameter( "password" );
			String message = request.getParameter( "content" );
			
			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setMessage(message);
			
			GuestbookDao dao = new GuestbookDao( new MySQLWebDBConnection() );
			dao.insert(vo);			
			response.sendRedirect( "gb" );
		} else if( "deleteform".equals( actionName ) ) {
			RequestDispatcher rd = request.getRequestDispatcher(  "/WEB-INF/views/deleteform.jsp"  );
			rd.forward( request, response );			
		} else if( "delete".equals( actionName ) ) {
			Long no = Long.parseLong( request.getParameter( "no" ) );
			String password = request.getParameter( "password" );

			GuestbookVo vo = new GuestbookVo();
			vo.setNo( no );
			vo.setPassword(password);
			
			GuestbookDao dao = new GuestbookDao( new MySQLWebDBConnection() );
			dao.delete( vo );
			
			response.sendRedirect( "gb" );
		} else {
			// default action ( list, index )
			GuestbookDao dao = new GuestbookDao( new MySQLWebDBConnection() );
			List<GuestbookVo> list = dao.getList();
			
			// 포워딩전에 JSP로 보낼 데이터를 request범위(scope)에 저장한다.
			request.setAttribute( "list", list );
			
			// forwarding (request 확장, request dispatcher )
			RequestDispatcher rd = request.getRequestDispatcher(  "/WEB-INF/views/index.jsp"  );
			rd.forward( request, response );
		}		
	}
}