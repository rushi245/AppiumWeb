import java.io.*;

import java.net.Socket;

import java.net.SocketException;

import java.net.UnknownHostException;
import java.util.Scanner;



public class AppuimClient {

private Socket socket = null;

private InputStream inStream = null;

private OutputStream outStream = null;

String typedMessage = null;

Scanner sc;


public AppuimClient() {

}



public Boolean createSocket(String ipAddress) {

    try {
    	
    	AppuimClient ac = new AppuimClient();
    	ac.createSocket("localhost");

//    	sc = new Scanner(System.in);
    	
        socket = new Socket(ipAddress, 25007);

        System.out.println("Connected");

        inStream = socket.getInputStream();

        outStream = socket.getOutputStream();

        createReadThread();

        createWriteThread();
        
        typedMessage = "Hello";
        
        return true;

    } catch (UnknownHostException u) {

        u.printStackTrace();
        return false;

    } catch (IOException io) {

        io.printStackTrace();
        return false;

    }

}



public void createReadThread() {

    Thread readThread = new Thread() {

        public void run() {

            while (socket.isConnected()) {

                try {

                    byte[] readBuffer = new byte[200];

                    int num = inStream.read(readBuffer);



                    if (num > 0) {

                        byte[] arrayBytes = new byte[num];

                        System.arraycopy(readBuffer, 0, arrayBytes, 0, num);

                        String recvedMessage = new String(arrayBytes, "UTF-8");

                        System.out.println("Received message :" + recvedMessage);
                        
                        if( recvedMessage.contains("Start App") ){
//                        	typedMessage = "Starting App";
                        	Thread.sleep(5000);
                        	
                        	System.out.println("Enter the port no. : ");
                        	
                        	String port = sc.next();
                        	
//                        	TataConnect tc = new TataConnect("10.0.7.43",port);
                        	
//                        	tc.start();
                        	
                        }
                        else if( recvedMessage.contains("Hello") ){
                        	Thread.sleep(100);
                        	typedMessage = "Start Server Port: 4723 BPPort: 4002";
                        }
                        
                    }/* else {

                        // notify();

                    }*/
                    ;
                    //System.arraycopy();

                }catch (SocketException se){

                    System.exit(0);

                } catch (IOException i) {

                    i.printStackTrace();

                } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }

        }

    };

    readThread.setPriority(Thread.MAX_PRIORITY);

    readThread.start();

}

public void createWriteThread() {

    Thread writeThread = new Thread() {

        public void run() {

            while (socket.isConnected()) {

                try {

                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

                    sleep(100);

                    String typedMessage = inputReader.readLine();

                    if (typedMessage != null && typedMessage.length() > 0) {

                        synchronized (socket) {

                            outStream.write(typedMessage.getBytes("UTF-8"));

                            sleep(100);
                            
                            typedMessage = null;

                        }

                    }

                    ;

                    //System.arraycopy();

                } catch (IOException i) {

                    i.printStackTrace();

                } catch (InterruptedException ie) {

                    ie.printStackTrace();

                }

            }

        }

    };

    writeThread.setPriority(Thread.MAX_PRIORITY);

    writeThread.start();

}


}