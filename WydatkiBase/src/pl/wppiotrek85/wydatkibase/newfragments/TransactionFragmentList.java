package pl.wppiotrek85.wydatkibase.newfragments;

import java.util.ArrayList;
import java.util.Arrays;

import pl.wppiotrek85.wydatkibase.R;
import pl.wppiotrek85.wydatkibase.adapters.BaseObjectAdapter;
import pl.wppiotrek85.wydatkibase.adapters.TransactionAdapter;
import pl.wppiotrek85.wydatkibase.asynctasks.ReadRepositoryAsyncTask.AsyncTaskResult;
import pl.wppiotrek85.wydatkibase.entities.ItemsContainer;
import pl.wppiotrek85.wydatkibase.entities.Transaction;
import pl.wppiotrek85.wydatkibase.enums.ERepositoryTypes;
import pl.wppiotrek85.wydatkibase.enums.ViewState;
import pl.wppiotrek85.wydatkibase.exceptions.RepositoryException;
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.interfaces.IReadRepository;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class TransactionFragmentList extends BaseListFragment<Transaction>
		implements IReadRepository, OnItemClickListener {

	private int skip = 0;
	private int take = 20;
	private boolean readMore = false;
	private boolean hasMore = false;
	private int accountId = 0;

	public TransactionFragmentList() {
		super(R.layout.fragment_transactions_list);
	}

	@Override
	public BaseObjectAdapter<Transaction> getAdapter(FragmentActivity activity,
			ArrayList<Transaction> list, IOnAdapterCheckboxClick object) {
		return new TransactionAdapter(getCurrentContext(), list, this.hasMore,
				null);
	}

	@Override
	public ArrayList<Transaction> getObjectList() {
		ItemsContainer<Transaction> container = WydatkiGlobals.getInstance()
				.getTransactionsContainer(accountId);
		ArrayList<Transaction> list = new ArrayList<Transaction>(
				Arrays.asList(container.getItems()));
		return list;
	}

	@Override
	protected void linkOtherView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void configureView() {
		objectListView.setOnItemClickListener(this);

	}

	@Override
	public void reload(boolean forceReload) {
		ItemsContainer<Transaction> container = WydatkiGlobals.getInstance()
				.getTransactionsContainer(accountId);
		if (container == null)
			forceReload = true;

		if (!forceReload) {

			ArrayList<Transaction> list = new ArrayList<Transaction>(
					Arrays.asList(container.getItems()));
			if (list == null)
				forceReload = true;
			else {
				this.hasMore = container.hasMore();
				adapter = new TransactionAdapter(getActivity(), list,
						this.hasMore, null);

				objectListView.setAdapter(adapter);
			}
		}

		if (forceReload) {
			skip = 0;
			readMore = false;
			hasMore = false;
			manager = new ObjectManager(ERepositoryTypes.Transactions, this,
					skip, take, accountId);
		}
	}

	@Override
	public void onTaskStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskProgress() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ItemsContainer) {
			ItemsContainer<Transaction> containt = (ItemsContainer<Transaction>) response.bundle;
			this.skip += containt.getItems().length;
			WydatkiGlobals.getInstance().setTransactionsContainer(accountId,
					containt, readMore ? true : false);
			reload(false);
		}
	}

	@Override
	public void onTaskInvalidResponse(RepositoryException exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Object o = view.getTag();
		if (o instanceof ViewState) {
			ViewState state = (ViewState) o;
			if (state == ViewState.DownloadMore && hasMore) {
				((TransactionAdapter) adapter).getMoreTransactions();
				getMoreTransactions();
			}
		}
	}

	private void getMoreTransactions() {
		readMore = true;
		manager = new ObjectManager(ERepositoryTypes.Transactions, this, skip,
				take, accountId);
	}

	@Override
	public void afterReloadAction() {
		// TODO Auto-generated method stub

	}

}
