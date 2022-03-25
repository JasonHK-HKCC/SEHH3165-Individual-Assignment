package app.jasonhk.hkcc.bnz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;
import lombok.val;
import lombok.var;

/**
 * A simple {@link Fragment} subclass.
 */
public class BmiFragment extends Fragment implements View.OnClickListener
{
    public BmiFragment() {}

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

        val resources   = getResources();
        val preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        var notSetText = "Not set";
        {
            val notSetId = getResources()
                    .getIdentifier("not_set", "string", getContext().getPackageName());
            if (notSetId != 0) { notSetText = getString(notSetId); }
        }

        //<editor-fold desc="User Info">
        val name = preferences.getString("user_name", notSetText);
        ((TextView) view.findViewById(R.id.user_name)).setText(name);

        val height = preferences.getInt("user_height", 180);
        val weight = preferences.getInt("user_weight", 60);

        var brithday = preferences.getString("user_birthday", null);
        if (brithday != null)
        {
            brithday = LocalDate.parse(brithday)
                                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        }
        else
        {
            brithday = notSetText;
        }

        var gender = notSetText;
        {
            val genderType = Gender.fromKey(preferences.getString("user_gender", null));
            if (genderType != null)
            {
                gender = resources.getStringArray(R.array.gender_entries)[genderType.ordinal()];
            }
        }

        TextView userSummaryView = view.findViewById(R.id.user_summary);
        userSummaryView.setText(
                getString(R.string.user_summary_bmi, height, weight, brithday, gender));
        //</editor-fold>

        //<editor-fold desc="BMI Result">
        val bmi = weight / Math.pow(height / 100.0, 2);

        TextView bmiResultView = view.findViewById(R.id.bmi_result);
        bmiResultView.setText(getString(R.string.bmi_result_header, bmi));

        val bmiCategories = resources.getStringArray(R.array.bmi_categories);

        TextView bmiCategoryView   = view.findViewById(R.id.bmi_category);
        TextView bmiSuggestionView = view.findViewById(R.id.bmi_suggestions);

        String bmiCategory;
        if (bmi < 18.5)
        {
            if (bmi < 16)
            {
                bmiCategory = bmiCategories[0];
            }
            else if (bmi < 17)
            {
                bmiCategory = bmiCategories[1];
            }
            else
            {
                bmiCategory = bmiCategories[2];
            }

            bmiSuggestionView.setText(R.string.bmi_suggestions_underweight);
        }
        else if (bmi < 25)
        {
            bmiCategory = bmiCategories[3];
            bmiSuggestionView.setText(R.string.bmi_suggestions_normal);
        }
        else
        {
            if (bmi < 30)
            {
                bmiCategory = bmiCategories[4];
            }
            else if (bmi < 35)
            {
                bmiCategory = bmiCategories[5];
            }
            else if (bmi < 40)
            {
                bmiCategory = bmiCategories[6];
            }
            else
            {
                bmiCategory = bmiCategories[7];
            }

            bmiSuggestionView.setText(R.string.bmi_suggestions_overweight);
        }
        bmiCategoryView.setText(bmiCategory);
        //</editor-fold>

        //<editor-fold desc="BMI Table">
        DataTable bmiTable = view.findViewById(R.id.bmi_table);
        val bmiHeader = new DataTableHeader.Builder()
                .item(getString(R.string.category_header), 3)
                .item(getString(R.string.bmi_header_with_formula), 2)
                .build();

        val bmiRows   = new ArrayList<DataTableRow>();
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

        ImageButton bmiTableToggle = view.findViewById(R.id.bmi_table_toggle);
        bmiTableToggle.setOnClickListener(this);
        onClick(bmiTableToggle);
        //</editor-fold>
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.bmi_table_toggle)
        {
            val       bmiTableToggle = (ImageButton) view;
            DataTable bmiTable       = getView().findViewById(R.id.bmi_table);

            switch (bmiTable.getVisibility())
            {
                case View.VISIBLE:
                    bmiTable.setVisibility(View.GONE);
                    bmiTableToggle.setImageResource(R.drawable.ic_arrow_down);
                    break;
                case View.GONE:
                    bmiTable.setVisibility(View.VISIBLE);
                    bmiTableToggle.setImageResource(R.drawable.ic_arrow_up);
                    break;
            }
        }
    }
}