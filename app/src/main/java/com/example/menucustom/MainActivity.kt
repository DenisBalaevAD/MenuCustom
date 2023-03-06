package com.example.menucustom

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.menucustom.databinding.ActivityMainBinding
import org.apache.commons.io.IOUtils
import java.io.*
import java.nio.charset.StandardCharsets
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var dlg1: DialogFragment? = null
    var dlg2: DialogFragment? = null


    lateinit var mSettings:SharedPreferences
    private val PERMISSION_STORAGE = 101

    @SuppressLint("RtlHardcoded", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSettings = getSharedPreferences("MenuCustom", Context.MODE_PRIVATE);

        val dialog = Dialog(this,R.style.DialogStyle)
        val window = dialog.window
        window!!.setGravity(Gravity.LEFT)
        window.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT)
        dialog.setTitle(null)
        dialog.setContentView(R.layout.item_list)
        dialog.setCancelable(true)
        dialog.show()

        val alertDialog: AlertDialog = AlertDialog.Builder(this, R.style.DialogStyle)
            .setTitle("Title")
            .setMessage("Are you sure?")
            .create()

        val window2: Window = alertDialog.window!!

        window2.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window2.setGravity(Gravity.RIGHT)

        alertDialog.show()

        if(mSettings.contains("VALUE")) {
            val text = mSettings.getString("VALUE", "")
            binding.tv.text = text
        }

        if (!PermissionUtils.hasPermissions(this)) {
            PermissionUtils.requestPermissions(this, PERMISSION_STORAGE)
        }

        binding.btnDlg2.setOnClickListener {
            //val intent = Intent(Intent.ACTION_GET_CONTENT)
            //val intent: Intent = Intent("android.intent.action.OPEN_DOCUMENT")

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            //intent.type = "*/*"
            intent.type = "application/vnd.android.package-archive"
            //intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
            startActivityForResult(intent, 1)

            mSettings.edit().putString("VALUE", "ОК").apply()
        }

        /*dlg1 = Dialog1()
        dlg2 = Dialog2()*/

        //showBottomSheetDialog()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK){
            val chosenImageUri:Uri? =  data?.data

            Toast.makeText(this,chosenImageUri!!.path.toString(),Toast.LENGTH_LONG).show()

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(chosenImageUri, "application/vnd.android.package-archive")
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            /*val bytes = toByteArrayUri(uri = chosenImageUri)
            Log.d("BYTES",bytes.toString())

            val encodedString = Base64.getEncoder().encodeToString(bytes)
            val decodedBytes = Base64.getDecoder().decode(encodedString)
            val decodedString = String(decodedBytes)

            Log.d("BYTES",decodedString)*/

            startActivity(intent)
        }
    }

    fun toByteArrayUri(uri: Uri): ByteArray?= IOUtils.toByteArray(contentResolver.openInputStream(uri))


    fun onClick(v: View) {
        when (v.id) {
            R.id.btnDlg1 -> dlg1!!.show(fragmentManager, "dlg1")
            else -> {}
        }
    }
}