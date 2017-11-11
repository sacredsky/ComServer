/* File server interface */
/* by Jia Rao */
import java.io.FileNotFoundException;
import java.rmi.*;

public interface ComServer extends Remote
{
  public int GetClock() throws RemoteException;
  public void SetClock(int newclock) throws RemoteException;
  public void SendMessage(MessageInfo messageif) throws RemoteException;
}
