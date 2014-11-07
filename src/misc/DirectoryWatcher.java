package misc;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * 默认会监听子孙目录
 */
public final class DirectoryWatcher {

    private Path watchRoot;
    private boolean recursive = true;
    private EventHandler handler;
    private ConcurrentHashMap<WatchKey, Path> keys = new ConcurrentHashMap<>();

    /**
     * @param watchRoot 监视的跟路径
     * @param recursive 是否监听子孙目录
     * @param handler   事件处理
     */
    public DirectoryWatcher(Path watchRoot, boolean recursive, EventHandler handler) {
        this.watchRoot = watchRoot;
        this.recursive = recursive;
        this.handler = handler;
    }

    public DirectoryWatcher(Path watchRoot, EventHandler handler) {
        this.watchRoot = watchRoot;
        this.handler = handler;
    }

    private void register(Path watchRoot, boolean recursive, WatchService watcher) throws IOException {
        if (!recursive) {
            register(watchRoot, watcher);
        } else {
            registerChilds(watchRoot, watcher);
        }
    }

    private void registerChilds(Path directoryFather, WatchService watchService) throws IOException {
        register(directoryFather, watchService);
        File file = directoryFather.toFile();
        File[] files = file.listFiles(pathname -> pathname.isDirectory());
        for (File tmp : files) {
            registerChilds(tmp.toPath(), watchService);
        }
    }

    private void register(Path directory, WatchService watchService) throws IOException {
        WatchKey key = directory.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, directory);
    }


    public void watch() throws IOException, InterruptedException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        register(watchRoot, recursive, watcher);
        while (true) {
            WatchKey watchKey = watcher.take();
            Path parent = keys.get(watchKey);
            List<WatchEvent<?>> events = watchKey.pollEvents();
            for (WatchEvent<?> event : events) {
                WatchEvent<Path> tmp = (WatchEvent<Path>) event;
                WatchEvent.Kind kind = tmp.kind();
                Path curr = parent.resolve(tmp.context());
                if (kind == ENTRY_CREATE) {
                    handler.create(parent, tmp.context());
                    if (curr.toFile().isDirectory() && recursive) {
                        register(curr, false, watcher);
                    }
                } else if (kind == ENTRY_DELETE) {
                    handler.delete(parent, tmp.context());
                } else if (kind == ENTRY_MODIFY) {
                    handler.modify(parent, tmp.context());
                } else if (kind == OVERFLOW) {
                    // nothing
                }
            }
            boolean valid = watchKey.reset();
            if (!valid) {
                if (keys.containsKey(watchKey)) {
                    keys.remove(watchKey);
                }
            }
        }
    }

    public interface EventHandler {
        public void create(Path parent, Path child);

        public void delete(Path parent, Path child);

        public void modify(Path parent, Path child);
    }
}
