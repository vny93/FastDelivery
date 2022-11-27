package vn.vunganyen.fastdelivery.data.model.classSupport

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.databinding.DialogDateBinding
import java.time.Year
import java.util.*

class MonthYearPickerDialog(cal : Calendar) : DialogFragment() {

    companion object {
        private const val MAX_YEAR = 2100
        private const val MIN_YEAR = 2000
    }
    var date : Date = cal.time
    private lateinit var binding: DialogDateBinding

    private var listener: DatePickerDialog.OnDateSetListener? = null

    fun setListener(listener: DatePickerDialog.OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogDateBinding.inflate(layoutInflater)
        val cal: Calendar = Calendar.getInstance().apply { time = date }

        binding.pickerMonth.run {
            minValue = 0
            maxValue = 11
           // value = cal.get(Calendar.MONTH)
            value = date.month
            var list = resources.getStringArray(R.array.month)
            displayedValues = list
        }

        binding.pickerYear.run {
            //val year = cal.get(Calendar.YEAR)
            val year = date.year
            minValue = MIN_YEAR
            maxValue = MAX_YEAR
            value = year-1
        }
        var alertDialog : AlertDialog.Builder = AlertDialog.Builder(requireContext())
        return AlertDialog.Builder(requireContext())
            .setTitle("           Vui lòng chọn tháng và năm")
            .setView(binding.root)
            .setPositiveButton("Xác nhận") { _, _ -> listener?.onDateSet(null, binding.pickerYear.value, binding.pickerMonth.value, 1) }
            .setNegativeButton("Đóng") { _, _ -> dialog?.cancel() }
            .create()
    }
}