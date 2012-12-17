package pl.wppiotrek85.wydatkibase.fragments.edit;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.Project;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryManagerMethods;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.ListSupport;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import pl.wppiotrek85.wydatkibase.units.ResultCodes;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class EditProjectFragment extends EditObjectBaseFragment<Project> {

	private CheckBox cbx_visibleForAll;

	public EditProjectFragment(boolean shouldReload, Project item) {
		super(shouldReload, item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.edit_project_layout, null);
		return convertView;
	}

	@Override
	protected void linkViews() {
		cbx_visibleForAll = (CheckBox) getView().findViewById(
				R.id.new_cbx_visibleForAll);
	}

	@Override
	public void configureViews() {
		if (!isUpdate)
			currentObject = new Project();

		if (isUpdate) {
			etbx_name.setText(currentObject.getName());
			cbx_isActive.setChecked(currentObject.isActive());
			cbx_visibleForAll.setChecked(currentObject.isVisibleForAll());
		}
	}

	@Override
	protected void prepareToSave() {
		boolean isValid = false;
		String name = etbx_name.getText().toString().trim();
		if (isUpdate
				|| (name.length() > 0 && !ListSupport.isProjectNameUsed(name))) {
			isValid = true;
		} else
			isValid = false;

		if (isValid) {
			Project project = new Project();

			if (isUpdate) {
				int projectId = currentObject.getId();
				project.setId(projectId);
			}

			project.setName(name);
			project.setIsActive(cbx_isActive.isChecked());
			project.setIsVisibleForAll(cbx_visibleForAll.isChecked());
			this.currentObject = project;
			saveObject();
		}
	}

	@Override
	protected void saveObject() {
		ObjectManager manager = new ObjectManager(ERepositoryTypes.Projects,
				this, ERepositoryManagerMethods.Create, currentObject);
	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof Project) {
			WydatkiGlobals.getInstance().updateProjectsList(
					(Project) response.bundle);
		}
		leaveActivity(ResultCodes.RESULT_NEED_UPDATE);
	}

}
