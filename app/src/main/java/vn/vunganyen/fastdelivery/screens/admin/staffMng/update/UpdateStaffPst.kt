package vn.vunganyen.fastdelivery.screens.admin.staffMng.update

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.vunganyen.fastdelivery.data.api.*
import vn.vunganyen.fastdelivery.data.model.auth.UpdateStatusAuthReq
import vn.vunganyen.fastdelivery.data.model.district.DistrictRes
import vn.vunganyen.fastdelivery.data.model.district.MainGetDistrictRes
import vn.vunganyen.fastdelivery.data.model.mass.UpdateRes
import vn.vunganyen.fastdelivery.data.model.parcel.MainShipperStatistics
import vn.vunganyen.fastdelivery.data.model.parcel.ShipperStatisticsReq
import vn.vunganyen.fastdelivery.data.model.salary.*
import vn.vunganyen.fastdelivery.data.model.staff.*
import vn.vunganyen.fastdelivery.screens.splash.SplashActivity
import java.text.SimpleDateFormat
import java.util.*

class UpdateStaffPst {
    var updateStaffItf : UpdateStaffItf
    var c =  GregorianCalendar(TimeZone.getTimeZone("GMT+7"))

    constructor(updateStaffItf: UpdateStaffItf) {
        this.updateStaffItf = updateStaffItf
    }

    fun validCheckUpdate(req: AdminUpdateReq) {
        if (req.manv.isEmpty() || req.hoten.isEmpty() || req.ngaysinh.isEmpty() || req.gioitinh.isEmpty() || req.diachi.isEmpty() ||
            req.email.isEmpty() || req.sdt.isEmpty()){
            updateStaffItf.Empty()
            return
        }
        if (!SplashActivity.CMND.matcher(req.cmnd).matches()) {
            updateStaffItf.CmndIllegal()
            return
        }
        if (!SplashActivity.SDT.matcher(req.sdt).matches()) {
            updateStaffItf.PhoneIllegal()
            return
        }
        if (!SplashActivity.EMAIL_ADDRESS.matcher(req.email).matches()) {
            updateStaffItf.EmailIllegal()
            return
        }
        val current : String = SplashActivity.formatYear.format(Date())
        val str: List<String> = req.ngaysinh.split("-")
        var year = str.get(0).toInt()
        var old = current.toInt() - year
        if(old < 18){
            updateStaffItf.OrlError()
            return
        }
        else checkCmndExist(req)

    }

