package heroclipse.utils;

import java.util.Map;
import java.util.Map.Entry;
import heroclipse.Activator;

import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.eclipse.core.runtime.preferences.InstanceScope;

public class HeroclipsePreferencesUtil {

	public static String getPreferenceValue(String key){
		Preferences preferences = getPreferenceStore();
		// This would be using default n scope
		// Preferences preferences = new DefaultScope()
		// .getNode(Application.PLUGIN_ID);
		//Preferences sub1 = preferences.node("heroku");
		String prefVal = preferences.get(key, null);
		if(prefVal==null){
			String defaultValue = ">>";
			defaultValue += "Preferences {";
			@SuppressWarnings("deprecation")
			InstanceScope configScope = new InstanceScope();
			defaultValue += String.format("configScope=%s,Scope Location:%s,", configScope,configScope.getLocation());
			defaultValue += String.format("Preference Location=%s,",configScope.getNode(Activator.PLUGIN_ID).absolutePath());
			try {
				defaultValue += String.format(",Total Keys= %s [",preferences.keys().length);
				for (String prefKey : preferences.keys()) {
					defaultValue += String.format(" {%s = %s},", prefKey,
							preferences.get(prefKey, "<DEF>"));
				}
			} catch (BackingStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				defaultValue += "{Exception}";
			}
			defaultValue += "] }";
			return defaultValue;
		}else{
			return preferences.get(key, null);
		}
	}
	
	public static boolean savePreferences(Map<String,String> prefs){
		Preferences preferences = getPreferenceStore();
		for(Entry<String, String> pref:prefs.entrySet()){
			preferences.put(pref.getKey(), pref.getValue());
		}
		try {
			// Forces the application to save the preferences
			preferences.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static Preferences getPreferenceStore(){
		@SuppressWarnings("deprecation")
		InstanceScope configScope = new InstanceScope();
		
		Preferences preferences = configScope.getNode(Activator.PLUGIN_ID);
		// This would be using default n scope
		// Preferences preferences = new DefaultScope()
		// .getNode(Application.PLUGIN_ID);
		return preferences;
	}
}
