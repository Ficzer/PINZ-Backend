package controller_test;

import com.filipflorczyk.pinzbackend.Application;
import com.filipflorczyk.pinzbackend.controllers.UserController;
import com.filipflorczyk.pinzbackend.dtos.UserDto;
import com.filipflorczyk.pinzbackend.services.interfaces.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.filipflorczyk.pinzbackend.controllers.*")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UserController userController = mock(UserController.class);

    @Test
    public void shouldReturnOkAndListOfConferenceRooms_WhenGet() throws Exception {

        doReturn(findAllMocked()).when(userController, "addSelfLinksToPageContent", ArgumentMatchers.any(Page.class));

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"userName\"")))
                .andExpect(content().string(containsString(" \"_links\": {\n" +
                        "        \"self\": {\n" +
                        "            \"href\": \"http://localhost:8080/users\"\n" +
                        "        }\n" +
                        "    },")))
                .andExpect(content().string(containsString("\"page\": {\n" +
                        "        \"size\": 20,\n")));

        // User PowerMockito
    }

    private Page<UserDto> findAllMocked(){

        UserDto user1 = new UserDto();
        user1.setUserName("Filip");

        UserDto user2 = new UserDto();
        user2.setUserName("Flap");

        UserDto user3 = new UserDto();
        user3.setUserName("Kacper");

        List<UserDto> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        return new PageImpl<>(userList, PageRequest.of(0, 20), 3);
    }
}
