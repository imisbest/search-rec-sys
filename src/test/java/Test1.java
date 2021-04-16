import com.csw.Application;
import com.csw.dao.PoemDao;
import com.csw.entity.Poem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)  //==@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class Test1 {
    @Autowired
    private PoemDao poemDao;

    @Test
    public void testqueryAll() {
        List<Poem> emps = poemDao.findAll();
        emps.forEach(e -> System.out.println("/e/" + e));
    }

    @Test
    public void testcount() {
        Long totalCounts = poemDao.findTotalCounts();
        System.out.println(totalCounts);
    }

    @Test
    public void uuid() {
//        D:\IdeaProjects\RUN\bootstrap-And-Jqgrid
        /*5c2764b4-8cf8-490b-aaa6-e603c67b562d
         * @c2764b48cf8490baaa6e603c67b562d*/
        System.out.println(UUID.randomUUID());
    }
}
