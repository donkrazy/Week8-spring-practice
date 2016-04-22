package com.estsoft.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.guestbook.dao.GuestbookDao;
import com.estsoft.guestbook.vo.GuestbookVo;

@Controller
public class GuestbookController {
	@Autowired
	private GuestbookDao dao;
	
	@RequestMapping( value="/insert", method=RequestMethod.POST )
	public String insert(@ModelAttribute GuestbookVo vo) {
		dao.insert( vo );
		return "redirect:/";
	}
	
	
	@RequestMapping( value="/delete/{no}", method=RequestMethod.POST )
	public String delete( @PathVariable( "no" ) Long no, @ModelAttribute GuestbookVo vo ) {
		vo.setNo(no);
		dao.delete( vo );
		return "redirect:/";
	}
	
	@RequestMapping( value="/deleteform", method=RequestMethod.GET )
	public String deleteform (@RequestParam( value="no", required=true, defaultValue="-1" ) Long no) {
		return "/WEB-INF/views/deleteform.jsp";
	}

	
	@RequestMapping( "/" )
	public String index( Model model ) {
		List<GuestbookVo> list = dao.getList();
		model.addAttribute( "list", list );
		return "/WEB-INF/views/index.jsp";
	}
}
