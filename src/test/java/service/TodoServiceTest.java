package service;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.repository.TodoRepository;
import springboot.service.TodoService;

import java.util.ArrayList;
import java.util.List;

public class TodoServiceTest {

    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.todoService = new TodoService(todoRepository);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.verifyNoMoreInteractions(todoRepository);
    }

    @Test
    public void getAllTest() throws Exception {
        //Expected result
        List<Todo> todos = new ArrayList<Todo>();
        todos.add(new Todo("Todo1", TodoPriority.MEDIUM));

        //given
        BDDMockito.given(todoRepository.getAll()).willReturn(todos);

        //when
        List<Todo> todoList = todoService.getAll();

        //then
        Assert.assertThat(todoList, Matchers.notNullValue());
        Assert.assertThat(todoList.isEmpty(), Matchers.equalTo(false));

        //verify
        BDDMockito.then(todoRepository).should().getAll();

    }

    @Test
    public void saveTodoTest() throws Exception {
        String name = "todo";
        TodoPriority todoPriority = TodoPriority.HIGH;

        BDDMockito.given(todoRepository.store(new Todo(name, todoPriority))).willReturn(true);

        Boolean result = todoService.saveTodo(name, todoPriority);
        Assert.assertThat(result, Matchers.equalTo(true));

        Mockito.verify(todoRepository).store(new Todo(name, todoPriority));
    }
}
