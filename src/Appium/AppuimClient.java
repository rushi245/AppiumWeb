package Appium;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;



public class AppuimClient {

private Socket socket = null;

private InputStream inStream = null;

private OutputStream outStream = null;

String typedMessage, output = null;

Scanner sc;

public AppuimClient() {

}

public boolean checkConnection(String ipaddress){
	try {
		socket = new Socket(ipaddress, 25007);
		
		return true;
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	
}

public String getDevices() throws UnsupportedEncodingException, IOException, InterruptedException{
//	return socket.getInetAddress().toString();
	typedMessage = "Device Info";
	output = "";
	createWrite();
	while(output.equals("")){

	}
	System.out.println("Output " +output);
	return output;
}

public boolean createSocket(String ipaddress) {

    try {

//    	sc = new Scanner(System.in);
    	
        socket = new Socket(ipaddress, 25007);

        System.out.println("Connected");

        inStream = socket.getInputStream();

        outStream = socket.getOutputStream();

        createReadThread();

//        createWriteThread();
        
//        typedMessage = "Hello";
        return true;

    } catch (UnknownHostException u) {

        u.printStackTrace();
        return false;

    } catch (Exception io) {

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
                        output = recvedMessage;
                        
                        if( recvedMessage.contains("Start App") ){
//                        	typedMessage = "Starting App";
                        	Thread.sleep(5000);
                        	
//                        	System.out.println("Enter the port no. : ");
                        	
//                        	String port = sc.next();
                        	
//                        	TataConnect tc = new TataConnect("10.0.7.43",port);
                        	
//                        	tc.start();
                        	
                        }
//                        else if( recvedMessage.contains("Hello") ){
//                        	Thread.sleep(100);
//                        	typedMessage = "Start Server Port: 4723 BPPort: 4002";
//                        }
                        
                    }/* else {

                        // notify();

                    }*/
                    ;
                    //System.arraycopy();

                }catch (SocketException se){

                    System.exit(0);
                    output = "Error";

                } catch (IOException i) {

                    i.printStackTrace();
                    output = "Error";

                } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					output = "Error";
				}

            }

        }

    };

    readThread.setPriority(Thread.MAX_PRIORITY);

    readThread.start();

}

public void createWrite() throws InterruptedException, UnsupportedEncodingException, IOException {


                  Thread.sleep(100);

                  if (typedMessage != null && typedMessage.length() > 0) {

                      synchronized (socket) {

                          outStream.write(typedMessage.getBytes("UTF-8"));

                          Thread.sleep(100);
                          
                          typedMessage = null;

                      }

                  }

                 

      }





//public void createWriteThread() {
//
//    Thread writeThread = new Thread() {
//
//        public void run() {
//
//            while (socket.isConnected()) {
//
//                try {
//
//                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
//
//                    sleep(100);
//
////                    String typedMessage = inputReader.readLine();
//
//                    if (typedMessage != null && typedMessage.length() > 0) {
//
//                        synchronized (socket) {
//
//                            outStream.write(typedMessage.getBytes("UTF-8"));
//
//                            sleep(100);
//                            
//                            typedMessage = null;
//
//                        }
//
//                    }
//
//                    ;
//
//                    //System.arraycopy();
//
//                } catch (IOException i) {
//
//                    i.printStackTrace();
//
//                } catch (InterruptedException ie) {
//
//                    ie.printStackTrace();
//
//                }
//
//            }
//
//        }
//
//    };
//
//    writeThread.setPriority(Thread.MAX_PRIORITY);
//
//    writeThread.start();
//
//}


}