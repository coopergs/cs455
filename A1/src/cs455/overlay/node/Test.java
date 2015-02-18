package cs455.overlay.node;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import cs455.overlay.transport.TCPSender;
import cs455.overlay.wireformats.OverlayNodeSendsData;

public class Test {
	
	public static void main(String[] args){
		try {
			TCPSender sender = new TCPSender(new Socket("192.168.1.118", 58643));
			int[] a = {0};
			OverlayNodeSendsData r = new OverlayNodeSendsData(0,0,1234,a);
			sender.sendData(r.getBytes());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
