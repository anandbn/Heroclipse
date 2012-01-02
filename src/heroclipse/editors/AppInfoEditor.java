package heroclipse.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

public class AppInfoEditor extends EditorPart {

	public static final String ID = "heroclipse.editors.AppInfoEditor"; //$NON-NLS-1$
	private Text text_1;
	private HerokuAppEditorInput appInput;
	public AppInfoEditor() {
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		GridLayout gl_parent = new GridLayout(1, false);
		gl_parent.horizontalSpacing = 0;
		parent.setLayout(gl_parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		new Label(composite, SWT.NONE);
		
		Label lblName = new Label(composite, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name:");
		appInput = (HerokuAppEditorInput) getEditorInput();
		
		setPartName(String.format("%s ( %s )",appInput.getName(),appInput.getHerokuApp().getGitUrl()));
		Composite composite_3 = new Composite(composite, SWT.NONE);
		composite_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		text_1 = new Text(composite_3, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		text_1.setBounds(0, 5, 312, 19);
		text_1.setText(appInput.getHerokuApp().getName());
		
		Button btnNewButton_3 = new Button(composite_3, SWT.NONE);
		btnNewButton_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		btnNewButton_3.setBounds(318, 1, 89, 28);
		btnNewButton_3.setText("Rename");
		
		new Label(composite, SWT.NONE);
		
		Label lblUrl = new Label(composite, SWT.NONE);
		lblUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUrl.setText("URL");
		
		Label lblHttpmyappherokuappcom = new Label(composite, SWT.NONE);
		lblHttpmyappherokuappcom.setText(appInput.getHerokuApp().getWebUrl());
		new Label(composite, SWT.NONE);
		
		Label lblGitUrl = new Label(composite, SWT.NONE);
		lblGitUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGitUrl.setAlignment(SWT.RIGHT);
		lblGitUrl.setText("Git Repository URL:");
		
		Label lblGitmyappherokuappcom = new Label(composite, SWT.NONE);
		lblGitmyappherokuappcom.setText(appInput.getHerokuApp().getGitUrl());
		new Label(composite, SWT.NONE);
		
		Label lblDomainName = new Label(composite, SWT.NONE);
		lblDomainName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDomainName.setAlignment(SWT.RIGHT);
		lblDomainName.setText("Domain Name:");
		new Label(composite, SWT.NONE);
		
		// Set the sorter for the table

		

	}
	

	@Override
	public void setFocus() {
		// Set the focus
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// Do the Save operation
	}

	@Override
	public void doSaveAs() {
		// Do the Save As operation
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		appInput =(HerokuAppEditorInput)input;
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	
	

}
