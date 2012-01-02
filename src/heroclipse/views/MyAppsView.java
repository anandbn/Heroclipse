package heroclipse.views;


import heroclipse.editors.HerokuAppEditor;
import heroclipse.editors.HerokuAppEditorInput;
import java.util.List;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import com.heroku.api.App;
import com.heroku.api.HerokuAPI;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class MyAppsView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "heroclipse.views.MyAppsView";

	private TableViewer viewer;
	//private Action action1;
	//private Action action2;
	/*private List<Action> pluginActions;
	private static List<String> myAppActions;*/
	private Action doubleClickAction;
	private TableColumn appNameColumn;
	private TableColumn gitUrlColumn;
	private TableColumn typeColumn;
	private TableColumn appUrlColumn;
	
	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			//String apiKey = HeroclipsePreferencesUtil.getPreferenceValue("apiKey");
			String apiKey = "f4e237dbf5747df56dc0b9e7c056e3bb2f7d05d5";
			HerokuAPI api = new HerokuAPI(apiKey);
			List<App> myApps = api.listApps();
			
			return myApps.toArray();
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			switch (index) {
			case 0: // Type column
				return "";
			case 1: // Name column
				if (obj instanceof App)
					return ((App) obj).getName();
				if (obj != null)
					return obj.toString();
				return "";
			case 2: // gitUrl
				if (obj instanceof App)
					return ((App) obj).getGitUrl();
				return "";
			case 3: // appUrl
				if (obj instanceof App)
					return ((App) obj).getWebUrl();
				return "";
			default:
				return "";
			}
		}

		public Image getColumnImage(Object obj, int index) {
			return null;
		}

	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public MyAppsView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		final Table table = viewer.getTable();
		table.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		typeColumn = new TableColumn(table, SWT.LEFT);
		typeColumn.setText("");
		typeColumn.setWidth(18);

		appNameColumn = new TableColumn(table, SWT.LEFT);
		appNameColumn.setText("Name");
		appNameColumn.setWidth(153);

		gitUrlColumn = new TableColumn(table, SWT.LEFT);
		gitUrlColumn.setText("Git Url");
		gitUrlColumn.setWidth(200);
		
		appUrlColumn = new TableColumn(table, SWT.LEFT);
		appUrlColumn.setText("App Url");
		appUrlColumn.setWidth(200);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());		
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());


		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "Heroclipse.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		
		Menu menu = menuMgr.createContextMenu(viewer.getTable());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
		getSite().setSelectionProvider(viewer);

	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		/*for(Action action:pluginActions){
			if(	action.getText().equalsIgnoreCase("Refresh")){
					manager.add(action);
			}
			
		}*/
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		/*for(Action action:pluginActions){
			if(	action.getText().equalsIgnoreCase("Refresh") ){
						manager.add(action);
						manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			
				}
		}*/
	}

	private void makeActions() {
		/*pluginActions = new ArrayList<Action>();
		Action action;
		for(final String actionLabel: myAppActions){
			action = new Action(){
				public void run(){
					showMessage(String.format("Executing %s...",actionLabel));
				}
			};
			action.setText(actionLabel);
			action.setToolTipText(String.format("Tooltip for %s",actionLabel));
			action.setImageDescriptor(Activator.getImageDescriptor("icons/"+actionLabel.toLowerCase()+".gif"));
			pluginActions.add(action);
		}*/
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				IWorkbenchPage myAppsViewPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					//myAppsViewPage.openEditor(new HerokuAppEditorInput((App)obj), AppInfoEditor.ID);
					myAppsViewPage.openEditor((IEditorInput)new HerokuAppEditorInput((App)obj), HerokuAppEditor.ID);
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}