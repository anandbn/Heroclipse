package heroclipse.editors;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.heroku.api.Collaborator;


public class CollaboratorEmailEditingSupport extends EditingSupport {

		private final TableViewer viewer;

		public CollaboratorEmailEditingSupport(TableViewer viewer) {
			super(viewer);
			this.viewer = viewer;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return new TextCellEditor(viewer.getTable());
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			return ((Collaborator) element).getEmail();
		}

		@Override
		protected void setValue(Object element, Object value) {
			((Collaborator) element).setEmail(String.valueOf(value));
			viewer.refresh();
		}
}
	