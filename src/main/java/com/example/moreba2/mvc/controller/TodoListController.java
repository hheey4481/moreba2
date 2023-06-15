package com.example.moreba2.mvc.controller;


import com.example.moreba2.mvc.model.dto.TodoDTO;
import com.example.moreba2.mvc.service.TodoService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "todoListController", value = "/mypage/list")
@Log4j2
public class TodoListController extends HttpServlet {
    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info("todo list...");

        try{
            List<TodoDTO> dtoList = todoService.listAll();
            req.setAttribute("dtoList", dtoList);
            req.getRequestDispatcher("/mypage/list.jsp").forward(req, resp);

        } catch (Exception e){
            log.error(e.getMessage());
            throw new ServletException("list error");
        }
    }
}

