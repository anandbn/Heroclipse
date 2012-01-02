package heroclipse.editors;

import java.util.HashMap;
import java.util.Map;

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

public class AppEnviromentEditor extends EditorPart {

	public static final String ID = "heroclipse.editors.AppEnviromentEditor"; //$NON-NLS-1$
	private Table table;
	private Text text_1;
	private TableViewer viewer;
	private HerokuAppEditorInput appInput;
	public AppEnviromentEditor() {
	}

	/**
	 * Create contents of the editor part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite composite_1 = new Composite(parent, SWT.NONE);
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
		viewer.setInput(appInput.getConfigVariables().entrySet());
		
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
		

	}
	
	public TableViewer getViewer() {
		return viewer;
	}

	// This will create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Key", "Value"};
		int[] bounds = { 200, 200};

		// First column is for the first name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			
			@SuppressWarnings("unchecked")
			public String getText(Object element) {
				return ((Map.Entry<String,String>)element).getKey();
			}
		});

		// Second column is for the last name
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@SuppressWarnings("unchecked")
			@Override
			public String getText(Object element) {
				return ((Map.Entry<String,String>)element).getValue();
			}
		});

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
