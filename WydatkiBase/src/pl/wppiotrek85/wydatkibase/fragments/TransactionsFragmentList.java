package pl.wppiotrek85.wydatkibase.fragments;

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
import pl.wppiotrek85.wydatkibase.interfaces.IOnAdapterCheckboxClick;
import pl.wppiotrek85.wydatkibase.managers.ObjectManager;
import pl.wppiotrek85.wydatkibase.support.WydatkiGlobals;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class TransactionsFragmentList extends
		ObjectListBaseFragment<Transaction> implements OnItemClickListener {

	private int skip = 0;
	private int take = 20;
	private boolean readMore = false;
	private boolean hasMore = false;
	private int accountId = 0;

	public TransactionsFragmentList() {
		super(R.layout.fragment_transactions_list,
				ERepositoryTypes.Transactions, false, false);
	}

	public TransactionsFragmentList(int resources,
			ERepositoryTypes repositoryType, boolean shouldReload,
			Boolean isChecakble) {
		super(R.layout.fragment_transactions_list, repositoryType,
				shouldReload, isChecakble);
	}

	public TransactionsFragmentList(boolean shouldReload, Boolean isChecakble,
			int accountId) {
		super(R.layout.fragment_transactions_list,
				ERepositoryTypes.Transactions, shouldReload, isChecakble);
		this.accountId = accountId;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}

	@Override
	public void afterRefreshAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void linkViews(View convertView) {
		// TODO Auto-generated method stub

	}

	@Override
	public BaseObjectAdapter<Transaction> getAdapter(FragmentActivity activity,
			ArrayList<Transaction> list, IOnAdapterCheckboxClick object) {
		return new TransactionAdapter(getActivity(), list, this.hasMore, this);
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
	public void onTaskResponse(AsyncTaskResult response) {
		if (response.bundle instanceof ItemsContainer) {
			ItemsContainer<Transaction> containt = (ItemsContainer<Transaction>) response.bundle;
			this.skip += containt.getItems().length;
			WydatkiGlobals.getInstance().setTransactionsContainer(accountId,
					containt, readMore ? true : false);
			refreshFragment(false);
		}
	}

	@Override
	public void refreshFragment(boolean forceRefresh) {
		ItemsContainer<Transaction> container = WydatkiGlobals.getInstance()
				.getTransactionsContainer(accountId);
		// if (container == null)
		// forceRefresh = true;
		// else
		// forceRefresh = false;

		if (!forceRefresh) {

			ArrayList<Transaction> list = new ArrayList<Transaction>(
					Arrays.asList(container.getItems()));
			if (list == null)
				forceRefresh = true;
			else {
				this.hasMore = container.hasMore();
				adapter = new TransactionAdapter(getActivity(), list,
						this.hasMore, null);

				objectListView.setAdapter(adapter);
			}
		}

		if (forceRefresh) {
			skip = 0;
			readMore = false;
			hasMore = false;
			manager = new ObjectManager(ERepositoryTypes.Transactions, this, 0,
					take, accountId);
		}
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
}
