package com.example.nawfal_3tif.Message

import android.R.id.message
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.nawfal_3tif.databinding.ItemMessageBinding
import com.google.android.material.snackbar.Snackbar

class MessageAdapter(
    context: Context,
    private val  Messages: List<MessageModel>
) : ArrayAdapter<MessageModel>(context, 0, Messages) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemMessageBinding =
            ItemMessageBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root

        val data = Messages[position]

        Glide.with(context)
            .load(data.avatarUrl)
            .into(binding.avatarImg)

        binding.textSender.text = data.senderName
        binding.textMessage.text = data.messageText
        binding.timestamp.text = data.timestamp

        view.setOnClickListener {
            Snackbar.make(
                parent,
                "Pesan dari ${data.senderName}: ${data.messageText} ${data.timestamp}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        return view
    }
}