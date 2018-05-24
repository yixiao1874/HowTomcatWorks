package exercise.ex02;

import javax.servlet.Servlet;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;

public class ResourceProcessor {
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator  + "webroot";

    private Request request;
    private Response response;

    public ResourceProcessor(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public ResourceProcessor(){}

    public void process(Request request, Response response){
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        if (uri.startsWith("/servlet/")){
            //说明是一个servlet请求，加载对应servlet
            URL[] urls = new URL[1];
            File classPath = new File(WEB_ROOT);
            try {
                urls[0] = new URL("file:"+classPath+"\\");
                URLClassLoader urlClassLoader = new URLClassLoader(urls);
                Class myClass = urlClassLoader.loadClass(servletName);
                Servlet servlet = (Servlet) myClass.newInstance();
                servlet.service(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //处理静态资源
            PrintWriter printWriter = null;
            try {
                printWriter = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = new byte[1024];
            FileInputStream fis = null;
            try {
                File file = new File(WEB_ROOT, request.getUri());
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, 1024);
                while (ch!=-1) {
                    printWriter.print(ch);
                    ch = fis.read(bytes, 0, 1024);
                }
            }catch (FileNotFoundException e){
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                printWriter.print(errorMessage);
            }catch (IOException e){
                e.printStackTrace();
            }
            /*byte[] bytes = new byte[1024];
            FileInputStream fis = null;
            OutputStream outputStream = response.getOutputStream();
            try {
                File file = new File(WEB_ROOT, request.getUri());

                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, 1024);
                while (ch!=-1) {
                    outputStream.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, 1024);
                }
            }
            catch (FileNotFoundException e) {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                outputStream.write(errorMessage.getBytes());
            }catch (IOException e){
                e.printStackTrace();
            }
            finally {
                if (fis!=null)
                    fis.close();
            }*/
        }
    }
}
