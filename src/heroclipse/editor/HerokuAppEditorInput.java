package heroclipse.editor;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.heroku.api.*;

public class HerokuAppEditorInput implements IEditorInput {
	private App herokuApp;
	private List<Collaborator> collaborators;
	private Map<String,String> configVariables;
	private List<Addon> addons;
	private List<Release> releases;
	
	public HerokuAppEditorInput(App app){
		this.herokuApp = app;
	}

	public App getHerokuApp() {
		return herokuApp;
	}

	public List<Collaborator> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(List<Collaborator> collaborators) {
		this.collaborators = collaborators;
	}
	
	public boolean hasCollaborators(){
		return this.collaborators!=null || !this.collaborators.isEmpty();
	}

	public Map<String, String> getConfigVariables() {
		return configVariables;
	}

	public void setConfigVariables(Map<String, String> configVariables) {
		this.configVariables = configVariables;
	}

	public boolean hasConfigVariables(){
		return this.configVariables!=null || !this.configVariables.isEmpty();
	}
	public List<Addon> getAddons() {
		return addons;
	}

	public void setAddons(List<Addon> addons) {
		this.addons = addons;
	}
	
	public boolean hasAddons(){
		return this.addons!=null || !this.addons.isEmpty();
	}
	public List<Release> getReleases() {
		return releases;
	}

	public void setReleases(List<Release> releases) {
		this.releases = releases;
	}
	public boolean hasReleases(){
		return this.releases!=null || !this.releases.isEmpty();
	}
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return herokuApp.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return herokuApp.getName();
	}

}
