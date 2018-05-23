package exercise.ex02;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    public static void main(String[] args) {
    }

    private InputStream inputStream;
    private String uri;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void parse(){
        StringBuffer stringBuffer = new StringBuffer();
        byte[] bytes = new byte[1024];
        long length = 0;
        try {
            length = inputStream.read(bytes);
            if(length != -1){
                for(byte b:bytes){
                    stringBuffer.append((char)b);
                }
            }
            //System.out.println(stringBuffer.toString());
            //    解析GET /servlet HTTP/1.1
            uri = parseUri(stringBuffer.toString());
            System.out.println(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String parseUri(String requestString){
        //  将GET /servlet HTTP/1.1解析成   /servlet
        int index = requestString.indexOf(" ");
        if (index > 0){
            int index2 = requestString.indexOf(" ",index+1);
            if(index2 > index){
                return requestString.substring(index+1,index2);
            }
        }
        return null;
    }

}
