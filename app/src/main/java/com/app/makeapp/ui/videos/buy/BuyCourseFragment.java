package com.app.makeapp.ui.videos.buy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.makeapp.R;
import com.app.makeapp.api.RestClient;
import com.app.makeapp.api.models.CategoriesModel;
import com.app.makeapp.etc.SharedPrefs;
import com.app.makeapp.etc.inapp.IabHelper;
import com.app.makeapp.etc.inapp.IabResult;
import com.app.makeapp.etc.inapp.Inventory;
import com.app.makeapp.etc.inapp.Purchase;
import com.app.makeapp.ui.BaseFragment;
import com.app.makeapp.ui.categories.view.ToolbarView;
import com.app.makeapp.ui.videos.buy.adapter.BuyCourseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.makeapp.etc.Constants.IAP_REQ_CODE;
import static com.app.makeapp.etc.Constants.IN_APP_KEY;
import static com.app.makeapp.etc.Constants.ITEM_SKU;
import static com.app.makeapp.etc.Constants.PURCHASE_TYPE;
import static com.app.makeapp.etc.Constants.TAG;

/**
 * Created by gleb on 19.12.16.
 */

public class BuyCourseFragment extends BaseFragment implements Callback<List<CategoriesModel>>,
        IabHelper.OnIabSetupFinishedListener, IabHelper.QueryInventoryFinishedListener,
        IabHelper.OnIabPurchaseFinishedListener, BuyCourseAdapter.OnBuyCourseItemClickListener {

    private IabHelper mHelper;
    private int categoryId;

    @BindView(R.id.toolbar)
    ToolbarView toolbarView;
    @BindView(R.id.listView)
    ListView listView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestClient.instance().getCategoryList(SharedPrefs.getUserObjectId(context)).enqueue(this);
        mHelper = new IabHelper(context, IN_APP_KEY);
        mHelper.startSetup(this);
        toolbarView.setTitleText("");
    }

    @Override
    public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {
        BuyCourseAdapter buyCourseAdapter = new BuyCourseAdapter(context);
        buyCourseAdapter.setOnBuyCourseListener(this);
        buyCourseAdapter.addAll(response.body());
        listView.setAdapter(buyCourseAdapter);
    }

    @Override
    public void onFailure(Call<List<CategoriesModel>> call, Throwable t) {
        Log.e(getFragmentManager().toString(), t.getMessage());
    }

    @Override
    public void onIabSetupFinished(IabResult result) {
        if (!result.isSuccess()) {
            Log.e(TAG, "In-app Billing setup failed: " + result);
        } else {
            Log.e(TAG, "In-app Billing is set up OK");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy_course, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCourseItemClick(int id) {
        this.categoryId = id;
        mHelper.launchPurchaseFlow(context, ITEM_SKU, IAP_REQ_CODE, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null)
            mHelper.dispose();
        mHelper = null;
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        mHelper.queryInventoryAsync(this);
        if (result.isFailure())
            Log.e(TAG, "onIabPurchaseFinished : result.isFailure");
        if(result.isSuccess()) {
            Log.e(TAG, "onIabPurchaseFinished : purchase.getSku().equals(ITEM_SKU)");
            RestClient.instance().addPurchase(SharedPrefs.getUserObjectId(context), categoryId, PURCHASE_TYPE).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(getFragmentManager().toString(), t.getMessage());
                }
            });
        }
    }

    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        if (result.isFailure()) {
            Log.e(TAG, "onQueryInventoryFinished : result.isFailure");
        } else {
            Log.e(TAG, "onQueryInventoryFinished : success");
            mHelper.consumeAsync(inv.getPurchase(ITEM_SKU), null);
        }
    }
}
