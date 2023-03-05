package com.example.menucustom

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.menucustom.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var dlg1: DialogFragment? = null
    var dlg2: DialogFragment? = null

    // The path to the root of this app's internal storage
    private var mPrivateRootDir: File? = null

    // The path to the "images" subdirectory
    private var mImagesDir: File? = null

    // Array of files in the images subdirectory
    lateinit var mImageFiles: Array<File>

    // Array of filenames corresponding to mImageFiles
    lateinit var mImageFilenames: Array<String>
    // Initialize the Activity

    lateinit var chosenImageUri:Uri

    @SuppressLint("RtlHardcoded", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        /*dlg1 = Dialog1()
        dlg2 = Dialog2()*/

        //showBottomSheetDialog()

        binding.btnDlg2.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            //intent.type = "application/vnd.android.package-archive"
            startActivityForResult(intent, 1)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK){
            val chosenImageUri:Uri? =  data?.data

           /*val cursor = contentResolver.query( chosenImageUri.to, null, null, null, null );
            cursor!!.moveToFirst();
            val filePath = cursor.getString(0);
            cursor.close();*/

            Toast.makeText(this,chosenImageUri.toString(),Toast.LENGTH_LONG).show()
            //Toast.makeText(this,"OK",Toast.LENGTH_LONG).show()

            /*val intent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.fromFile(File(chosenImageUri.toString()))
            intent.setDataAndType(uri,"application/vnd.android.package-archive")
            startActivity(intent)*/

            /*val installIntent = Intent(Intent.ACTION_VIEW)
            installIntent.setDataAndType(
                Uri.parse(filePath),
                "application/vnd.android.package-archive"
            )
            startActivity(installIntent)*/


            /*packageManager.canRequestPackageInstalls()
            startActivity(Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES))*/

            /*val intent = Intent(Intent.ACTION_INSTALL_PACKAGE)
            intent.setDataAndType(
                FileProvider.getUriForFile(
                    this,
                    "your.application.package.fileprovider",
                    File(chosenImageUri.toString())
                ), "application/vnd.android.package-archive"
            )
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            startActivity(intent)*/

            /*val intent = Intent(Intent.ACTION_INSTALL_PACKAGE)
            intent.setDataAndType(Uri.parse(chosenImageUri.toString()), "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)*/

            //install(chosenImageUri.toString())

            /*if(android.os.Build.VERSION.SDK_INT >= 29){
                val intent = Intent(Intent.ACTION_INSTALL_PACKAGE);
                intent.data = Uri.parse(chosenImageUri);
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                startActivity(intent);
            }else{
                val intent = Intent(Intent.ACTION_VIEW);
                //output file is the apk downloaded earlier
                intent.setDataAndType(Uri.parse(chosenImageUri), "application/vnd.android.package- archive");
                        startActivity(intent);
            }*/

            val urlapk = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".fileProvider",
                File(chosenImageUri?.path.toString())
            )
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(urlapk, "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(intent)
        }
    }

    fun install(filename: String) {
        val file = File(filename)
        if (file.exists()) {
            try {
                val command: String
                command = "adb install -r $filename"
                val proc = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
                proc.waitFor()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.btnDlg1 -> dlg1!!.show(fragmentManager, "dlg1")
            else -> {}
        }
    }

    fun showBottomSheetDialog() {
        val view: View = layoutInflater.inflate(R.layout.dialog1, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()
    }
}