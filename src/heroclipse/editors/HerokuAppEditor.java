package heroclipse.editors;





import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.*;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.ide.IDE;

import com.heroku.api.HerokuAPI;


public class HerokuAppEditor extends MultiPageEditorPart implements IResourceChangeListener{

	public static final String ID = "heroclipse.editors.HerokuAppEditor";
	public HerokuAppEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}
	/**
	 * Creates page 0 of the multi-page editor,
	 * which contains a text editor.
	 */
	void createAppInfoPage() {
		AppInfoEditor appInfo = new AppInfoEditor();

		int index=-1;
		try {
			index = addPage(appInfo,getEditorInput());
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPageText(index, "App Info");
	}
	/**
	 * Creates page 1 of the multi-page editor,
	 * which allows you to change the font used in page 2.
	 */
	void createCollaboratorPage() {
		AppCollaboratorEditor collaborators = new AppCollaboratorEditor();
		int index=-1;
		try {
			index = addPage(collaborators,getEditorInput());
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPageText(index, "Collaborators");

	}
	void createEnvironmentVariablesPage() {
		AppEnviromentEditor environment = new AppEnviromentEditor();
		int index=-1;
		try {
			index = addPage(environment,getEditorInput());
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPageText(index, "Environment Variables");

	}
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createAppInfoPage();
		createCollaboratorPage();
		createEnvironmentVariablesPage();
		setPartName(getEditorInput().getName()+".app");
	}
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
	
	}
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
		
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
		if (!(editorInput instanceof HerokuAppEditorInput))
			throw new PartInitException("Invalid Input: Must be Heroku App Type");
		intializeApplicationInfo((HerokuAppEditorInput)editorInput);
		super.init(site, editorInput);
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return false;
	}
	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	protected void pageChange(int newPageIndex) {
		
	}
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private void intializeApplicationInfo(HerokuAppEditorInput appInput) {
		//HerokuAPI api =new HerokuAPI(HeroclipsePreferencesUtil.getPreferenceValue("apiKey"));
		HerokuAPI api = new HerokuAPI("f4e237dbf5747df56dc0b9e7c056e3bb2f7d05d5");
		appInput.setCollaborators(api.listCollaborators(appInput.getName()));
		appInput.setConfigVariables(api.listConfig(appInput.getName()));
		appInput.setReleases(api.listReleases(appInput.getName()));
		appInput.setAddons(api.listAppAddons(appInput.getName()));
		
	}
}
