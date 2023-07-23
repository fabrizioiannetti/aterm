package terminal;

public class Platform {

	public static final String OS_WIN32 = "win32";
	public static final String OS_LINUX = "linux";

	public static String getDebugOption(String strOption) {
		return "";
	}

	public static Object getOS() {
		return OS_LINUX;
	}

	public static boolean getDebugBoolean(String string) {
		// TODO Auto-generated method stub
		return false;
	}

}
