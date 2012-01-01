package heroclipse;

import heroclipse.utils.HeroclipsePreferencesUtil;

import java.util.HashMap;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.osgi.service.prefs.Preferences;

import com.heroku.api.HerokuAPI;

//import com.heroku.api.HerokuAPI;

public class HerokuPreferencesPagev3 extends PreferencePage implements IWorkbenchPreferencePage{
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;

	/**
	 * Create the preference page.
	 */
	public HerokuPreferencesPagev3() {
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("Heroclipse", "icons/heroku_logo.jpg"));
		setTitle("Heroku");
	}

	/**
	 * Create contents of the preference page.
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(3, false));
		
		Label lblEmail = new Label(container, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmail.setText("Email:");
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(container, SWT.NONE);
		
		Label lblPassword = new Label(container, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassword.setText("Password:");
		
		text_1 = new Text(container, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_1.setEchoChar('*');
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String apiKey = HerokuAPI.obtainApiKey(text.getText(),text_1.getText());
				text_2.setText(apiKey);
				
			}
		});
		btnNewButton.setToolTipText("Click here to login to Heroku to fetch your API Key");
		btnNewButton.setText("Get API Key");
		
		Label lblApiKey = new Label(container, SWT.NONE);
		lblApiKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblApiKey.setText("API Key:");
		
		text_2 = new Text(container, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnValidate = new Button(container, SWT.NONE);
		btnValidate.setText("Validate");
		
		Label lblSshKey = new Label(container, SWT.NONE);
		lblSshKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSshKey.setText("SSH Key:");
		
		text_3 = new Text(container, SWT.BORDER);
		GridData gd_text_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_text_3.minimumHeight = 20;
		text_3.setLayoutData(gd_text_3);
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, false, 3, 1));
		GridLayout gl_composite = new GridLayout(3, false);
		gl_composite.marginHeight = 0;
		gl_composite.marginWidth = 0;
		gl_composite.verticalSpacing = 3;
		composite.setLayout(gl_composite);
		
		Button btnGenerate = new Button(composite, SWT.NONE);
		btnGenerate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnGenerate.setText("Generate..");
		
		Button btnAddUpdate = new Button(composite, SWT.NONE);
		btnAddUpdate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnAddUpdate.setText("Update");
		
		Button btnRemove = new Button(composite, SWT.NONE);
		btnRemove.setText("Remove");
		initializePreferencesFromStore();
		return container;
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void performApply() {
		performOk();
	}

	@Override
	public boolean performOk() {
		HashMap<String,String> prefs = new HashMap<String,String>();
		prefs.put("email", text.getText());
		prefs.put("apiKey",text_2.getText());
		return HeroclipsePreferencesUtil.savePreferences(prefs);
	}
	
	private void initializePreferencesFromStore(){
		/*
		 * The way to store Preferences has changed in Indigo
		 * Directly constructing the ConfigruationScope has been deprecated.
		 */
		@SuppressWarnings("deprecation")
		ConfigurationScope configScope = new ConfigurationScope();
		Preferences preferences = configScope.getNode(Activator.PLUGIN_ID);
		Preferences herokuPrefs = preferences.node("heroku");
		text.setText(herokuPrefs.get("email", ""));
		text_2.setText(herokuPrefs.get("apiKey", ""));

	}
}
