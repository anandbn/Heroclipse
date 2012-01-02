package heroclipse.editors;

import java.util.HashMap;
import heroclipse.Activator;
import heroclipse.utils.HeroclipsePreferencesUtil;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import com.heroku.api.Collaborator;
import com.heroku.api.HerokuAPI;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class AppInfoEditorMaster extends EditorPart {

	public static final String ID = "heroclipse.editors.AppInfoEditor"; //$NON-NLS-1$
	private Table table;
	private Text text_1;
	private TableViewer viewer;
	private HerokuAppEditorInput appInput;
	public AppInfoEditorMaster() {
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
		
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_tabFolder.widthHint = 573;
		tabFolder.setLayoutData(gd_tabFolder);
		
		TabItem tbtmInfo = new TabItem(tabFolder, SWT.NONE);
		tbtmInfo.setText("Info");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmInfo.setControl(composite);
		composite.setLayout(new GridLayout(3, false));
		new Label(composite, SWT.NONE);
		
		Label lblName = new Label(composite, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name:");
		appInput = (HerokuAppEditorInput) getEditorInput();
		
		intializeApplicationInfo(appInput);
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
		lblHttpmyappherokuappcom.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.BOLD));
		new Label(composite, SWT.NONE);
		
		Label lblGitUrl = new Label(composite, SWT.NONE);
		lblGitUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGitUrl.setAlignment(SWT.RIGHT);
		lblGitUrl.setText("Git Repository URL:");
		
		Label lblGitmyappherokuappcom = new Label(composite, SWT.NONE);
		lblGitmyappherokuappcom.setText(appInput.getHerokuApp().getGitUrl());
		lblGitmyappherokuappcom.setFont(SWTResourceManager.getFont("Lucida Grande", 11, SWT.BOLD));
		new Label(composite, SWT.NONE);
		
		Label lblDomainName = new Label(composite, SWT.NONE);
		lblDomainName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDomainName.setAlignment(SWT.RIGHT);
		lblDomainName.setText("Domain Name:");
		new Label(composite, SWT.NONE);
		
		
		TabItem tbtmCollaborators = new TabItem(tabFolder, SWT.NONE);
		tbtmCollaborators.setText("Collaborators");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmCollaborators.setControl(composite_1);
		composite_1.setLayout(new GridLayout(4, false));
		
		viewer = new TableViewer(composite_1, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		createColumns(parent, viewer);
		
		final Table tblCollaborators = viewer.getTable();
		tblCollaborators.setHeaderVisible(true);
		tblCollaborators.setLinesVisible(true);

		
		viewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(appInput.getCollaborators());
		
		// Set the sorter for the table

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
		
		
		
		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		composite_2.setLayout(new GridLayout(1, false));
		Button btnNewButton = new Button(composite_2, SWT.NONE);
		btnNewButton.setText("+");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Collaborator c = new Collaborator();
				c.setEmail("New Collaborator Email");
				appInput.getCollaborators().add(c);
				viewer.refresh();
			}
		});
		Button btnNewButton_1 = new Button(composite_2, SWT.NONE);
		btnNewButton_1.setText("-");
		
		Button btnSave = new Button(composite_2, SWT.NONE);
		btnSave.setText("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				HerokuAPI api = new HerokuAPI(HeroclipsePreferencesUtil.getPreferenceValue("apiKey"));
				HashMap<String,Collaborator> currentCollaborators = new HashMap<String,Collaborator>();
				for(Collaborator collaborator:api.listCollaborators(appInput.getName())){
					currentCollaborators.put(collaborator.getEmail(), collaborator);
					
				}
				for(Collaborator collaborator:appInput.getCollaborators()){
					if(!currentCollaborators.containsKey(collaborator.getEmail())){
						api.addCollaborator(appInput.getName(),collaborator.getEmail());
					}
				}
				viewer.refresh();
			}
		});
		
		if(!appInput.getHerokuApp().getOwnerEmail().equalsIgnoreCase(HeroclipsePreferencesUtil.getPreferenceValue("email"))){
			Button btnNewButton_2 = new Button(composite_2, SWT.NONE);
			btnNewButton_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					TableItem selItem = tblCollaborators.getSelection()[0];
					String ownerEmail = selItem.getText(1);
					HerokuAPI api = new HerokuAPI(HeroclipsePreferencesUtil.getPreferenceValue("apiKey"));
					api.transferApp(appInput.getName(), ownerEmail);
					appInput.setCollaborators(api.listCollaborators(appInput.getName()));
					viewer.refresh();
				}
			});
			btnNewButton_2.setText("Make Owner");
			
		}
		
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		TabItem tbtmEnvironmentConfiguration = new TabItem(tabFolder, SWT.NONE);
		tbtmEnvironmentConfiguration.setText("Environment Variables");
		
		Composite composite_4 = new Composite(tabFolder, SWT.NONE);
		tbtmEnvironmentConfiguration.setControl(composite_4);
		composite_4.setLayout(new GridLayout(4, false));
		
		table = new Table(composite_4, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn varName = new TableColumn(table, SWT.NONE);
		varName.setWidth(100);
		varName.setText("Key");
		
		TableColumn varValue = new TableColumn(table, SWT.NONE);
		varValue.setWidth(300);
		varValue.setText("Value");
		new Label(composite_4, SWT.NONE);
		
		Composite composite_5 = new Composite(composite_4, SWT.NONE);
		composite_5.setLayout(new GridLayout(1, false));
		Button btnNewButton_4 = new Button(composite_5, SWT.NONE);
		btnNewButton_4.setText("+");
		
		Button btnNewButton_5 = new Button(composite_5, SWT.NONE);
		btnNewButton_5.setText("-");
		new Label(composite_4, SWT.NONE);
		
		
		TabItem tbtmProcesses = new TabItem(tabFolder, SWT.NONE);
		tbtmProcesses.setText("Processes");
		
		TabItem tbtmAddons = new TabItem(tabFolder, SWT.NONE);
		tbtmAddons.setText("Addons");
		
		TabItem tbtmReleases = new TabItem(tabFolder, SWT.NONE);
		tbtmReleases.setText("Releases");

	}
	
	public TableViewer getViewer() {
		return viewer;
	}

	// This will create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Owner", "Collaborator Email"};
		int[] bounds = { 50, 200};

		// First column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public Image getImage(Object element) {
				Collaborator p = (Collaborator) element;
				Boolean isOwner = appInput.getHerokuApp().getOwnerEmail().equalsIgnoreCase(p.getEmail());
				if(isOwner){
					return Activator.getImageDescriptor("icons/appowner.gif").createImage();
				}else{
					return null;
				}
				
			}
			public String getText(Object element) {
				return null;
			}
		});

		// Second column is for the last name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Collaborator p = (Collaborator) element;
				return p.getEmail();
			}
		});
		col.setEditingSupport(new CollaboratorEmailEditingSupport(viewer));

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;

	}	

	private void intializeApplicationInfo(HerokuAppEditorInput appInput) {
		HerokuAPI api =new HerokuAPI(HeroclipsePreferencesUtil.getPreferenceValue("apiKey"));
		//HerokuAPI api = new HerokuAPI("f4e237dbf5747df56dc0b9e7c056e3bb2f7d05d5");
		appInput.setCollaborators(api.listCollaborators(appInput.getName()));
		appInput.setConfigVariables(api.listConfig(appInput.getName()));
		appInput.setReleases(api.listReleases(appInput.getName()));
		appInput.setAddons(api.listAppAddons(appInput.getName()));
		
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
