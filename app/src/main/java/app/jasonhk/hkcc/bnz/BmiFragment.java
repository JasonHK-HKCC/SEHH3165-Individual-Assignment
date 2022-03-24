package app.jasonhk.hkcc.bnz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;
import lombok.val;
import lombok.var;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BmiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BmiFragment extends Fragment
{
    public BmiFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BmiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BmiFragment newInstance(String param1, String param2)
    {
        BmiFragment fragment = new BmiFragment();
        Bundle      args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        val resources = getResources();

        val preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        var notSetText = "Not set";
        {
            val notSetId = getResources()
                    .getIdentifier("not_set", "string", getContext().getPackageName());
            if (notSetId != 0) { notSetText = getString(notSetId); }
        }

        val name = preferences.getString("user_name", notSetText);
        ((TextView) view.findViewById(R.id.user_name)).setText(name);

        val height = preferences.getInt("user_height", 180);
        val weight = preferences.getInt("user_weight", 60);

        var gender = notSetText;
        {
            val genderType = Gender.fromKey(preferences.getString("user_gender", null));
            if (genderType != null)
            {
                gender = getResources().getStringArray(R.array.gender_entries)[genderType.ordinal()];
            }
        }

        TextView userSummaryView = view.findViewById(R.id.user_summary);
        userSummaryView.setText(
                getString(R.string.user_summary_bmi, height, weight, notSetText, gender));

        //<editor-fold desc="BMI Table">
        DataTable bmiTable = view.findViewById(R.id.bmi_table);
        val bmiHeader = new DataTableHeader.Builder()
                .item(getString(R.string.category_header), 3)
                .item(getString(R.string.bmi_header_with_formula), 2)
                .build();

        val bmiRows = new ArrayList<DataTableRow>();
        val bmiCategories = resources.getStringArray(R.array.bmi_categories);
        val bmiRanges = resources.getStringArray(R.array.bmi_ranges);
        for (var i = 0; i < bmiCategories.length; i++)
        {
            val row = new DataTableRow.Builder()
                    .value(bmiCategories[i])
                    .value(bmiRanges[i])
                    .build();
            bmiRows.add(row);
        }

        bmiTable.setHeader(bmiHeader);
        bmiTable.setRows(bmiRows);
        bmiTable.inflate(getContext());
        //</editor-fold>
    }
}