    fun checkCmndExist(req : AdminUpdateReq){
        ApiStaffService.Api.api.checkCmnd(SplashActivity.token, CheckCmndReq(req.cmnd)).enqueue(object :
            Callback<CheckProfileRes> {
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        checkPhoneExist(req)
                    }
                    else{
                        if(UpdateStaffActivity.staff.cmnd.equals(check)){
                            checkPhoneExist(req)
                        }
                        else updateStaffItf.CmndExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkPhoneExist(req : AdminUpdateReq){
        ApiStaffService.Api.api.checkPhone(SplashActivity.token, CheckPhoneReq(req.sdt)).enqueue(object : Callback<CheckProfileRes>{
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        checkEmailExist(req)
                    }
                    else{
                        if(UpdateStaffActivity.staff.sdt.equals(check)){
                            checkEmailExist(req)
                        }
                        else updateStaffItf.PhoneExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun checkEmailExist(req : AdminUpdateReq){
        ApiStaffService.Api.api.checkEmail(SplashActivity.token, CheckEmailReq(req.email)).enqueue(object : Callback<CheckProfileRes>{
            override fun onResponse(call: Call<CheckProfileRes>, response: Response<CheckProfileRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check.equals("false")){
                        updateStaffItf.CheckSuccess()
                    }
                    else{
                        if(UpdateStaffActivity.staff.email.equals(check)){
                            updateStaffItf.CheckSuccess()
                        }
                        else  updateStaffItf.EmailExist()
                    }
                }
            }
            override fun onFailure(call: Call<CheckProfileRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateProfile(req : AdminUpdateReq){
        ApiStaffService.Api.api.adminUpdateStaff(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    updateStaffItf.updateSuccess()
                }
            }
            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getDistrict(){
        ApiDistrictService.Api.api.getDistrict1().enqueue(object : Callback<MainGetDistrictRes>{
            override fun onResponse(call: Call<MainGetDistrictRes>, response: Response<MainGetDistrictRes>) {
                if(response.isSuccessful){
                    updateStaffItf.getListDistrict(response.body()!!.districts)
                }
            }

            override fun onFailure(call: Call<MainGetDistrictRes>, t: Throwable) {
                println("UpdateStaffPst Error getDistrict()")
            }

        })
    }

    fun getWards(code : Long){
        ApiDistrictService.Api.api.getDistrict2(code).enqueue(object : Callback<DistrictRes>{
            override fun onResponse(call: Call<DistrictRes>, response: Response<DistrictRes>) {
                if(response.isSuccessful){
                    updateStaffItf.getListWards(response.body()!!.wards)
                }
            }

            override fun onFailure(call: Call<DistrictRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateStatus(req : UpdateStatusAuthReq){
        ApiAuthService.Api.api.updateStatusAuth(SplashActivity.token, req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    if(req.trangthai == 0){
                        updateStaffItf.activeAccount()
                    }
                    else updateStaffItf.lockAccount()
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun checkSalary(req : CheckSalaryReq){
        ApiSalaryService.Api.api.check_salary(SplashActivity.token,req).enqueue(object : Callback<CheckSalaryRes>{
            override fun onResponse(call: Call<CheckSalaryRes>, response: Response<CheckSalaryRes>) {
                if(response.isSuccessful){
                    var check = response.body()!!.result
                    if(check == true){
                        val formatDate = SimpleDateFormat("yyyy-MM")
                        var mdate: Date = formatDate.parse(req.mdate)
                        c.clear()
                        c.time = mdate
                        c.add(Calendar.HOUR_OF_DAY, 7)
                        c.roll(Calendar.MONTH,-1)
                        var strDate = formatDate.format(c.time)
                        updateStaffItf.checkSalaryExist(strDate)
                    }
                    else{
                        updateStaffItf.checkSalaryNotExist()
                    }
                }
            }

            override fun onFailure(call: Call<CheckSalaryRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun findEndOfMonth(cal: Calendar) : Calendar {
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1)
        System.out.println("Start of the next month:  " + cal.getTime());
        return cal
    }

    fun shipperStatistics(req : ShipperStatisticsReq){
        ApiParcelService.Api.api.shipper_statistics(SplashActivity.token,req).enqueue(object : Callback<MainShipperStatistics>{
            override fun onResponse(call: Call<MainShipperStatistics>, response: Response<MainShipperStatistics>) {
                if(response.isSuccessful){
                    updateStaffItf.getListSuccess(response.body()!!.result)
                }
            }

            override fun onFailure(call: Call<MainShipperStatistics>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addSalaryStaff(req : AddSalaryStaffReq){
        ApiSalaryService.Api.api.add_salary_staff(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    var mdate: Date = SplashActivity.formatdate.parse(req.ngaynhan)
                    c.clear()
                    c.time = mdate
                    c.add(Calendar.HOUR_OF_DAY, 7)
                    c.roll(Calendar.MONTH,-1)
                    val formatDate = SimpleDateFormat("yyyy-MM")
                    var strDate = formatDate.format(c.time)
                    updateStaffItf.addSalaryStaff(strDate)
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addSalaryShipper(req : AddSalaryShipperReq){
        ApiSalaryService.Api.api.add_salary_shipper(SplashActivity.token,req).enqueue(object : Callback<UpdateRes>{
            override fun onResponse(call: Call<UpdateRes>, response: Response<UpdateRes>) {
                if(response.isSuccessful){
                    var mdate: Date = SplashActivity.formatdate.parse(req.ngaynhan)
                    c.clear()
                    c.time = mdate
                    c.add(Calendar.HOUR_OF_DAY, 7)
                    c.roll(Calendar.MONTH,-1)
                    val formatDate = SimpleDateFormat("yyyy-MM")
                    var strDate = formatDate.format(c.time)
                    updateStaffItf.addSalaryShipper(strDate)
                }
            }

            override fun onFailure(call: Call<UpdateRes>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}