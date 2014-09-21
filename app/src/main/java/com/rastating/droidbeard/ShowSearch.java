package com.rastating.droidbeard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rastating.droidbeard.entities.TvDBResult;
import com.rastating.droidbeard.net.AddShowTask;
import com.rastating.droidbeard.net.ApiResponseListener;
import com.rastating.droidbeard.net.SearchTvDBTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowSearch extends Activity implements ApiResponseListener<TvDBResult[]> {
    private EditText mCriteria;
    private ProgressDialog mDialog;
    private ListView mListView;
    private Button mSearchButton;
    private SearchTvDBTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_search);
        mCriteria = (EditText) findViewById(R.id.criteria);
        mListView = (ListView) findViewById(R.id.list_view);
        mSearchButton = (Button) findViewById(R.id.search);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog("Searching The TVDB", "Please wait...");
                mTask = new SearchTvDBTask(ShowSearch.this);
                mTask.addResponseListener(ShowSearch.this);
                mTask.execute(mCriteria.getText().toString());
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idView = (TextView) view.findViewById(R.id.id);
                int id = Integer.valueOf(idView.getText().toString());
                showProgressDialog("Adding Show", "Please wait...");

                AddShowTask task = new AddShowTask(ShowSearch.this);
                task.addResponseListener(new ApiResponseListener<Boolean>() {
                    @Override
                    public void onApiRequestFinished(Boolean result) {
                        mDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowSearch.this);
                        builder.setTitle("Show Queued")
                            .setMessage("The show has been queued to be added in Sickbeard.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ShowSearch.this.finish();
                                }
                            })
                            .create()
                            .show();
                    }
                });

                task.execute(id);
            }
        });

        Preferences preferences = new Preferences(this);
        if (!preferences.hasAcknowledgedShowAddingHelp()) {
            showToolTip();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mTask != null) {
            mTask.cancel(true);
        }
    }

    @Override
    public void onApiRequestFinished(TvDBResult[] results) {
        mTask = null;
        mDialog.dismiss();

        if (results != null) {
            ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>(results.length);
            for (TvDBResult result : results) {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("name", result.getName());
                item.put("first_aired", result.getFirstAired());
                item.put("id", String.valueOf(result.getId()));
                data.add(item);
            }

            String[] from = new String[] { "name", "first_aired", "id" };
            int[] to = new int[] { R.id.name, R.id.first_aired, R.id.id };
            SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.tvdb_result_item, from, to) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    if (position % 2 == 0) {
                        view.setBackgroundResource(R.drawable.alternate_list_item_bg);
                    } else {
                        view.setBackgroundColor(Color.TRANSPARENT);
                    }

                    return view;
                }
            };

            mListView.setAdapter(adapter);
        }
        else {
            mListView.setAdapter(null);
        }
    }

    private void showProgressDialog(String caption, String message) {
        mDialog = new ProgressDialog(ShowSearch.this);
        mDialog.setTitle(caption);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();
    }

    private void showToolTip() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.tooltip_dialog, null);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.do_not_show_again);
        builder.setView(view);
        builder.setTitle("DroidBeard Tip");
        builder.setMessage("Before adding a show through DroidBeard, you must first ensure you have setup your default options in SickBeard; such as the storage location and quality settings. This can be done in the 'Add Shows' page in SickBeard itself.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (checkBox.isChecked()) {
                    Preferences preferences = new Preferences(ShowSearch.this);
                    preferences.putBoolean(Preferences.ACKNOWLEDGED_SHOW_ADDING_HELP, true);
                }
            }
        });
        builder.show();
    }
}