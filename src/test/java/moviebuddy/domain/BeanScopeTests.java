package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopeTests {
    @Test
    @DisplayName("Singleton Scope Test")
    void equals_MovieFinderBean() {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(MovieBuddyFactory.class);
        MovieFinder movieFinder = applicationContext.getBean(MovieFinder.class);
        Assertions.assertEquals(movieFinder, applicationContext.getBean(MovieFinder.class));
    }
}
