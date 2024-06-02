package org.webserver.httpcore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpMainThread extends Thread {

    private final ServerSocket serverSocket;
    private final int port;
    private final String webroot;

    public HttpMainThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        System.out.println("Http server is listening on port " + port);
        try {
            while(serverSocket.isBound()){
                Socket socket = serverSocket.accept();
                WorkerThread workerThread = new WorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
