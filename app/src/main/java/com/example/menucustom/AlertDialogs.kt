package com.example.menucustom

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast

class AlertDialogs(private var context:Context) {

    fun show(view:Int){
        val viewCustomer = LayoutInflater.from(context).inflate(view,null)

        val seekBar = viewCustomer.findViewById<Button>(R.id.button)

        seekBar.setOnClickListener {
            Toast.makeText(context,"1234", Toast.LENGTH_LONG).show()
            val alertDialog: AlertDialog = AlertDialog.Builder(context, R.style.DialogStyle)
                .setTitle("Title")
                .setMessage("Are you sure?")
                .create()
            alertDialog.show()
        }

    }
}