import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClientLoader extends Applet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4744673981053459832L;
	public static final boolean useRsa = false;
	public static final boolean USEISAAC = false;
	public static Properties props = new Properties();
	public JFrame frame;
	private JPanel jp = new JPanel();
	public static String world;

	public static final String ADDRESS = "127.0.0.1";
	public static final int WLPORT = 43594; //43595
	//why is this 43594 if comment says 43595 ^
	public static void main(String[] paramArrayOfString)
	{
		System.getProperties().put("sun.java2d.noddraw", "true"); //Fixes fullscreen mode 
		new ClientLoader("1");
	}

	public ClientLoader(String wld) {
		try {
			ClientLoader.world = wld;
			this.frame = new JFrame("530 Client");
			this.frame.setLayout(new BorderLayout());
			this.frame.setResizable(true);
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.jp.setLayout(new BorderLayout());
			this.jp.add(this);
			this.jp.setPreferredSize(new Dimension(765, 503));
			this.frame.getContentPane().add(this.jp, "Center");
			this.frame.pack();
			this.frame.setVisible(true);
			props.put("worldid", wld);
			props.put("members", "1");
			props.put("modewhat", "1");
			props.put("modewhere", "0");
			props.put("safemode", "0");
			props.put("game", "0");
			props.put("js", "1");
			props.put("lang", "0");
			props.put("affid", "0");
			props.put("advertsuppressed", "1");
			props.put("lowmem", "0");
			props.put("settings", "kKmok3kJqOeN6D3mDdihco3oPeYN2KFy6W5--vZUbNA");
			Class87 sn = new Class87(this, 32, "arios", 29);
			client.providesignlink(sn);
			client localclient = new client();
			localclient.init();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
	public String getParameter(String paramString)
	{
		return ((String)props.get(paramString));
	}

	public URL getDocumentBase() {
		return getCodeBase();
	}

	public URL getCodeBase() {
		try {
			return new URL("http://" + ADDRESS);
			//return new URL("http://");
		} catch (MalformedURLException localException) {
			System.out.println("World List Loading might not be working or something else is wrong.");
			System.out.println("Stack Trace:");
			localException.printStackTrace();

			return null;
		}
	}
}