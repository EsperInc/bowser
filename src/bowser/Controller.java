package bowser;

import static ox.util.Utils.getExtension;
import java.net.URL;
import java.util.Map;
import com.google.common.collect.Maps;

public abstract class Controller {

  private WebServer server;
  private final Map<String, String> folders = Maps.newHashMap();

  public final void init(WebServer server) {
    this.server = server;
    init();
  }

  public abstract void init();

  protected Route route(String method, String path) {
    Route ret = new Route(this, method, path, !server.developerMode);
    server.add(ret);
    return ret;
  }

  public WebServer getServer() {
    return server;
  }

  public void mapFolders(String... extensions) {
    for (String s : extensions) {
      mapFolder(s, s);
    }
  }

  public void mapFolder(String extension, String folder) {
    folders.put(extension, folder);
  }

  public URL getResource(String path) {
    String folder = folders.get(getExtension(path));
    if (folder != null) {
      path = folder + "/" + path;
    }
    return getClass().getResource(path);
  }

}
