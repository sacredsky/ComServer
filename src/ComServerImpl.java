/* Implementation of server methods */
/* by Jia Rao */

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ThreadLocalRandom;


public class ComServerImpl implements ComServer
{
	private int myclock;
	public ComServerImpl() {
		myclock= ThreadLocalRandom.current().nextInt();
	}
	
	public int GetClock() throws RemoteException{
			return myclock;
	}
	public void SetClock(int newclock) throws RemoteException{
		myclock=newclock;
        System.out.println("Server new clock : " + myclock);
	}
	
	
	public void RecieveMessage(String myname) throws RemoteException{
		MessageInfo messageif = new MessageOp();
		try
		{
			//
			//
			//
			
			messageif.setInfo(myname, null);
		}
		catch(Exception e) {
			System.err.println("ComServer exception: "+ e.getMessage());
			e.printStackTrace();
		}
        
	}

	public void SendMessage(MessageInfo messageif) throws RemoteException{
		try
		{
			System.out.println("Recieved "+messageif.getFrom()+" : "+messageif.getContent());
		}
		catch(Exception e) {
			System.err.println("ComServer exception: "+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		if(args.length != 3) {
			System.out.println("Usage: java FileClient myname1 hostname2 hostname3");
			System.exit(0);
		}
		try
		{
			ComServerImpl ComServer = new ComServerImpl();
			ComServer stub = (ComServer) UnicastRemoteObject.exportObject(ComServer, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("COM-SERVER-"+args[0], stub);
            
            System.err.println("Server "+args[0]+" ready");
            System.out.println("Server init clock : " + ComServer.myclock);
			
			ComServer fs1 = null, fs2 = null;
			
			BufferedReader inFromUser = new BufferedReader (new InputStreamReader(System.in));
			String req = inFromUser.readLine();
			StringTokenizer st = new StringTokenizer(req);
			String cmd = st.nextToken();
			while(!cmd.equals("exit")) {
				if (cmd.equals("setclock")){
					int clock1=fs1.GetClock();
					int clock2=fs2.GetClock();
					int clockavg=(clock1+clock2+ComServer.myclock)/3;
					ComServer.SetClock(clockavg);
					fs1.SetClock(clockavg);
					fs2.SetClock(clockavg);
				}
				else if (cmd.equals("c")){			
					fs1 = (ComServer)registry.lookup("COM-SERVER-"+args[1]);
					fs2 = (ComServer)registry.lookup("COM-SERVER-"+args[2]);
				}
				else {
					System.out.println("Unknown Command " + cmd);
				}
				inFromUser = new BufferedReader (new InputStreamReader(System.in));
				req = inFromUser.readLine();
				st = new StringTokenizer(req);
				cmd = st.nextToken();
			}
            
		}

		catch (Exception re)
		{
			System.out.println("Remote exception: " + re.toString());
			re.printStackTrace();
		}
		
	}
}


