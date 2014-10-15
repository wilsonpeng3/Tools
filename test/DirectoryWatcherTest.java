import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryWatcherTest {

    @org.junit.Test
    public void testWatch() throws Exception {
        DirectoryWatcher directoryWatcher = new DirectoryWatcher(Paths.get("C:\\Users\\PC\\Desktop\\pqs"), true, new DirectoryWatcher.EventHandler() {
            @Override
            public Object create(Path parent, Path fileName) {
                System.out.println("create  "+ parent.resolve(fileName));
                return null;
            }

            @Override
            public Object delete(Path parent, Path fileName) {
                System.out.println("delete  " +parent.resolve(fileName));
                return null;
            }

            @Override
            public Object modify(Path parent, Path fileName) {
                System.out.println("modify  " +parent.resolve(fileName));
                return null;
            }
        });
        directoryWatcher.watch();
    }
}