package com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pojo.Todo;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private static final List<Todo> todos = new ArrayList<>();
    /* private static int todoCount = 3; */

    static {
        todos.add(new Todo(1, "jbk", "Learn Spring MVC", new Date(), false));
        todos.add(new Todo(2, "jbk", "Learn Struts", new Date(), false));
        todos.add(new Todo(3, "jbk", "Learn Hibernate", new Date(), false));
    }

    public List<Todo> retrieveTodos(String user) {
        List<Todo> filteredTodos = new ArrayList<>();
        for (Todo todo : todos) {
            if (todo.getUser().equals(user)) {
                filteredTodos.add(todo);
            }
        }
        return filteredTodos;
    }
}