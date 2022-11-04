package vn.vunganyen.fastdelivery.screens.admin.staffMng.getList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import vn.vunganyen.fastdelivery.R
import vn.vunganyen.fastdelivery.data.adapter.AdapterStaffMng
import vn.vunganyen.fastdelivery.data.model.classSupport.StartAlertDialog
import vn.vunganyen.fastdelivery.data.model.staff.CheckWorkReq
import vn.vunganyen.fastdelivery.data.model.staff.ListStaffRes
import vn.vunganyen.fastdelivery.databinding.ActivityStaffMngBinding
import vn.vunganyen.fastdelivery.screens.admin.staffMng.insert.InsertStaffActivity

class StaffMngActivity : AppCompatActivity(), StaffMngItf {
    lateinit var binding : ActivityStaffMngBinding
    lateinit var staffMngPst: StaffMngPst
    var adapterStaffMng : AdapterStaffMng = AdapterStaffMng()
    var alertDialog: StartAlertDialog = StartAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffMngBinding.inflate(layoutInflater)
        setContentView(binding.root)
        staffMngPst = StaffMngPst(this)
        setToolbar()
        getData()
        invokeDeleteStaff()
        setEvent()
    }
    fun getData(){
        staffMngPst.getFullStaff()
    }

    fun setEvent(){
        binding.imvInsertStaff.setOnClickListener{
            var intent = Intent(this, InsertStaffActivity::class.java)
            startActivity(intent)
        }
    }

    fun invokeDeleteStaff(){
        adapterStaffMng.clickRemoveStaff = {
            id -> staffMngPst.checkStaffWork(CheckWorkReq(id))
        }
    }

    override fun getFullStaff(list: List<ListStaffRes>) {
        adapterStaffMng.setData(list)
        binding.rcvListStaff.adapter = adapterStaffMng
        binding.rcvListStaff.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun deleteFail() {
        alertDialog.showStartDialog3(getString(R.string.RemoveStaffFail),this)
    }

    override fun allowDetele(req: CheckWorkReq) {
        alertDialog.showStartDialog4(getString(R.string.AllRemoveStaff),this)
        alertDialog.clickOk = {
            ->staffMngPst.deleteStaff(req)
        }
    }

    override fun deleteSuccess() {
        alertDialog.showStartDialog3(getString(R.string.RemoveStaffSuccess),this)
        getData()
    }

    fun setToolbar() {
        var toolbar = binding.toolbarListStaff
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}