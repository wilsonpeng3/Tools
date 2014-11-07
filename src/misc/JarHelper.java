package misc;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarHelper {

    public static void scanJar(JarFile jarFile, EntryHandler handler) throws IOException {
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            handler.handle(entry);
        }
    }

    public interface EntryHandler {
        public void handle(JarEntry entry);
    }
}
